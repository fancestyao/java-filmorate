package ru.yandex.practicum.filmorate.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.*;

import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ExceptHandler {
    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> validationError(final ValidationException e) {
        log.info(e.getMessage());
        return new ResponseEntity<>(Map.of("Ошибка валидации.", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> userNotValidException(final UserNotValidException e) {
        log.info(e.getMessage());
        return new ResponseEntity<>(Map
                .of("Пользователя не удается валидировать.", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> filmNotValidException(final FilmNotValidException e) {
        log.info(e.getMessage());
        return new ResponseEntity<>(Map
                .of("Фильм не удается валидировать.", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> userNotFoundException(final UserNotFoundException e) {
        log.info(e.getMessage());
        return new ResponseEntity<>(Map.of("Пользователь не найден.", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> likeNotFoundException(final LikeNotFoundException e) {
        log.info(e.getMessage());
        return new ResponseEntity<>(Map.of("Лайк не найден.", e.getMessage()), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> filmNotFoundException(final FilmNotFoundException e) {
        log.info(e.getMessage());
        return new ResponseEntity<>(Map.of("Фильм не найден.", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> MPANotFoundException(final MPANotFoundException e) {
        log.info(e.getMessage());
        return new ResponseEntity<>(Map.of("Рейтинг не найден.", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> GenreNotFoundException(final GenreNotFoundException e) {
        log.info(e.getMessage());
        return new ResponseEntity<>(Map.of("Жанр не найден.", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> serverError(final Throwable e) {
        log.info(e.getMessage());
        return new ResponseEntity<>(Map.of("Ошибка сервера.", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
