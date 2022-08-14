package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;


import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("films")
@Slf4j

public class FilmController {

    protected static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    @Autowired
    private FilmService filmService;

    @GetMapping
    public Collection<Film> getFilms() {
        log.info("Запрос всех фильмов");
        return filmService.getFilms();
    }

    @GetMapping("/{filmId}")
    public Film getFilmById(@PathVariable int filmId) {
        log.info("Получение информации о фильме с id={}", filmId);
        return filmService.getFilmById(filmId);
    }
    @PostMapping
    public Film saveFilm(@Valid  @RequestBody Film film) {
        log.info("Добавление фильма с названием {}", film.getName());
        validate(film);
        log.info("Произведена валидация фильма");
        return filmService.saveFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Изменение фильма id = {}", film.getId());
        validate(film);
        log.info("Произведена валидация фильма");
        return filmService.updateFilm(film);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public void addLike(@PathVariable int filmId, @PathVariable int userId) {
        log.info("Пользователь  id = {} ставит лайк фильму id = {}", userId, filmId);
        filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public void deleteLike(@PathVariable int filmId, @PathVariable int userId) {
        log.info("Пользователь  id = {} удаляет лайк фильма id = {}", userId, filmId);
        filmService.deleteLike(filmId, userId);
    }
    @GetMapping("/popular")
    public Collection<Film> getMostLikedFilms(@RequestParam(defaultValue = "10", required = false) int count) {
        log.info("Запрос списrf из первых {} фильмов по количеству лайков", count);
        return filmService.getMostLikedFilms(count);

    }

    protected void validate(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            throw new ValidationException("Название фильма не может быть пустым");
        }
        if (film.getDescription().length() >200) {
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(MIN_RELEASE_DATE)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            throw new ValidationException("Дата релиза должна быть не раньше " + MIN_RELEASE_DATE.format(formatter));
        }
        if (film.getDuration() < 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }

    }

}
