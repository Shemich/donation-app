package ru.shemich.donationapp.service.impl;

import org.springframework.stereotype.Service;
import ru.shemich.donationapp.api.request.DonateRequest;
import ru.shemich.donationapp.model.Donate;
import ru.shemich.donationapp.repository.DonateRepository;
import ru.shemich.donationapp.service.DonateService;

import java.util.List;

@Service
public class DonateServiceImpl implements DonateService {
    private final DonateRepository donateRepository;

    public DonateServiceImpl(DonateRepository donateRepository) {
        this.donateRepository = donateRepository;
    }

    public List<Donate> getAll() {
        return donateRepository.findAll();
    }

    @Override
    public Donate getById(Long id) {
        return donateRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Donate donate) {
        donateRepository.save(donate);
    }



    @Override
    public Donate update(Donate donate, Donate donateDetails) {
        /*        if (personDetails.getName() != null) person.setName(personDetails.getName());*/
        return donate;
    }

    @Override
    public void delete(Long id) {
        donateRepository.deleteById(id);
    }

    @Override
    public Donate create(DonateRequest request, String streamerNickname) {
        Donate donate = new Donate();
        donate.setStreamerNickname(streamerNickname);
        donate.setText(request.getMessage());
        donate.setAmount(request.getAmount());
        donate.setIsPrivate(request.getIsPrivate());
        if (!donate.getIsPrivate()) donate.setDonaterNickname(request.getDonaterNickname());
        donateRepository.save(donate);
        return donate;
    }
}
