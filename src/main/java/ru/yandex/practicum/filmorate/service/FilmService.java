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
    private GenreStorage genreStorage;
    @Autowired
    private LikeStorage likeStorage;

    public Collection<Film> getAllFilms() {
        Collection<Film> allFilms = filmStorage.getAllFilms();
        for(Film film : allFilms) {
            LinkedHashSet<Genre> genres = genreStorage.loadFilmGenres(film.getId());
            if (genres != null && !genres.isEmpty()) {
                film.setGenres(genres);
            }
        }
        return allFilms;
    }

    public Film getFilmById(int filmId) {
        Film resFilm = filmStorage.getFilmById(filmId);
        if (resFilm == null) {
            throw new NotFoundException("Фильм с id = " + filmId + " не найден");
        }
        LinkedHashSet<Genre> genres = genreStorage.loadFilmGenres(filmId);
        if (genres != null && !genres.isEmpty()) {
            resFilm.setGenres(genres);
        }
        return resFilm;
    }

    public Film saveFilm(Film film) {
        if (filmStorage.getAllFilms().contains(film)) {
            throw new ValidationException("Фильм с id = "+ film.getId()+ " уже есть в списке");
        }
        Film sFilm = filmStorage.saveFilm(film);
        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            genreStorage.saveFilmGenres(sFilm);
        }
        return sFilm;
    }

    public Film updateFilm(Film film) {
        final Film uFilm = filmStorage.getFilmById(film.getId());
        if (uFilm == null) {
            throw new NotFoundException("Фильм с id = " + film.getId() + " не найден");
        }
        genreStorage.saveFilmGenres(film);
        return filmStorage.updateFilm(film);
    }

    public void addLike(int filmId, int userId) {
        final User user = userStorage.getUserById(userId);
        final Film film = filmStorage.getFilmById(filmId);
        if (film == null) {
            throw new NotFoundException("Фильм с id = " + filmId + " не найден");
        }
        if (user == null) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден");
        }
        likeStorage.addLike(filmId, userId);
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
        likeStorage.deleteLike(filmId, userId);

    }

    public Collection<Film> getMostLikedFilms(int count) {
        Collection<Film> films = likeStorage.getMostLikedFilms(count);
        for(Film film : films) {
            LinkedHashSet<Genre> genres = genreStorage.loadFilmGenres(film.getId());
            if (genres != null && !genres.isEmpty()) {
                film.setGenres(genres);
            }
        }
        return films;
    }
}

