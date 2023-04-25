package ru.yandex.practicum.filmorate.service.interfaces;

import ru.yandex.practicum.filmorate.model.FilmGenre;

import java.util.List;

public interface GenreService {
    List<FilmGenre> getAllGenres();

    FilmGenre getGenreById(Integer id);
}
