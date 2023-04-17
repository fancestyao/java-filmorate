package ru.yandex.practicum.filmorate.storage.classes;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotValidException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@Getter
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private int id = 1;

    @Override
    public User addUser(User user) {
        userValidation(user);
        nameUserValidation(user);
        user.setId(generateId());
        users.put(user.getId(), user);
        log.info("Пользователь успешно добавлен {}.", user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        userValidation(user);
        idUserValidation(user);
        nameUserValidation(user);
        users.put(user.getId(), user);
        log.info("Пользователь успешно обновлен {}.", user);
        return user;
    }

    @Override
    public Map<Integer, User> getAllUsers() {
        log.info("Успешно отправлен список всех пользователей.");
        return users;
    }

    @Override
    public User getUserById(Integer id) {
        if (!users.containsKey(id)) {
            throw new UserNotFoundException("Пользователь с id" + id + " не найден.");
        }
        log.info("Успешно отправлен пользователь с id = {}.", id);
        return users.get(id);
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
            log.warn("Ошибка в поиске пользователя.");
            throw new UserNotFoundException("Пользователь ранее не был зарегистрирован.");
        }
    }

    private void nameUserValidation(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
    }

    private int generateId() {
        return id++;
    }
}
