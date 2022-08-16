package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.LikeStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.LinkedHashSet;

@Service
@Qualifier
public class FilmService {
    @Autowired
    @Qualifier("filmDbStorage")
    private FilmStorage filmStorage;
    @Autowired
    @Qualifier("userDbStorage")
    private UserStorage userStorage;

    @Autowired
    private LikeStorage likeStorage;

    public Collection<Film> getAllFilms() {
        Collection<Film> allFilms = filmStorage.getAllFilms();
        return allFilms;
    }

    public Film getFilmById(int filmId) {
        final Film resFilm = getFilmWithCheckNull(filmId);
        return resFilm;
    }

    public Film saveFilm(Film film) {
        if (filmStorage.getAllFilms().contains(film)) {
            throw new ValidationException("Фильм с id = "+ film.getId()+ " уже есть в списке");
        }
        final Film sFilm = filmStorage.saveFilm(film);
        return sFilm;
    }

    public Film updateFilm(Film film) {
        Film uFilm = getFilmWithCheckNull(film.getId());
        uFilm = filmStorage.updateFilm(film);
        return uFilm;
    }

    public void addLike(int filmId, int userId) {

        final Film film = getFilmWithCheckNull(filmId);
        final User user = userStorage.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден");
        }
        likeStorage.addLike(filmId, userId);
    }

    public void deleteLike(int filmId, int userId) {

        final User user = userStorage.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден");
        }
        final Film film = getFilmWithCheckNull(filmId);
        likeStorage.deleteLike(filmId, userId);
    }

    public Collection<Film> getMostLikedFilms(int count) {
        Collection<Film> films = likeStorage.getMostLikedFilms(count);
        return films;
    }

    private Film getFilmWithCheckNull (int filmId) {
        Film resFilm = filmStorage.getFilmById(filmId);
        if (resFilm == null) {
            throw new NotFoundException("Фильм с id = " + filmId + " не найден");
        }
        return resFilm;
    }
}

