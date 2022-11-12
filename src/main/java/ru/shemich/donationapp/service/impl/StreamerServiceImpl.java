package ru.shemich.donationapp.service.impl;

import org.springframework.stereotype.Service;
import ru.shemich.donationapp.model.Streamer;
import ru.shemich.donationapp.model.User;
import ru.shemich.donationapp.repository.StreamerRepository;
import ru.shemich.donationapp.service.StreamerService;

import java.util.List;

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

    @Override
    public List<Streamer> getAll() {
        return streamerRepository.findAll();
    }
    @Override
    public Streamer getById(Long id) {
        return streamerRepository.findById(id).orElse(null);
    }

    @Override
    public void saveStreamer(Streamer streamer) {
        streamerRepository.save(streamer);
    }

    @Override
    public void save(Streamer streamer) {
        streamer.setBalance(0L);
        streamer.setWidgetId(9L);
        streamerRepository.save(streamer);
    }

    @Override
    public Streamer update(Streamer streamer, Streamer streamerDetails) {
        if (streamerDetails.getNickname() != null) streamer.setNickname(streamerDetails.getNickname());
        if (streamerDetails.getWidgetId() != null) streamer.setWidgetId(streamerDetails.getWidgetId());
        return streamer;
    }

    @Override
    public void delete(Long id) {
        streamerRepository.deleteById(id);
    }


}
