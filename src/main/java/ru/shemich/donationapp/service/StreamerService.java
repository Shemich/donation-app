package ru.shemich.donationapp.service;

import org.springframework.stereotype.Service;
import ru.shemich.donationapp.model.Streamer;

public interface StreamerService {

    Streamer getByNickname(String nickname);
}
