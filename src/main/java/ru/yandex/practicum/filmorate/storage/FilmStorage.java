package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Collection<Film> getAllFilms();
    Film getFilmById(int filmId);
    Film saveFilm(Film film);
    Film updateFilm(Film film);
    void deleteFilm(Film film);
}
