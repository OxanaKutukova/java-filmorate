package ru.yandex.practicum.filmorate.storage.dao;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureTestDatabase
@AllArgsConstructor(onConstructor_ = @Autowired)

class GenreDbStorageTest {

    private final GenreDbStorage genreStorage;
    @Test
    void getGenreById() {
        Genre genre = genreStorage.getGenreById(6);
        Genre testGenre = new Genre(6, "Боевик");
        assertThat(genre)
                .isEqualTo(testGenre);
    }

    @Test
    void getAllGenres() {
        List<Genre> genres = genreStorage.getAllGenres();
        assertThat(genres.size())
                .isEqualTo(6);
    }
}