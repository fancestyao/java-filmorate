package ru.yandex.practicum.filmorate.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.*;

import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ExceptHandler {
    @ExceptionHandler
    public ResponseEntity<Map<String, String>> validationError(final ValidationException e) {
        log.info(e.getMessage());
        return new ResponseEntity<>(Map.of("Ошибка валидации", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> userNotFoundException(final UserNotFoundException e) {
        log.info(e.getMessage());
        return new ResponseEntity<>(Map.of("Пользователь не найден", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> filmNotFoundException(final FilmNotFoundException e) {
        log.info(e.getMessage());
        return new ResponseEntity<>(Map.of("Фильм не найден", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> serverError(final InternalServerError e) {
        log.info(e.getMessage());
        return new ResponseEntity<>(Map.of("Ошибка сервера", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
