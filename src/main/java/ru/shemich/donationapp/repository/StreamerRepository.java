package ru.shemich.donationapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shemich.donationapp.model.Streamer;

import java.util.Optional;

public interface StreamerRepository extends JpaRepository<Streamer, Long> {
    Streamer findByNickname(String nickname);
}
