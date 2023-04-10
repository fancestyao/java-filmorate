package ru.yandex.practicum.filmorate.exception;

public class FilmNotValidException extends RuntimeException {
    public FilmNotValidException(String message) {
        super(message);
    }
}
