package ru.shemich.donationapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shemich.donationapp.model.Donate;
import ru.shemich.donationapp.model.Widget;

public interface DonateRepository extends JpaRepository<Donate, Long> {
}
