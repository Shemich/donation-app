package ru.shemich.donationapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shemich.donationapp.model.Widget;
import ru.shemich.donationapp.service.impl.WidgetServiceImpl;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/widget/", produces = APPLICATION_JSON_VALUE)
public class WidgetController {

    private final WidgetServiceImpl widgetServiceImpl;

    @Autowired
    public WidgetController(WidgetServiceImpl widgetServiceImpl) {
        this.widgetServiceImpl = widgetServiceImpl;
    }

    @GetMapping("/{widgetId}")
    public ResponseEntity<Widget> getWidget(@PathVariable("widgetId") Long id) {
        Widget widget = widgetServiceImpl.getById(id);
        if (widget == null) {
            log.warn("Not found widget with id: {}", id);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            log.info("Found widget with id: {}", id);
            return new ResponseEntity<>(widget, HttpStatus.OK);
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> createWidget(@RequestBody Widget widget) {
        widgetServiceImpl.save(widget);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location","api/v1/widget/" + widget.getId());
        log.info("Create widget with id: {}", widget.getId());
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }
}
