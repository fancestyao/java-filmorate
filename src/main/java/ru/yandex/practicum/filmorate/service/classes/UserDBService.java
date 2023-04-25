package ru.yandex.practicum.filmorate.service.classes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.UserDBStorage;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.interfaces.UserService;

import java.util.Collection;
import java.util.List;

@Primary
@Service
@Slf4j
@RequiredArgsConstructor
public class UserDBService implements UserService {
    private final UserDBStorage userDBStorage;

    public void addFriend(Integer id, Integer friendId) {
        String sqlQuery = "INSERT INTO FRIENDS(USER_ID, FRIEND_ID)" +
                "VALUES(?, ?)";
        try {
            log.info("Добавляем друга.");
            userDBStorage.jdbcAddFriendUpdate(sqlQuery, id, friendId);
        } catch (DataAccessException e) {
            log.info("Произошла ошибка добавления.");
            throw new UserNotFoundException("Пользователь не найден.");
        }
    }

    public void deleteFriend(Integer id, Integer friendId) {
        String sqlQuery = "DELETE FROM FRIENDS " +
                          "WHERE (USER_ID = ? AND FRIEND_ID = ?) OR (USER_ID = ? AND FRIEND_ID = ?)";
        userDBStorage.jdbcDeleteFriendUpdate(sqlQuery, id, friendId);
        log.info("Удаляем друга.");
    }

    public List<User> getAllFriends(Integer id) {
        String sqlQuery = "SELECT * FROM USERS WHERE ID IN (SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID = ?)";
        log.info("Получаем список всех друзей.");
        return userDBStorage.jdbcGetAllFriends(sqlQuery, id);
    }

    public List<User> getMutualFriends(Integer id, Integer otherId) {
        String sqlQuery = "SELECT * FROM FRIENDS AS FR1 " +
                          "JOIN FRIENDS AS FR2 ON FR1.FRIEND_ID = FR2.FRIEND_ID AND FR2.USER_ID = ?" +
                          "JOIN USERS AS U ON U.ID = FR1.FRIEND_ID " +
                          "WHERE FR1.USER_ID = ?";
        log.info("Получаем список общих друзей.");
        return userDBStorage.jdbcGetMutualFriends(sqlQuery, id, otherId);
    }

    public User addUser(User user) {
        return userDBStorage.addUser(user);
    }

    public User updateUser(User user) {
        return userDBStorage.updateUser(user);
    }

    public Collection<User> getAllUsers() {
        return userDBStorage.getAllUsers().values();
    }

    public User getUserById(Integer id) {
        return userDBStorage.getUserById(id);
    }
}
