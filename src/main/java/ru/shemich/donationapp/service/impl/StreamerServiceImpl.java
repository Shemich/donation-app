package ru.shemich.donationapp.service.impl;

import org.springframework.stereotype.Service;
import ru.shemich.donationapp.model.Streamer;
import ru.shemich.donationapp.repository.StreamerRepository;
import ru.shemich.donationapp.service.StreamerService;

@Service
public class StreamerServiceImpl implements StreamerService {
    private final StreamerRepository streamerRepository;

    public StreamerServiceImpl(StreamerRepository streamerRepository) {
        this.streamerRepository = streamerRepository;
    }

    @Override
    public Streamer getByNickname(String nickname) {
        return streamerRepository.findByNickname(nickname);
    }
}
