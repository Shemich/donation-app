package ru.shemich.donationapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.shemich.donationapp.model.Donate;
import ru.shemich.donationapp.service.impl.DonateServiceImpl;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/donate", produces = APPLICATION_JSON_VALUE)
public class DonateController {

    private final DonateServiceImpl donateServiceImpl;

    public DonateController(DonateServiceImpl donateServiceImpl) {
        this.donateServiceImpl = donateServiceImpl;
    }

    // admin role only
    @GetMapping()
    public ResponseEntity<List<Donate>> getAll(){
        List<Donate> donates = donateServiceImpl.getAll();
        log.info("Fetching all donates");
        return new ResponseEntity<>(donates, HttpStatus.OK);
    }

    @GetMapping("/{donateId}")
    public ResponseEntity<Donate> getDonate (@PathVariable Long donateId) {
        Donate donate = donateServiceImpl.getById(donateId);
        if (donate == null) {
            log.warn("Not found donate with id: {}", donateId);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            log.info("Found donate with id: {}", donateId);
            return new ResponseEntity<>(donate, HttpStatus.OK);
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> createDonate(@RequestBody Donate donate) {
        donateServiceImpl.save(donate);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location","api/v1/donate/" + donate.getId());
        log.info("Create donate with id: {}", donate.getId());
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    // admin only role
    @DeleteMapping("/{donateId}")
    public ResponseEntity<String> deleteDonate (@PathVariable Long donateId) {
        donateServiceImpl.delete(donateId);
        log.info("Delete donate with id: {}", donateId);
        return new ResponseEntity<>("Donate with id: " + donateId + " deleted", HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{donateId}")
    public ResponseEntity<Donate> updateDonate (@PathVariable Long donateId, @RequestBody Donate donateDetails) {
        Donate donate = donateServiceImpl.getById(donateId);
        donate = donateServiceImpl.update(donate, donateDetails);
        donateServiceImpl.save(donate);
        log.info("Updating person with id: {}", donateId);
        return new ResponseEntity<>(donate, HttpStatus.OK);
    }
}
