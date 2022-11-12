package ru.shemich.donationapp.service;

import ru.shemich.donationapp.model.Streamer;

import java.util.List;

public interface StreamerService {

    List<Streamer> getAll();
    Streamer getById(Long id);
    Streamer getByNickname(String nickname);
    void saveStreamer(Streamer streamer);
    void save(Streamer streamer);
    Streamer update(Streamer streamer, Streamer streamerDetails);
    void delete(Long id);
}
