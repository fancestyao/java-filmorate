package ru.yandex.practicum.filmorate.storage.classes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.FilmNotValidException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private static final LocalDate DATE_OF_FIRST_FILM = LocalDate.of(1895, Month.DECEMBER, 28);
    private int id = 1;

    @Override
    public Film addFilm(Film film) {
        filmValidation(film);
        film.setId(generateId());
        films.put(film.getId(), film);
        log.info("Фильм успешно добавлен {}.", film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        filmValidation(film);
        idFilmValidation(film);
        films.put(film.getId(), film);
        log.info("Фильм успешно обновлен {}.", film);
        return film;
    }

    @Override
    public Film getFilmById(Integer id) {
        if (!films.containsKey(id) || id == null) {
            throw new FilmNotFoundException("Фильм с id" + id + " не найден.");
        }
        log.info("Фильм c id {} успешно получен.", id);
        return films.get(id);
    }

    @Override
    public List<Film> getAllFilms() {
        log.info("Успешно получен список всех фильмов.");
        return new ArrayList<>(films.values());
    }

    private void filmValidation(Film film) {
        if (film.getName() == null || film.getName().isEmpty()) {
            log.warn("Ошибка валидации фильма.");
            throw new FilmNotValidException("Ошибка регистрации названия фильма.");
        } else if (film.getDescription() == null || film.getDescription().length() > 200) {
            log.warn("Ошибка валидации фильма.");
            throw new FilmNotValidException("Ошибка регистрации описания фильма.");
        } else if (film.getReleaseDate() == null || film.getReleaseDate().isBefore(DATE_OF_FIRST_FILM)) {
            log.warn("Ошибка валидации фильма.");
            throw new FilmNotValidException("Ошибка регистрации даты релиза фильма.");
        } else if (film.getDuration() < 0) {
            log.warn("Ошибка валидации фильма.");
            throw new FilmNotValidException("Ошибка регистрации длительности фильма.");
        }
    }

    private void idFilmValidation(Film film) {
        if (!films.containsKey(film.getId())) {
            log.warn("Ошибка в поиске фильма.");
            throw new FilmNotFoundException("Фильм ранее не был зарегистрирован.");
        }
    }

    private int generateId() {
        return id++;
    }
}
