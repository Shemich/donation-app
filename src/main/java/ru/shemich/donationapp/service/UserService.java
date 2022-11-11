package ru.shemich.donationapp.service;

import ru.shemich.donationapp.model.User;

import java.util.List;

public interface UserService {

    User getById(Long id);
    List<User> getAll();
    void saveUser(User user);
    void deleteById(Long id);
}
