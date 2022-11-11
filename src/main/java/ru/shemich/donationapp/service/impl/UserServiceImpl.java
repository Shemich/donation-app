package ru.shemich.donationapp.service.impl;

import org.springframework.stereotype.Service;
import ru.shemich.donationapp.model.User;
import ru.shemich.donationapp.repository.UserRepository;
import ru.shemich.donationapp.service.UserService;

import java.util.List;


/*
manage
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
