package ru.shemich.donationapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.shemich.donationapp.model.Streamer;
import ru.shemich.donationapp.model.Widget;
import ru.shemich.donationapp.service.StreamerService;
import ru.shemich.donationapp.service.WidgetService;


import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/streamers", produces = APPLICATION_JSON_VALUE)
public class StreamerController {
    private final StreamerService streamerService;
    private final WidgetService widgetService;

    @Autowired
    public StreamerController(StreamerService streamerService, WidgetService widgetService) {
        this.streamerService = streamerService;
        this.widgetService = widgetService;
    }

    @GetMapping()
    public ResponseEntity<List<Streamer>> findAll(Model model){
        List<Streamer> streamers = streamerService.getAll();
        model.addAttribute("streamers", streamers);
        log.info("Fetching all streamers");
        return new ResponseEntity<>(streamers, HttpStatus.OK);
    }

    @GetMapping("/{streamerId}")
    public ResponseEntity<Streamer> findStreamer (@PathVariable Long streamerId) {
        Streamer streamer = streamerService.getById(streamerId);
        if (streamer == null) {
            log.warn("Not found streamer with id: {}", streamerId);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            log.info("Found streamer with id: {}", streamerId);
            return new ResponseEntity<>(streamer, HttpStatus.OK);
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> createStreamer(@RequestBody Streamer streamer) {
        streamerService.save(streamer);
        Widget widget = new Widget();
        streamer.setWidgetId(widget.getId());
        widgetService.save(widget);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location","api/v1/streamers/" + streamer.getId());
        log.info("Create streamer with id: {}", streamer.getId());
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @DeleteMapping("/{streamerId}")
    public ResponseEntity<String> deleteStreamer (@PathVariable Long streamerId) {
        streamerService.delete(streamerId);
        log.info("Delete streamer with id: {}", streamerId);
        return new ResponseEntity<>("Streamer with id: " + streamerId + " deleted", HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{streamerId}")
    public ResponseEntity<Streamer> updateStreamer (@PathVariable Long streamerId, @RequestBody Streamer streamerDetails) {
        Streamer streamer = streamerService.getById(streamerId);
        streamer = streamerService.update(streamer, streamerDetails);
        streamerService.save(streamer);
        log.info("Updating streamer with id: {}", streamerId);
        return new ResponseEntity<>(streamer, HttpStatus.OK);
    }
}
