package ru.shemich.donationapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shemich.donationapp.api.request.DonateRequest;
import ru.shemich.donationapp.model.Donate;
import ru.shemich.donationapp.model.Streamer;
import ru.shemich.donationapp.model.Widget;
import ru.shemich.donationapp.service.DonateService;
import ru.shemich.donationapp.service.StreamerService;
import ru.shemich.donationapp.service.WidgetService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(maxAge = 3600)
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
    public ResponseEntity<Donate> createDonate(@PathVariable("streamerNickname") String streamerNickname,
                                               @RequestBody DonateRequest request) {
        Donate donate = donateService.create(request, streamerNickname);  //  создаем донат и сохраняем в бд
        log.info("Create donate with id: {}", donate.getId());
        Streamer streamer = streamerService.getByNickname(streamerNickname);
        log.info("Streamer: {}", streamer);
        Widget widget = widgetService.getById(streamer.getWidgetId());
        log.info("Widget: {}", widget);
        widgetService.update(widget, request);  //  обновить виджет
        streamer.setBalance(streamer.getBalance() + request.getAmount());
        streamerService.saveStreamer(streamer);
        log.info("Updated widget with id: {}", widget.getId());
        return new ResponseEntity<>(donate, HttpStatus.CREATED);
    }
}
