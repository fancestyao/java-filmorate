package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

public interface UserStorage {
    User addUser(User user);

    User updateUser(User user);

    Map<Integer, User> getAllUsers();

    User getUserById(Integer id);
}
