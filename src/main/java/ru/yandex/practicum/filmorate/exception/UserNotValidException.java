package ru.yandex.practicum.filmorate.exception;

public class UserNotValidException extends RuntimeException {

    public UserNotValidException(String message) {
        super(message);
    }
}
