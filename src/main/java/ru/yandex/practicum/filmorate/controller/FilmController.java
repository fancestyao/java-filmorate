package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.interfaces.FilmService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmDBService;

    @GetMapping
    public List<Film> getFilms() {
        log.info("Пришел запрос на получение всех фильмов.");
        return filmDBService.getAllFilms();
    }

    @PostMapping
    public Film addFilm(@RequestBody Film film) {
        log.info("Пришел запрос на добавление фильма.");
        return filmDBService.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        log.info("Пришел запрос на обновление фильма.");
        return filmDBService.updateFilm(film);
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable Integer id) {
        log.info("Пришел запрос на получение фильма с id = {}.", id);
        return filmDBService.getFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("Пришел запрос на добавление реакции фильма с id = {} от пользователя с id = {}.", id, userId);
        filmDBService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("Пришел запрос на удаление реакции фильма с id = {} от пользователя с id = {}.", id, userId);
        filmDBService.removeLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getTopFilms(@RequestParam(defaultValue = "10") Integer count) {
        log.info("Пришел запрос на получение топ фильмов.");
        return filmDBService.showTopFilms(count);
    }
}
