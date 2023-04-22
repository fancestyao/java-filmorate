package ru.yandex.practicum.filmorate.service.classes;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.UserDBStorage;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.util.List;
import java.util.Map;

@Data
@Primary
@Service
@RequiredArgsConstructor
public class UserDBService implements UserStorage{
    private final JdbcTemplate jdbcTemplate;
    private final UserDBStorage userDBStorage;

    public void addFriend(Integer id, Integer friendId) {
        String sqlQuery = "INSERT INTO FRIENDS(USER_ID, FRIEND_ID)" +
                "VALUES(?, ?)";
        try {
            jdbcTemplate.update(sqlQuery, id, friendId);
        } catch (DataAccessException e) {
            throw new UserNotFoundException("Пользователь не найден.");
        }
    }

    public void deleteFriend(Integer id, Integer friendId) {
        String sqlQuery = "DELETE FROM FRIENDS " +
                          "WHERE (USER_ID = ? AND FRIEND_ID = ?) OR (USER_ID = ? AND FRIEND_ID = ?)";
        jdbcTemplate.update(sqlQuery, id, friendId, friendId, id);
    }

    public List<User> getAllFriends(Integer id) {
        String sqlQuery = "SELECT * FROM USERS WHERE ID IN (SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID = ?)";
        return jdbcTemplate.query(sqlQuery, userDBStorage::mapRowToUser, id);
    }

    public List<User> getMutualFriends(Integer id, Integer otherId) {
        String sqlQuery = "SELECT * FROM FRIENDS AS FR1 " +
                          "JOIN FRIENDS AS FR2 ON FR1.FRIEND_ID = FR2.FRIEND_ID AND FR2.USER_ID = ?" +
                          "JOIN USERS AS U ON U.ID = FR1.FRIEND_ID " +
                          "WHERE FR1.USER_ID = ?";
        return jdbcTemplate.query(sqlQuery, userDBStorage::mapRowToUser, id, otherId);
    }

    @Override
    public User addUser(User user) {
        return userDBStorage.addUser(user);
    }

    @Override
    public User updateUser(User user) {
        return userDBStorage.updateUser(user);
    }

    @Override
    public Map<Integer, User> getAllUsers() {
        return userDBStorage.getAllUsers();
    }

    @Override
    public User getUserById(Integer id) {
        return userDBStorage.getUserById(id);
    }
}
