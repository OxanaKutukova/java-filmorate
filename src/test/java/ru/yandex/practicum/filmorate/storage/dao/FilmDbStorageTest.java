package ru.yandex.practicum.filmorate.storage.dao;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;


import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AllArgsConstructor(onConstructor_ = @Autowired)
class FilmDbStorageTest {

    private final FilmDbStorage filmDbStorage;
    @Test
    void FilmDbStorageTest() {
        Film testFilm = new Film();
        testFilm.setName("Film");
        testFilm.setDescription("Description");
        testFilm.setDuration(200);
        testFilm.setReleaseDate(LocalDate.of(1956, 12, 28));
        testFilm.setMpa(new Mpa(1, null));
        filmDbStorage.saveFilm(testFilm);

        List<Film> testFilms = filmDbStorage.getAllFilms();
        assertThat(testFilms.size())
                .isEqualTo(1);

        testFilm.setMpa(new Mpa(2, null));
        filmDbStorage.updateFilm(testFilm);
        Film updateFilm = filmDbStorage.getFilmById(testFilm.getId());
        assertThat(updateFilm.getMpa()).isEqualTo(testFilm.getMpa());
        filmDbStorage.deleteFilm(testFilm);

    }
}