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

@RestController
@RequestMapping("films")
@Slf4j

public class FilmController {

    protected static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    @Autowired
    private FilmService filmService;

    //Получить список всех фильмов
    @GetMapping
    public Collection<Film> getAllFilms() {
        log.info("getAllFilms (GET /films/): Получить список всех фильмов");
        Collection<Film> allFilms = filmService.getAllFilms();
        log.info("getAllFilms (GET /films/): Результат = {}", allFilms);
        return allFilms;
    }

    //Получить фильм по Id
    @GetMapping("/{filmId}")
    public Film getFilmById(@PathVariable int filmId) {
        log.info("getFilmById (GET /films/{}): Получить фильм по id (входной параметр) = {}", filmId, filmId);
        Film resFilm = filmService.getFilmById(filmId);
        log.info("getFilmById (GET /films/{}): Результат = {}", filmId, resFilm);
        return resFilm;
    }

    //Добавить фильм
    @PostMapping
    public Film saveFilm(@Valid  @RequestBody Film film) {
        log.info("saveFilm (POST /films/): Добавить фильм (входной параметр) = {}", film);
        validate(film);
        log.info("saveFilm (POST /films/): Произведена валидация фильма");
        Film resFilm = filmService.saveFilm(film);
        log.info("saveFilm (POST /films): Добавлен фильм: {}", resFilm);
        return resFilm;
    }

    //Изменить фильм
    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("updateFilm (PUT /films/): Изменить фильм (входной параметр) = {}", film);
        validate(film);
        log.info("updateFilm (PUT /films): Произведена валидация фильма");
        Film resFilm = filmService.updateFilm(film);
        log.info("updateFilm (PUT /films/): Изменен фильм: {}", resFilm);
        return resFilm;
    }

    //Пользователь ставит лайк фильму
    @PutMapping("/{filmId}/like/{userId}")
    public void addLike(@PathVariable int filmId, @PathVariable int userId) {
        log.info("addLike (PUT /films/{}/like/{}): Пользователь id = {} ставит лайк фильму id = {}",
                userId, filmId, userId, filmId);
        filmService.addLike(filmId, userId);
    }

    //Пользователь удаляет лайк у фильма
    @DeleteMapping("/{filmId}/like/{userId}")
    public void deleteLike(@PathVariable int filmId, @PathVariable int userId) {
        log.info("deleteLike (DELETE /films/{}/like/{}): Пользователь id = {} удаляет лайк фильма id = {}",
                userId, filmId, userId, filmId);
        filmService.deleteLike(filmId, userId);
    }

    //Получить список из первых count фильмов по количеству лайков
    @GetMapping("/popular")
    public Collection<Film> getMostLikedFilms(@RequestParam(defaultValue = "10", required = false) int count) {
        log.info("getMostLikedFilms (GET /films/popular/): Получить список из первых {} фильмов по количеству лайков", count);
        Collection<Film> films = filmService.getMostLikedFilms(count);
        log.info("getMostLikedFilms (GET /films/popular/): Результат {}", films);
        return films;
    }

    //Валидация фильма
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
