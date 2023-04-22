package ru.yandex.practicum.filmorate.service.classes;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class InMemoryUserService {
    private final UserStorage userStorage;

    public void addFriend(Integer id, Integer friendId) {
        User user1 = userStorage.getUserById(id);
        User user2 = userStorage.getUserById(friendId);
        user1.addFriend(friendId);
        user2.addFriend(id);
        log.info("Пользователь {} и пользователь {} теперь друзья.", user1.getName(), user2.getName());
    }

    public void removeFriend(Integer id, Integer friendId) {
        User user1 = userStorage.getUserById(id);
        User user2 = userStorage.getUserById(friendId);
        user1.getFriends().remove(friendId);
        user2.getFriends().remove(id);
        log.info("Пользователь {} и пользователь {} больше не друзья.", user1.getName(), user2.getName());
    }

    public List<User> showMutualFriends(Integer id, Integer friendId) {
        log.info("Успешно получен лист общих друзей.");
        return userStorage.getUserById(id).getFriend()
                .stream()
                .filter(userStorage.getUserById(friendId).getFriend()::contains)
                .map(userStorage::getUserById)
                .collect(Collectors.toList());
    }

    public List<User> showAllFriends(Integer id) {
        if (!userStorage.getAllUsers().containsKey(id)) {
            throw new UserNotFoundException("Пользователя с id" + id + " не существует.");
        }
        log.info("Успешно получен список всех друзей пользователя c id{}", id);
        return userStorage.getAllUsers().get(id).getFriends().stream().filter(userStorage.getAllUsers()::containsKey)
                .map(userStorage.getAllUsers()::get).collect(Collectors.toList());
    }

    public User addUser(User user) {
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public User getUserById(Integer id) {
        return userStorage.getUserById(id);
    }

    public Map<Integer, User> getAllUsers() {
        return userStorage.getAllUsers();
    }
}