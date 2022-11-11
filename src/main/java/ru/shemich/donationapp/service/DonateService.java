package ru.shemich.donationapp.service;

import ru.shemich.donationapp.api.request.DonateRequest;
import ru.shemich.donationapp.model.Donate;

import java.util.List;

public interface DonateService {

    List<Donate> getAll();
    Donate getById(Long id);
    void save(Donate donate);
    Donate update(Donate donate, Donate donateDetails);
    void delete(Long id);
    Donate create(DonateRequest request, String streamerNickname);

}
