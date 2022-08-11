package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    FilmController filmController;
    Film film;

    @BeforeEach
    void init() {
        filmController = new FilmController();
        film = new Film();
        film.setName("Film");
        film.setDescription("Description");
        film.setDuration(200);
        film.setReleaseDate(LocalDate.of(1956, 12, 28));
    }

    @Test
    void validate_BlankName_ShouldThrowValidationException() {
        film.setName("");

        final ValidationException exception = assertThrows(ValidationException.class, () -> filmController.validate(film));
        assertEquals("Название фильма не может быть пустым",
                exception.getMessage());
    }

    @Test
    void validate_NullName_ShouldThrowValidationException() {
        film.setName(null);

        final ValidationException exception = assertThrows(ValidationException.class, () -> filmController.validate(film));
        assertEquals("Название фильма не может быть пустым",
                exception.getMessage());
    }

    @Test
    void validate_DescriptionGT200_ShouldThrowValidationException() {
        film.setDescription("50char 0123456789 0123456789 0123456789 0123456789"+
                "50char 0123456789 0123456789 0123456789 0123456789"+
                "50char 0123456789 0123456789 0123456789 0123456789"+
                "50char 0123456789 0123456789 0123456789 0123456789"+
                "50char 0123456789 0123456789 0123456789 0123456789");

        final ValidationException exception = assertThrows(ValidationException.class, () -> filmController.validate(film));
        assertEquals("Максимальная длина описания — 200 символов",
                exception.getMessage());
    }

    @Test
    void validate_ReleaseDateInThePast_ShouldThrowValidationException() {
        film.setReleaseDate(LocalDate.MIN);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String messageException = "Дата релиза должна быть не раньше " + FilmController.MIN_RELEASE_DATE.format(formatter);

        final ValidationException exception = assertThrows(ValidationException.class, () -> filmController.validate(film));
        assertEquals(messageException, exception.getMessage());
    }

    @Test
    void validate_DurationLS0_ShouldThrowValidationException() {
        film.setDuration(-100);

        final ValidationException exception = assertThrows(ValidationException.class, () -> filmController.validate(film));
        assertEquals("Продолжительность фильма должна быть положительной",
                exception.getMessage());
    }

}