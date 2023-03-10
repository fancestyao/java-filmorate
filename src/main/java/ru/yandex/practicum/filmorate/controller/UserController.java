package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmNotValidException;
import ru.yandex.practicum.filmorate.exception.UserNotValidException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final HashMap<Integer, User> users = new HashMap<>();
    private int id = 1;

    private int generateId() {
        return id++;
    }

    private void userValidation(User user) {
        if (user.getEmail() == null
                || user.getEmail().isEmpty()
                || !user.getEmail().contains("@")) {
            log.warn("Ошибка валидации пользователя.");
            throw new UserNotValidException("Ошибка регистрации почты пользователя.");
        } else if (user.getLogin() == null
                || user.getLogin().isEmpty()
                || user.getLogin().contains(" ")) {
            log.warn("Ошибка валидации пользователя.");
            throw new UserNotValidException("Ошибка регистрации логина пользователя.");
        } else if (user.getBirthday() == null || user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Ошибка валидации пользователя.");
            throw new UserNotValidException("Ошибка регистрации даты рождения пользователя.");
        }
    }

    private void idUserValidation(User user) {
        if (!users.containsKey(user.getId())) {
            log.warn("Ошибка валидации пользователя.");
            throw new FilmNotValidException("Пользователь ранее не был зарегистрирован.");
        }
    }

    private void nameUserValidation(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        userValidation(user);
        nameUserValidation(user);
        user.setId(generateId());
        users.put(user.getId(), user);
        log.info("Пользователь успешно добавлен {}.", user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        userValidation(user);
        idUserValidation(user);
        nameUserValidation(user);
        users.put(user.getId(), user);
        log.info("Пользователь успешно обновлен {}.", user);
        return user;
    }

    @GetMapping
    public ArrayList<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}
