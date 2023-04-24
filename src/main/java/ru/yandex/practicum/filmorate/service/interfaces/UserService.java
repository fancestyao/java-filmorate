package ru.yandex.practicum.filmorate.service.interfaces;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    void addFriend(Integer id, Integer friendId);

    void deleteFriend(Integer id, Integer friendId);

    List<User> getAllFriends(Integer id);

    List<User> getMutualFriends(Integer id, Integer otherId);

    Collection<User> getAllUsers();

    User addUser(User user);

    User updateUser(User user);

    User getUserById(Integer id);
}
