package ru.shemich.donationapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shemich.donationapp.model.UserAuthDetails;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository <UserAuthDetails, Long> {
    Optional<UserAuthDetails> findByLogin(String login);
}