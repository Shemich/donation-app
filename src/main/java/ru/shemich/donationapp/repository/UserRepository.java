package ru.shemich.donationapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shemich.donationapp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
