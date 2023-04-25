package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.interfaces.UserService;

import java.util.Collection;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final UserService userDBService;

    @GetMapping
    public Collection<User> getUsers() {
        log.info("Пришел запрос на получение всех пользователей.");
        return userDBService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        log.info("Пришел запрос на добавление пользователя.");
        return userDBService.addUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        log.info("Пришел запрос на обновление пользователя.");
        return userDBService.updateUser(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Integer id) {
        log.info("Пришел запрос на получение пользователя с id = {}.", id);
        return userDBService.getUserById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Пришел запрос на добавление в друзья пользователя с id = {} " +
                "от пользователя с id = {}.", id, friendId);
        userDBService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Пришел запрос на удаление из друзей пользователя с id = {} " +
                "от пользователя с id = {}.", id, friendId);
        userDBService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getAllFriends(@PathVariable Integer id) {
        log.info("Пришел запрос на получение списка всех друзей пользователя с id = {}.", id);
        return userDBService.getAllFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getMutualFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        log.info("Пришел запрос на получение списка общих друзей " +
                "между пользователями с id = {} и id = {}.", id, otherId);
        return userDBService.getMutualFriends(id, otherId);
    }
}
