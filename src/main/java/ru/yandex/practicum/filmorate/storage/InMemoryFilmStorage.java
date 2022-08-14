package ru.yandex.practicum.filmorate.storage;


import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;


import java.util.Collection;
import java.util.HashMap;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final HashMap<Integer, Film> films = new HashMap<>();
    private Integer generateFilmId = 0;

    @Override
    public Collection<Film> getFilms() { return films.values();}

    @Override
    public Film getFilmById(int filmId) { return films.get(filmId);}
    @Override
    public Film saveFilm(Film film) {
        film.setId(++generateFilmId);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public void addLike(int filmId, int userId) {
        films.get(filmId).getUserIds().add(userId);
    }

    @Override
    public void deleteLike(int filmId, int userId) {
        films.get(filmId).getUserIds().remove(userId);
    }

}
