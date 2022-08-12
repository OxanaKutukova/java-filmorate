package ru.yandex.practicum.filmorate.storage;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface FilmStorage {
    Collection<Film> getFilms();
    Film getFilmById(int filmId);
    Film saveFilm(Film film);
    Film updateFilm(Film film);

    void addLike(int filmId, int userId);

    void deleteLike(int filmId, int userId);
}
