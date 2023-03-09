package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.FilmNotValidException;
import ru.yandex.practicum.filmorate.exception.UserNotValidException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.Month;

@SpringBootTest
class FilmorateApplicationTests {
	static User defaultUser;
	static Film defaultFilm;
	static FilmController filmController;
	static UserController userController;

	@BeforeAll
	static void BeforeAll() {
		userController = new UserController();
		filmController = new FilmController();
		defaultUser = new User(1,
				"testEmail@test.ru",
				"testLogin",
				"testName",
				LocalDate.of(1999, 5, 18));
		defaultFilm = new Film(1,
				"testName",
				"testDescription",
				LocalDate.now(),
				30L);
	}

	@Test
	void shouldCreateUserDefaultBehaviourCase() {
		Assertions.assertEquals(defaultUser, userController.addUser(defaultUser));
		Assertions.assertTrue(userController.users.containsValue(defaultUser));
	}

	@Test
	void shouldThrowUserNotValidExceptionIfUsersEmailIsEmptyCase() {
		User userWithEmptyEmail = new User(2,
				"",
				"testLogin",
				"testName",
				LocalDate.of(1999, 5, 18));
		Assertions.assertThrows(
				UserNotValidException.class,
				() -> userController.addUser(userWithEmptyEmail)
		);
	}

	@Test
	void shouldThrowUserNotValidExceptionIfUserWithoutAtSymbolInEmailCase() {
		User userWithoutAtSymbol = new User(2,
				"testEmailTest.ru",
				"testLogin",
				"testName",
				LocalDate.of(1999, 5, 18));
		Assertions.assertThrows(
				UserNotValidException.class,
				() -> userController.addUser(userWithoutAtSymbol)
		);
	}

	@Test
	void shouldThrowUserNotValidExceptionIfUsersLoginIsEmptyCase() {
		User userWithEmptyLogin = new User(2,
				"test@Emailtest.ru",
				"",
				"testName",
				LocalDate.of(1999, 5, 18));
		Assertions.assertThrows(
				UserNotValidException.class,
				() -> userController.addUser(userWithEmptyLogin)
		);
	}

	@Test
	void shouldThrowUserNotValidExceptionIfUsersLoginContainsWhiteSpacesCase() {
		User userWithEmptyLogin = new User(2,
				"test@Emailtest.ru",
				"creatively designed login",
				"testName",
				LocalDate.of(1999, 5, 18));
		Assertions.assertThrows(
				UserNotValidException.class,
				() -> userController.addUser(userWithEmptyLogin)
		);
	}

	@Test
	void shouldNotThrowUserNotValidExceptionIfUsersNameIsEmptyCase() {
		User userWithEmptyLogin = new User(2,
				"test@Emailtest.ru",
				"testLogin",
				"",
				LocalDate.of(1999, 5, 18));
		userController.addUser(userWithEmptyLogin);
		Assertions.assertEquals(userWithEmptyLogin.getName(),
				userController.users.get(2).getName());
	}

	@Test
	void shouldThrowUserNotValidExceptionIfUsersBirthdayIsInTheFutureCase() {
		User userIsTerminator = new User(2,
				"test@Emailtest.ru",
				"testLogin",
				"testName",
				LocalDate.of(2023, 5, 18));
		Assertions.assertThrows(
				UserNotValidException.class,
				() -> userController.addUser(userIsTerminator)
		);
	}

	@Test
	void shouldCreateFilmDefaultBehaviourCase() {
		Assertions.assertEquals(defaultFilm, filmController.addFilm(defaultFilm));
		Assertions.assertTrue(filmController.films.containsValue(defaultFilm));
	}

	@Test
	void shouldThrowFilmNotValidExceptionIfFilmsNameIsEmptyCase() {
		Film filmWithEmptyName = new Film(2,
				"",
				"testDesc",
				LocalDate.of(1999, 5, 18),
				120);
		Assertions.assertThrows(
				FilmNotValidException.class,
				() -> filmController.addFilm(filmWithEmptyName)
		);
	}

	@Test
	void shouldThrowFilmNotValidExceptionIfFilmsDescIsLargerThan200SymbolsCase() {
		Film filmWithHugeDescription = new Film(2,
				"testName",
				"&Lorem ipsum dolor sit amet, consectetuer adipiscing elit." +
						" Aenean commodo ligula eget dolor." +
						" Aenean massa." +
						" Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus." +
						" Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem." +
						" Nulla consequat massa quis enim." +
						" Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu." +
						" In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo." +
						" Nullam dictum felis eu pede mollis pretium. Integer tincidunt." +
						" Cras dapibus. Vivamus elementum semper nisi." +
						" Aenean vulputate eleifend tellus." +
						" Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim." +
						" Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus." +
						" Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum." +
						" Aenean imperdiet. Etiam ultricies nisi vel augue." +
						" Curabitur ullamcorper ultricies nisi. Nam eget dui." +
						" Etiam rhoncus." +
						" Maecenas tempus, tellus eget condimentum rhoncus," +
						" sem quam semper libero, sit amet adipiscing sem neque sed ipsum." +
						" Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem." +
						" Maecenas nec odio et ante tincidunt tempus." +
						" Donec vitae sapien ut libero venenatis faucibus." +
						" Nullam quis ante." +
						" Etiam sit amet orci eget eros faucibus tincidunt." +
						" Duis leo. Sed fringilla mauris sit amet nibh." +
						" Donec sodales sagittis magna." +
						" Sed consequat, leo eget bibendum sodales, augue velit cursus nunc.",
				LocalDate.of(1999, 5, 18),
				120);
		Assertions.assertThrows(
				FilmNotValidException.class,
				() -> filmController.addFilm(filmWithHugeDescription)
		);
	}

	@Test
	void shouldThrowFilmNotValidExceptionIfFilmsReleaseDateIsEarlierThan28Dec1895Case() {
		Film filmIsMadeBC = new Film(2,
				"testName",
				"testDesc",
				LocalDate.of(1895, Month.DECEMBER, 27),
				120);
		Assertions.assertThrows(
				FilmNotValidException.class,
				() -> filmController.addFilm(filmIsMadeBC)
		);
	}

	@Test
	void shouldThrowFilmNotValidExceptionIfFilmsDurationIsNegativeCase() {
		Film filmWithNegativeDuration = new Film(2,
				"testName",
				"testDesc",
				LocalDate.of(2000, Month.DECEMBER, 27),
				-1);
		Assertions.assertThrows(
				FilmNotValidException.class,
				() -> filmController.addFilm(filmWithNegativeDuration)
		);
	}
}
