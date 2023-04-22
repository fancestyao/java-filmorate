package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.classes.UserDBService;

import java.util.Collection;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserDBService userDBService;

    @GetMapping
    public Collection<User> getUsers() {
        return userDBService.getAllUsers().values();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userDBService.addUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        return userDBService.updateUser(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Integer id) {
        return userDBService.getUserById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        userDBService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        userDBService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getAllFriends(@PathVariable Integer id) {
        return userDBService.getAllFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getMutualFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        return userDBService.getMutualFriends(id, otherId);
    }
}
