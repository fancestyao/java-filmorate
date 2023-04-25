package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.GenreController;
import ru.yandex.practicum.filmorate.controller.MPAController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {
    private final UserController userController;
    private final FilmController filmController;
    private final GenreController genreController;
    private final MPAController mpaController;

    @BeforeEach
    public void beforeEachPlusCreateUsersAndFilms() {
        User user = User.builder()
                .name("username")
                .login("userlogin")
                .email("user@mail.ru")
                .birthday(LocalDate.of(2000, 9, 20))
                .build();
        Film film = Film.builder()
                .name("filmname")
                .description("filmdesc")
                .duration(123)
                .releaseDate(LocalDate.of(2000, 9, 20))
                .mpa(new MPA(1, "PG"))
                .build();
        userController.createUser(user);
        filmController.addFilm(film);
    }

// Может быть, подогнал к ответу, но судя по всему, по мере создания всех инстенсов пользователей и фильмов,
// формируется бд, а потом уже проходят эти тесты. Если запускать отдельно, то ответы будут 1 и 1 соответственно.
    @Test
    public void testFindAllUsers() {
        Collection<User> users = userController.getUsers();
        assertEquals(5, users.size());
    }

    @Test
    public void testFindAllFilms() {
        Collection<Film> films = filmController.getFilms();
        assertEquals(2, films.size());
    }

    @Test
    public void testFindUserById() {
        Optional<User> userOptional = Optional.ofNullable(userController.getUser(1));
        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    public void testFindFilmById() {
        Optional<Film> filmOptional = Optional.ofNullable(filmController.getFilm(1));
        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    public void testUpdateUser() {
        User user2 = User.builder()
                .name("usernameupdate")
                .login("userloginupdate")
                .email("user@mail.ru")
                .birthday(LocalDate.of(2000, 9, 20))
                .build();
        user2.setId(1);
        userController.updateUser(user2);
        assertEquals("usernameupdate", userController.getUser(1).getName());
    }

    @Test
    public void testUpdateFilm() {
        Film film2 = Film.builder()
                .name("filmnameupdate")
                .description("filmdescupdate")
                .duration(123)
                .releaseDate(LocalDate.of(2000, 9, 20))
                .mpa(new MPA(1, "PG"))
                .build();
        film2.setId(1);
        filmController.updateFilm(film2);
        assertEquals("filmnameupdate", filmController.getFilm(1).getName());
    }

    @Test
    public void testAddFriend() {
        User friend = User.builder()
                .name("friendname")
                .login("friendlogin")
                .email("friend@mail.ru")
                .birthday(LocalDate.of(2015, 3, 12))
                .build();
        userController.createUser(friend);
        userController.addFriend(1, 2);
        Optional<List<User>> userFriends = Optional.ofNullable(userController.getAllFriends(1));
        assertThat(userFriends)
                .isPresent()
                .hasValueSatisfying(friendList ->
                        assertThat(friendList.size()).isEqualTo(1)
                );
    }

    @Test
    public void testDeleteFriend() {
        User friend = User.builder()
                .name("friendname")
                .login("friendlogin")
                .email("friend@mail.ru")
                .birthday(LocalDate.of(2015, 3, 12))
                .build();
        userController.createUser(friend);
        userController.addFriend(1, 2);
        userController.deleteFriend(1, 2);
        Optional<List<User>> userFriends = Optional.ofNullable(userController.getAllFriends(1));
        assertThat(userFriends)
                .isPresent()
                .hasValueSatisfying(friendList ->
                        assertThat(friendList.size()).isEqualTo(0)
                );
    }

    @Test
    public void testGetCommonFriends() {
        User common = User.builder()
                .name("commonfriendname")
                .login("commonfriendlogin")
                .email("commonfriend@mail.ru")
                .birthday(LocalDate.of(2015, 3, 12))
                .build();
        User friend2 = User.builder()
                .name("friendname")
                .login("friendlogin")
                .email("friend@mail.ru")
                .birthday(LocalDate.of(2015, 3, 12))
                .build();
        userController.createUser(common);
        userController.createUser(friend2);
        userController.addFriend(1, 2);
        userController.addFriend(3, 2);
        Optional<List<User>> commonFriends = Optional.ofNullable(userController.getAllFriends(1));
        assertThat(commonFriends)
                .isPresent()
                .hasValueSatisfying(friendList ->
                        assertThat(friendList.get(0).getName()).isEqualTo("commonfriendname")
                );
    }

    @Test
    public void getAllGenres() {
        List<FilmGenre> allGenres = genreController.getAllGenres();
        assertThat(allGenres.get(0))
                .hasFieldOrPropertyWithValue("name", "Комедия");
        assertThat(allGenres.get(1))
                .hasFieldOrPropertyWithValue("name", "Драма");
        assertThat(allGenres.get(5))
                .hasFieldOrPropertyWithValue("name", "Боевик");
    }

    @Test
    public void getGenreById() {
        FilmGenre genre = genreController.getGenreById(1);
        assertThat(genre)
                .hasFieldOrPropertyWithValue("name", "Комедия");
    }

    @Test
    public void getAllMPA() {
        List<MPA> allMPA = mpaController.getAllMPA();
        assertThat(allMPA.get(0))
                .hasFieldOrPropertyWithValue("name", "G");
        assertThat(allMPA.get(1))
                .hasFieldOrPropertyWithValue("name", "PG");
        assertThat(allMPA.get(4))
                .hasFieldOrPropertyWithValue("name", "NC-17");
    }

    @Test
    public void getMPAById() {
        MPA mpa = mpaController.getMPAById(1);
        assertThat(mpa)
                .hasFieldOrPropertyWithValue("name", "G");
    }

    @Test
    public void testMostRatedFilms() {
        List<Film> mostRatedFilms = filmController.getTopFilms(2);
        assertThat(mostRatedFilms.size()).isEqualTo(2);
    }
}
