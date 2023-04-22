package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotValidException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
@Primary
@Slf4j
public class UserDBStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User addUser(User user) {
        userValidation(user);
        nameUserValidation(user);
        String sqlQuery = "INSERT INTO USERS(NAME, EMAIL, LOGIN, BIRTHDAY) " +
                "VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sqlQuery,
                    new String[]{"id"});
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getLogin());
            statement.setDate(4, Date.valueOf(user.getBirthday()));
        return statement;
        }, keyHolder);
        int id = Objects.requireNonNull(keyHolder.getKey()).intValue();
        return getUserById(id);
    }

    @Override
    public User updateUser(User user) {
        userValidation(user);
        nameUserValidation(user);
        String sqlQuery = "UPDATE USERS SET NAME = ?, EMAIL = ?, LOGIN = ?, BIRTHDAY = ? WHERE ID = ?";
        jdbcTemplate.update(sqlQuery,
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getBirthday(),
                user.getId());
        return getUserById(user.getId());
    }

    @Override
    public Map<Integer, User> getAllUsers() {
        Map<Integer, User> resultMap = new HashMap<>();
        String sqlQuery = "SELECT * FROM USERS";
        List<User> resultList = jdbcTemplate.query(sqlQuery, this::mapRowToUser);
        resultList.forEach(user -> resultMap.put(user.getId(), user));
        return resultMap;
    }

    @Override
    public User getUserById(Integer id) {
        String sqlQuery = "SELECT * FROM USERS WHERE ID = ?";
        User user;
        try {
            user = jdbcTemplate.queryForObject(sqlQuery, this::mapRowToUser, id);
        } catch (DataAccessException e) {
            throw new UserNotFoundException("Пользователь не найден.");
        }
        return user;
    }

    public User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("ID"))
                .name(resultSet.getString("NAME"))
                .login(resultSet.getString("LOGIN"))
                .email(resultSet.getString("EMAIL"))
                .birthday(resultSet.getDate("BIRTHDAY").toLocalDate())
                .build();
    }

    private void nameUserValidation(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
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
}
