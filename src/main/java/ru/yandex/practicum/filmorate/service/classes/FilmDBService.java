package ru.yandex.practicum.filmorate.service.classes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmDBStorage;
import ru.yandex.practicum.filmorate.exception.LikeNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.interfaces.FilmService;

import java.util.List;

@Primary
@Service
@Slf4j
@RequiredArgsConstructor
public class FilmDBService implements FilmService {
    private final FilmDBStorage filmDbStorage;

    public void addLike(Integer filmId, Integer userId) {
        String sqlQuery = "INSERT INTO LIKES(USER_ID, FILM_ID) " +
                          "VALUES (?, ?)";
        filmDbStorage.jdbcAddLike(sqlQuery, filmId, userId);
        log.info("Оценка успешно добавлена.");
    }

    public void removeLike(Integer filmId, Integer userId) {
        String sqlQuery = "DELETE FROM LIKES WHERE USER_ID = ? AND FILM_ID = ?";
        int count = filmDbStorage.jdbcRemoveLike(sqlQuery, userId, filmId);
        log.info("Оценка успешно удалена.");
        if (count == 0) {
            log.info("Оценка не найдена.");
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
        log.info("Успешно получен список лучших фильмов.");
        return filmDbStorage.jdbcShowTopFilms(sqlQuery, count);
    }

    public Film addFilm(Film film) {
        return filmDbStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        return filmDbStorage.updateFilm(film);
    }

    public List<Film> getAllFilms() {
        return filmDbStorage.getAllFilms();
    }

    public Film getFilmById(Integer id) {
        return filmDbStorage.getFilmById(id);
    }
}
