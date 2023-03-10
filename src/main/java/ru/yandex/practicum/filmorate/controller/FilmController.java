package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmNotValidException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private static final LocalDate DATE_OF_FIRST_FILM = LocalDate.of(1895, Month.DECEMBER, 28);
    private int id = 1;

    private int generateId() {
        return id++;
    }

    private void filmValidation(Film film) {
        if (film.getName() == null || film.getName().isEmpty()) {
            log.warn("Ошибка валидации фильма.");
            throw new FilmNotValidException("Ошибка регистрации названия фильма.");
        } else if (film.getDescription() == null || film.getDescription().length() > 200 ) {
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
            log.warn("Ошибка валидации фильма.");
            throw new FilmNotValidException("Фильм ранее не был зарегистрирован.");
        }
    }
    
    @PostMapping
    public Film addFilm(@RequestBody Film film) {
        filmValidation(film);
        film.setId(generateId());
        films.put(film.getId(), film);
        log.info("Фильм успешно добавлен {}.", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        filmValidation(film);
        idFilmValidation(film);
        films.put(film.getId(), film);
        log.info("Фильм успешно обновлен {}.", film);
        return film;
    }

    @GetMapping
    public ArrayList<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }
}
