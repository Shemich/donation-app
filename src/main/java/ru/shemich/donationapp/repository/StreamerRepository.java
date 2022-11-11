package ru.shemich.donationapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shemich.donationapp.model.Streamer;

public interface StreamerRepository extends JpaRepository<Streamer, Long> {
    Streamer findByNickname(String nickname);
}
