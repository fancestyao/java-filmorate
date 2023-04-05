package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.classes.InMemoryUserStorage;

import java.util.Collection;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final InMemoryUserStorage userStorage;

    @GetMapping
    public Collection<User> getUsers() {
        return userStorage.getAllUsers().values();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userStorage.addUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        return userStorage.updateUser(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Integer id) {
        return userStorage.getUserById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void addFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        userService.removeFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllFriends(@PathVariable Integer id) {
        return userService.showAllFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getMutualFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        return userService.showMutualFriends(id, otherId);
    }
}
