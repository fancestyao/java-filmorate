package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;

public interface FilmStorage {
    Film addFilm(Film film);

    Film updateFilm(Film film);

    ArrayList<Film> getAllFilms();

    Film getFilmById(Integer id);
}
