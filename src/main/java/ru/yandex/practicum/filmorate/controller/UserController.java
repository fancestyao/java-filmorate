package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserNotValidException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    public final HashMap<Integer, User> users = new HashMap<>();
    private int id = 1;

    private int generateId() {
        return id++;
    }
    private boolean isValid(User user) {
        if (user.getEmail().isEmpty()
                || !user.getEmail().contains("@")
                || user.getLogin().isEmpty()
                || user.getLogin().contains(" ")
                || user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Ошибка валидации пользователя.");
            return false;
        } else {
            return true;
        }
    }

    @PostMapping()
    public User addUser(@RequestBody User user) {
        if (isValid(user)) {
            if (user.getName() == null || user.getName().isEmpty()) {
                user.setName(user.getLogin());
            }
            user.setId(generateId());
            users.put(user.getId(), user);
            log.info("Пользователь успешно добавлен.");
        } else {
            throw new UserNotValidException();
        }
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.info("Пользователь успешно обновлен");
        } else {
            throw new UserNotValidException();
        }
        return user;
    }

    @GetMapping()
    public ArrayList<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}
