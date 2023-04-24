package ru.yandex.practicum.filmorate.service.classes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.GenresDBStorage;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.service.interfaces.GenreService;

import java.util.List;

@Primary
@Service
@Slf4j
@RequiredArgsConstructor
public class GenresDBService implements GenreService {
    private final GenresDBStorage genresDBStorage;

    public List<FilmGenre> getAllGenres() {
        log.info("Запрос на получение всех жанров в сервисе.");
        return genresDBStorage.getAllGenres();
    }

    public FilmGenre getGenreById(Integer id) {
        log.info("Запрос на получение жанра с id = {} в сервисе.", id);
        return genresDBStorage.getGenreById(id);
    }
}
