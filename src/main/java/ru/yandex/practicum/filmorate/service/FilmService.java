package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class FilmService {
    @Autowired
    FilmStorage filmStorage;
    @Autowired
    UserStorage userStorage;
    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film getFilmById(int filmId) {
        final Film film = filmStorage.getFilmById(filmId);
        if (film == null) {
            throw new NotFoundException("Фильм с id=" + filmId + " не найден");
        }
        return film;
    }

    public Film saveFilm(Film film) {
        if (filmStorage.getFilms().contains(film)) {
            throw new ValidationException("Фильм с Id "+ film.getId()+ " уже есть в списке");
        }
        return filmStorage.saveFilm(film);
    }

    public Film updateFilm(Film film) {
        final Film tmpFilm = filmStorage.getFilmById(film.getId());
        if (tmpFilm == null) {
            throw new NotFoundException("Фильм с id=" + film.getId() + " не найден");
        }
        return filmStorage.updateFilm(film);
    }

    public void addLike(int filmId, int userId) {
        final User user = userStorage.getUserById(userId);
        final Film film = filmStorage.getFilmById(filmId);
        if (film == null) {
            throw new NotFoundException("Фильм с id=" + filmId + " не найден");
        }
        if (user == null) {
            throw new NotFoundException("Пользователь с id=" + userId + " не найден");
        }
        filmStorage.addLike(filmId, userId);
    }

    public void deleteLike(int filmId, int userId) {
        final User user = userStorage.getUserById(userId);
        final Film film = filmStorage.getFilmById(filmId);
        if (film == null) {
            throw new NotFoundException("Фильм с id = " + filmId + " не найден");
        }
        if (user == null) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден");
        }
        if (!film.getUserIds().contains(user.getId())) {
            throw new NotFoundException("У фильма с id = " + filmId + " лайк от пользователя с id =" + userId + " не найден");
        }
        filmStorage.deleteLike(filmId, userId);
    }

    public Collection<Film> getMostLikedFilms(int count) {
        return   filmStorage.getFilms().stream()
                .sorted((film1, film2) -> film2.getUserIds().size() - film1.getUserIds().size())
                .limit(count).collect(Collectors.toList());
    }
}
