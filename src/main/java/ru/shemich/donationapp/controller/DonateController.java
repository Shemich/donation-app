package ru.shemich.donationapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

    @GetMapping("/b")
    public ResponseEntity<JSONObject> getAllTips(){
        List<Donate> donates = donateService.getAll();
        String content;
        log.info("Fetching all tips");
        try {
            String url = "https://admin.netmonet.co/api/external/v1/transaction/restaurant/overall/comments";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestProperty("Authorization", "Token ZWUwNzMwZWQtYjQxOS00YzJlLWJkYzctYjA3OGNlZTA4MTQ0Oks1RGhFN3o4MzJJR1RObzN2MnZYZmRBRk1mWnQ0UFNzcEkzSzNL");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            content = response.toString();
        } catch (Exception e) {
            System.out.print("ERROR : " + e);
            return ResponseEntity.badRequest()
                    .body(null);
        }
        JSONObject root = new JSONObject(content);

        return new ResponseEntity<>(root, HttpStatus.OK);
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

        widget.setDonateId(donate.getId());
        widgetService.update(widget, request);
        if (request.getAmount() != null) {
               streamer.setBalance(request.getAmount());
        }

        streamerService.saveStreamer(streamer);
        log.info("Updated widget with id: {}", widget.getId());

        return new ResponseEntity<>(donate, HttpStatus.CREATED);
    }
}
