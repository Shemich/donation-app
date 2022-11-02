package ru.shemich.donationapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.shemich.donationapp.api.request.DonateRequest;
import ru.shemich.donationapp.model.Donate;
import ru.shemich.donationapp.model.Streamer;
import ru.shemich.donationapp.model.Widget;
import ru.shemich.donationapp.service.DonateService;
import ru.shemich.donationapp.service.StreamerService;
import ru.shemich.donationapp.service.WidgetService;
import ru.shemich.donationapp.service.impl.DonateServiceImpl;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/donate", produces = APPLICATION_JSON_VALUE)
public class DonateController {

    private final DonateService donateService;
    private final WidgetService widgetService;
    private final StreamerService streamerService;


    // admin role only
    @GetMapping()
    public ResponseEntity<List<Donate>> getAll(){
        List<Donate> donates = donateService.getAll();
        log.info("Fetching all donates");
        return new ResponseEntity<>(donates, HttpStatus.OK);
    }

    @GetMapping("/{donateId}")
    public ResponseEntity<Donate> getDonate (@PathVariable Long donateId) {
        Donate donate = donateService.getById(donateId);
        if (donate == null) {
            log.warn("Not found donate with id: {}", donateId);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            log.info("Found donate with id: {}", donateId);
            return new ResponseEntity<>(donate, HttpStatus.OK);
        }
    }

    @PostMapping(path = "/{streamerNickname}", consumes = "application/json")
    public ResponseEntity<String> createDonate(@PathVariable("streamerNickname") String streamerNickname,
                                               @RequestBody DonateRequest request) {
        Donate donate = donateService.create(request, streamerNickname);  //  создаем донат и сохраняем в бд
        log.info("Create donate with id: {}", donate.getId());
        Streamer streamer = streamerService.getByNickname(streamerNickname);  //  обновить виджет
        Widget widget = widgetService.getById(streamer.getWidgetId());
        widgetService.update(widget, request);
        log.info("Updated widget with id: {}", widget.getId());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location","api/v1/donate/" + donate.getId());
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    // admin only role
    @DeleteMapping("/{donateId}")
    public ResponseEntity<String> deleteDonate (@PathVariable Long donateId) {
        donateService.delete(donateId);
        log.info("Delete donate with id: {}", donateId);
        return new ResponseEntity<>("Donate with id: " + donateId + " deleted", HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{donateId}")
    public ResponseEntity<Donate> updateDonate (@PathVariable Long donateId, @RequestBody Donate donateDetails) {
        Donate donate = donateService.getById(donateId);
        donate = donateService.update(donate, donateDetails);
        donateService.save(donate);
        log.info("Updating person with id: {}", donateId);
        return new ResponseEntity<>(donate, HttpStatus.OK);
    }
}
