package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.service.classes.GenresDBService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/genres")
public class GenreController {
    private final GenresDBService genresDBService;

    @GetMapping
    public List<FilmGenre> getAllGenres() {
        return genresDBService.getAllGenres();
    }

    @GetMapping("/{id}")
    public FilmGenre getGenreById(@PathVariable Integer id) {
        return genresDBService.getGenreById(id);
    }
}
