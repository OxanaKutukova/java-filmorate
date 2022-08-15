package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.Collection;


@Service
public class GenreService {
    @Autowired
    private GenreStorage genreStorage;

    public Collection<Genre> getAllGenres() {
        return genreStorage.getAllGenres();
    }

    public Genre getGenreById(int genreId) {
        final Genre resGenre = genreStorage.getGenreById(genreId);
        if (resGenre == null) {
            throw new NotFoundException("Жанр с id = " + genreId + " не найден");
        }
        return resGenre;
    }

}
