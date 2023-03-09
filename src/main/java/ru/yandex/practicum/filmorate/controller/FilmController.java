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
    public final HashMap<Integer, Film> films = new HashMap<>();
    private final LocalDate TO_COMPARE = LocalDate.of(1895, Month.DECEMBER, 28);
    private int id = 1;

    private int generateId() {
        return id++;
    }

    private boolean isValid(Film film) {
        if (film.getName().isEmpty() 
                || film.getDescription().length() > 200 
                || film.getReleaseDate().isBefore(TO_COMPARE)
                || film.getDuration() < 0) {
            log.warn("Ошибка валидации фильма.");
            return false;
        } else {
            return true;
        }
    }
    
    @PostMapping()
    public Film addFilm(@RequestBody Film film) {
        if (isValid(film)) {
            film.setId(generateId());
            films.put(film.getId(), film);
            log.info("Фильм успешно добавлен.");
        } else {
            throw new FilmNotValidException();
        }
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Фильм успешно обновлен");
        } else {
            throw new FilmNotValidException();
        }
        return film;
    }

    @GetMapping()
    public ArrayList<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }
}
