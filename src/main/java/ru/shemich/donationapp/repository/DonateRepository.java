package ru.shemich.donationapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shemich.donationapp.model.Donate;

public interface DonateRepository extends JpaRepository<Donate, Long> {
}
