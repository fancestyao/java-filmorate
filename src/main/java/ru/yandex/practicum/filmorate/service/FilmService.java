package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.comparator.RateComparator;
import ru.yandex.practicum.filmorate.exception.FilmLikeException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public void addLike(Integer filmId, Integer userId) {
        Film film = filmStorage.getFilmById(filmId);
        User user = userStorage.getUserById(userId);
        if (!film.getLikesRate().contains(user.getId())) {
            film.getLikesRate().add(userId);
            film.addLike();
            log.info("Пользователь '{}' оценил фильм '{}'", user.getName(), film.getName());
        } else {
            throw new FilmLikeException("Пользователь уже оценил данный фильм.");
        }
    }

    public void removeLike(Integer filmId, Integer userId) {
        Film film = filmStorage.getFilmById(filmId);
        User user = userStorage.getUserById(userId);
        if (film.getLikesRate().contains(user.getId())) {
            film.getLikesRate().remove(user.getId());
            film.removeLike();
            log.info("Пользователь '{}' убрал оценку с фильма '{}'", user.getName(), film.getName());
        } else {
            throw new FilmLikeException("Пользователь еще не оценил данный фильм.");
        }
    }

    public List<Film> showTopFilms(Integer countOfTopFilms) {
        List<Film> result = new ArrayList<>(filmStorage.getAllFilms());
        if (countOfTopFilms == 0) {
            if (filmStorage.getAllFilms().size() <= 10) {
                result.sort(new RateComparator());
                log.info("Успешно получен топ фильмов.");
                return result;
            } else {
                result.sort(new RateComparator());
                log.info("Успешно получен топ фильмов со значением по умолчанию.");
                return result.subList(0, 10);
            }
        } else {
            result.sort(new RateComparator());
            log.info("Успешно получен топ фильмов с count = {}.", countOfTopFilms);
            return result.subList(0, countOfTopFilms);
        }
    }
}

