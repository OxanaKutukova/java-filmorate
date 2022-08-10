package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ControllerException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;



import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("films")
@Slf4j

public class FilmController {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private Integer filmId = 0;
    protected static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    @GetMapping
    public Collection<Film> getFilms() {
        return films.values();
    }

    @PostMapping
    public Film saveFilm(@RequestBody Film film) {
        validate(film);
        film.setId(++filmId);
        if (!films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.debug("Фильм {} успешно дообавлен", film.getName());
        } else {
            log.debug("Фильм с Id {} уже есть в списке", film.getId());
        }
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        validate(film);
        if (!films.containsKey(film.getId())) {
            log.debug("Фильм с Id {} в списке не найден", film.getId());
            throw new ControllerException("Ошибка обновления фильма: фильм не был найден");        }
        films.put(film.getId(), film);
        log.debug("Фильм с названием {} успешно обновлен", film.getName());
        return film;
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
