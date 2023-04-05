package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.classes.InMemoryFilmStorage;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;
    private final InMemoryFilmStorage filmStorage;

    @GetMapping
    public List<Film> getFilms() {
        return filmStorage.getAllFilms();
    }

    @PostMapping
    public Film addFilm(@RequestBody Film film) {
        return filmStorage.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        log.info("Пришел запрос на обновление фильма");
        return filmStorage.updateFilm(film);
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable Integer id) {
        return filmStorage.getFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Integer id, @PathVariable Integer userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable Integer id, @PathVariable Integer userId) {
        filmService.removeLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getTopFilms(@RequestParam(defaultValue = "0") Integer count) {
        return filmService.showTopFilms(count);
    }
}
