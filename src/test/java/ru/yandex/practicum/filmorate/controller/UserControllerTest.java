package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    UserController userController;
    User user;

    @BeforeEach
    void init() {
        userController = new UserController();
        user = new User();
        user.setName("name");
        user.setLogin("login");
        user.setEmail("nekto@email.com");
        user.setBirthday(LocalDate.of(2000, 12, 28));

    }

    @Test
    void validate_InvalidEmail_ShouldThrowValidationException() {
        user.setEmail("nekto_email.com");

        final ValidationException exception = assertThrows(ValidationException.class, () -> userController.validate(user));
        assertEquals("Электронная почта не может быть пустой и должна содержать символ @",
                exception.getMessage());
    }

    @Test
    void validate_BlankEmail_ShouldThrowValidationException() {
        user.setEmail("");

        final ValidationException exception = assertThrows(ValidationException.class, () -> userController.validate(user));
        assertEquals("Электронная почта не может быть пустой и должна содержать символ @",
                exception.getMessage());
    }

    @Test
    void validate_NullEmail_ShouldThrowValidationException() {
        user.setEmail(null);

        final ValidationException exception = assertThrows(ValidationException.class, () -> userController.validate(user));
        assertEquals("Электронная почта не может быть пустой и должна содержать символ @",
                exception.getMessage());
    }

    @Test
    void validate_BlankLogin_ShouldThrowValidationException() {
        user.setLogin("  ");

        final ValidationException exception = assertThrows(ValidationException.class, () -> userController.validate(user));
        assertEquals("Логин не может быть пустым и содержать пробелы",
                exception.getMessage());
    }

    @Test
    void validate_NullLogin_ShouldThrowValidationException() {
        user.setLogin(null);

        final ValidationException exception = assertThrows(ValidationException.class, () -> userController.validate(user));
        assertEquals("Логин не может быть пустым и содержать пробелы",
                exception.getMessage());
    }
    @Test
    void validate_BlankName_ShouldSetAsLogin() {
        user.setName("");

        userController.validate(user);
        assertEquals(user.getLogin(),user.getName());
    }

    @Test
    void validate_BirthdayInTheFuture_ShouldThrowValidationException() {
        user.setBirthday(LocalDate.MAX);

        final ValidationException exception = assertThrows(ValidationException.class, () -> userController.validate(user));
        assertEquals("Дата рождения не может быть в будущем",
                exception.getMessage());
    }

}