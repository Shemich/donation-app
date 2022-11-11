package ru.shemich.donationapp.service;

import ru.shemich.donationapp.model.Streamer;

public interface StreamerService {

    Streamer getByNickname(String nickname);
}
