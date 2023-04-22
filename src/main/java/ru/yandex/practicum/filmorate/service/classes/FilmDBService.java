package ru.yandex.practicum.filmorate.service.classes;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmDBStorage;
import ru.yandex.practicum.filmorate.exception.LikeNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;

import java.util.List;

@Data
@Primary
@Service
@RequiredArgsConstructor
public class FilmDBService implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final FilmDBStorage filmDbStorage;

    public void addLike(Integer filmId, Integer userId) {
        String sqlQuery = "INSERT INTO LIKES(USER_ID, FILM_ID) " +
                          "VALUES (?, ?)";
        jdbcTemplate.update(sqlQuery, userId, filmId);
    }

    public void removeLike(Integer filmId, Integer userId) {
        String sqlQuery = "DELETE FROM LIKES WHERE USER_ID = ? AND FILM_ID = ?";
        int count = jdbcTemplate.update(sqlQuery, userId, filmId);
        if (count == 0) {
            throw new LikeNotFoundException("Оценка не найдена.");
        }
    }

    public List<Film> showTopFilms(Integer count) {
        String sqlQuery = "SELECT F.ID, F.NAME, F.DESCRIPTION, F.RELEASE_DATE, " +
                          "F.DURATION, F.MPA_ID, M.MPA_NAME " +
                          "FROM FILM AS F " +
                          "LEFT JOIN LIKES AS L ON F.ID = L.FILM_ID " +
                          "LEFT JOIN MPA AS M ON F.MPA_ID = M.MPA_ID " +
                          "GROUP BY F.NAME " +
                          "ORDER BY COUNT(L.USER_ID) DESC " +
                          "LIMIT ?";
        return jdbcTemplate.query(sqlQuery, filmDbStorage::mapRowToFilm, count);
    }

    @Override
    public Film addFilm(Film film) {
        return filmDbStorage.addFilm(film);
    }

    @Override
    public Film updateFilm(Film film) {
        return filmDbStorage.updateFilm(film);
    }

    @Override
    public List<Film> getAllFilms() {
        return filmDbStorage.getAllFilms();
    }

    @Override
    public Film getFilmById(Integer id) {
        return filmDbStorage.getFilmById(id);
    }
}
