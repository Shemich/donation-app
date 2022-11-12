package ru.shemich.donationapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shemich.donationapp.model.Streamer;
import ru.shemich.donationapp.model.Widget;
import ru.shemich.donationapp.service.StreamerService;
import ru.shemich.donationapp.service.impl.StreamerServiceImpl;
import ru.shemich.donationapp.service.impl.WidgetServiceImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import org.json.JSONArray;
import org.json.JSONObject;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(maxAge = 3600)
@Slf4j
@RestController
@RequestMapping(path = "/api/v1/widget/", produces = APPLICATION_JSON_VALUE)
public class WidgetController {

    public String content;
    private final WidgetServiceImpl widgetServiceImpl;
    private final StreamerServiceImpl streamerService;

    @Autowired
    public WidgetController(WidgetServiceImpl widgetServiceImpl, StreamerServiceImpl streamerService) {
        this.widgetServiceImpl = widgetServiceImpl;
        this.streamerService = streamerService;
    }

    @GetMapping("/{widgetId}")
    public ResponseEntity<Widget> getWidget(@RequestParam("token") String hash, @PathVariable("widgetId") Long id) {
        Widget widget = widgetServiceImpl.getById(id);
        if (widget == null) {
            log.warn("Not found widget with id: {}", id);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            log.info("Found widget with id: {}", id);
            if (widget.getHash().equals(hash)) {
                log.info("Token match ok: {}", hash);
                return new ResponseEntity<>(widget, HttpStatus.OK);
            } else {
                log.info("Token match bad: {}", hash);
                return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
            }
        }
    }

    @GetMapping("/{widgetId}/b")
    public ResponseEntity<Widget> current(@RequestParam("token") String hash, @PathVariable("widgetId") Long id) throws JSONException {
        try {
            Widget widget = widgetServiceImpl.getById(id);
            if (widget == null) {
                log.warn("Not found widget with id: {}", id);
            } else {
                log.info("Found widget with id: {}", id);
                if (widget.getHash().equals(hash)) {
                    log.info("Token match ok: {}", hash);
                } else {
                    log.info("Token match bad: {}", hash);
                }
            }
            String url = "https://admin.netmonet.co/api/external/v1/transaction/restaurant/overall/comments";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestProperty ("Authorization", "Token ZWUwNzMwZWQtYjQxOS00YzJlLWJkYzctYjA3OGNlZTA4MTQ0Oks1RGhFN3o4MzJJR1RObzN2MnZYZmRBRk1mWnQ0UFNzcEkzSzNL");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            content = response.toString();
        }
        catch (Exception e) {
            System.out.print("ERROR : "+e);
            return ResponseEntity.badRequest()
                    .body(null);
        }

        JSONObject root = new JSONObject(content);
        JSONArray data = root.getJSONArray("data");
        Widget widget = widgetServiceImpl.getById(id);

        JSONObject lastTip = data.getJSONObject(0);

        widget.setAmount(lastTip.getLong("amount"));
        widget.setDonateMessage(lastTip.getString("message"));
        log.info("message: {}", lastTip.getString("message"));

        log.info("data: {}", data.getJSONObject(0).toString());
        widgetServiceImpl.save(widget);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization",
                "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBRE1JTiIsInJvbGUiOiJBRE1JTiIsInVzZXJfaWQiOjIsImlhdCI6MTY2ODE2NTI0OSwiZXhwIjoxNjY4NzcwMDQ5fQ.RbsnFFwP0FmWulrrWywAUYoSFa7GW-Su1oQ9XgTCgS8");

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(widget);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> createWidget(@RequestBody Widget widget) {
        widgetServiceImpl.save(widget);
        Streamer streamer = streamerService.getById(widget.getStreamerId());
        streamer.setWidgetId(widget.getId());
        streamerService.saveStreamer(streamer);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location","api/v1/widget/" + widget.getId());
        log.info("Create widget with id: {}", widget.getId());
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }
}
