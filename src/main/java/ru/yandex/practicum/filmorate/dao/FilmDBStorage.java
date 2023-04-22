package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.FilmNotValidException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Primary
@Slf4j
public class FilmDBStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private static final LocalDate DATE_OF_FIRST_FILM = LocalDate.of(1895, Month.DECEMBER, 28);

    @Override
    public Film addFilm(Film film) {
        filmValidation(film);
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("FILM")
                .usingGeneratedKeyColumns("ID");
        int filmId = simpleJdbcInsert.executeAndReturnKey(film.toMap()).intValue();
        Optional<List<FilmGenre>> filmGenres = Optional.ofNullable(film.getGenres());
        setGenresToFilm(filmGenres, filmId);
        return getFilmById(filmId);
    }

    @Override
    public Film updateFilm(Film film) {
        filmValidation(film);
        String sqlQuery = "UPDATE FILM SET NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?," +
                " DURATION = ?, MPA_ID = ? WHERE ID = ?";
        jdbcTemplate.update(sqlQuery,
                            film.getName(),
                            film.getDescription(),
                            film.getReleaseDate(),
                            film.getDuration(),
                            film.getMpa().getId(),
                            film.getId());
        Optional<List<FilmGenre>> filmGenres = Optional.ofNullable(film.getGenres());
        setGenresToFilm(filmGenres, film.getId());
        return getFilmById(film.getId());
    }

    @Override
    public List<Film> getAllFilms() {
        String sqlQuery = "SELECT F.ID, F.NAME, F.DESCRIPTION, F.RELEASE_DATE, F.DURATION, " +
                          "F.MPA_ID, MPA_NAME FROM FILM AS F " +
                          "JOIN MPA AS M ON F.MPA_ID = M.MPA_ID";
        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm);
    }

    @Override
    public Film getFilmById(Integer id) {
        String sqlQuery = "SELECT F.ID, F.NAME, F.DESCRIPTION, F.RELEASE_DATE, F.DURATION, F.MPA_ID, M.MPA_NAME" +
                " FROM FILM AS F" +
                " JOIN MPA AS M ON F.MPA_ID = M.MPA_ID" +
                " WHERE F.ID = ?";
        Film film;
        try {
            film = jdbcTemplate.queryForObject(sqlQuery, this::mapRowToFilm, id);
        } catch (DataAccessException e) {
            throw new FilmNotFoundException("Фильм не найден.");
        }
        return film;
    }

    public Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        return Film.builder()
                .id(resultSet.getInt("ID"))
                .name(resultSet.getString("NAME"))
                .description(resultSet.getString("DESCRIPTION"))
                .releaseDate(resultSet.getDate("RELEASE_DATE").toLocalDate())
                .duration(resultSet.getInt("DURATION"))
                .mpa(new MPA(resultSet.getInt("MPA_ID"), resultSet.getString("MPA_NAME")))
                .genres(getGenresOfFilm(resultSet.getInt("ID")))
                .build();
    }

    private List<FilmGenre> getGenresOfFilm(int filmId) {
        List<FilmGenre> genresList;
        String sqlQuery = "SELECT G.GENRE_ID, G.GENRE_NAME FROM GENRE AS G " +
                "JOIN FILM_GENRE AS FG ON FG.GENRE_ID = G.GENRE_ID WHERE FG.FILM_ID = ?";
        genresList = jdbcTemplate.query(sqlQuery, (rs, rowNum) ->
                new FilmGenre(rs.getInt("GENRE_ID"), rs.getString("GENRE_NAME")), filmId);
        genresList.sort(Comparator.comparingInt(FilmGenre::getId));
        return genresList;
    }

    private void setGenresToFilm(Optional<List<FilmGenre>> filmGenres, int filmId) {
        String sqlQuery1 = "DELETE FROM FILM_GENRE WHERE FILM_ID = ?";
        jdbcTemplate.update(sqlQuery1, filmId);
        if (filmGenres.isPresent() && !filmGenres.get().isEmpty()) {
            for (FilmGenre genre : filmGenres.get()) {
                String sqlQuery2 = "MERGE INTO FILM_GENRE (GENRE_ID, FILM_ID) KEY (GENRE_ID, FILM_ID) VALUES (?, ?)";
                jdbcTemplate.update(sqlQuery2, genre.getId(), filmId);
            }
        }
    }

    private void filmValidation(Film film) {
        if (film.getName() == null || film.getName().isEmpty()) {
            log.warn("Ошибка валидации фильма.");
            throw new FilmNotValidException("Ошибка регистрации названия фильма.");
        } else if (film.getDescription() == null || film.getDescription().length() > 200) {
            log.warn("Ошибка валидации фильма.");
            throw new FilmNotValidException("Ошибка регистрации описания фильма.");
        } else if (film.getReleaseDate() == null || film.getReleaseDate().isBefore(DATE_OF_FIRST_FILM)) {
            log.warn("Ошибка валидации фильма.");
            throw new FilmNotValidException("Ошибка регистрации даты релиза фильма.");
        } else if (film.getDuration() < 0) {
            log.warn("Ошибка валидации фильма.");
            throw new FilmNotValidException("Ошибка регистрации длительности фильма.");
        }
    }
}
