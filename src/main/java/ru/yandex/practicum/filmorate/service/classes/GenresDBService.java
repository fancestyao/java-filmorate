package ru.yandex.practicum.filmorate.service.classes;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import ru.yandex.practicum.filmorate.dao.GenresDBStorage;
import ru.yandex.practicum.filmorate.model.FilmGenre;

import java.util.List;

@Data
@Primary
@Service
@RequiredArgsConstructor
public class GenresDBService {
    private final GenresDBStorage genresDBStorage;

    @GetMapping
    public List<FilmGenre> getAllGenres() {
        return genresDBStorage.getAllGenres();
    }

    @GetMapping("/{id}")
    public FilmGenre getGenreById(Integer id) {
        return genresDBStorage.getGenreById(id);
    }
}
