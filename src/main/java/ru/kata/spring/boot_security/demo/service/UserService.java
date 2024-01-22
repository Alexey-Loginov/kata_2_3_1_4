package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
    List<User> getAllUsers();

    void saveUsers(User user);

    User getUser(long id);

    void updateUser(User user);

    void deleteUser(long id);
}
