package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

@RestController
@RequestMapping("genres")
@Slf4j

public class GenreController {
    @Autowired
    private GenreService genreService;

    //Получить список всех жанров
    @GetMapping
    public Collection<Genre> getAllGenres() {
        log.info("getAllGenres(GET /genres/): Получить список всех жанров");
        Collection<Genre> allGenres = genreService.getAllGenres();
        log.info("getAllGenres(GET /genres/): Результат = {}", allGenres);
        return allGenres;
    }

    //Получить жанр по Id
    @GetMapping("/{genreId}")
    public Genre getGenreById(@PathVariable int genreId) {
        log.info("getGenreById(GET /genres/{}): Получить жанр по id (входной параметр) = {}", genreId);
        Genre resGenre = genreService.getGenreById(genreId);
        log.info("getGenreById(GET /genres/{}): Результат = {}", genreId, resGenre);
        return resGenre;
    }
}
