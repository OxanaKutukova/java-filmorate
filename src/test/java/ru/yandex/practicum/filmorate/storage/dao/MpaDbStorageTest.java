package ru.yandex.practicum.filmorate.storage.dao;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@AllArgsConstructor(onConstructor_ = @Autowired)
class MpaDbStorageTest {

    private final MpaDbStorage mpaStorage;

    @Test
    void getMpaById() {
        Mpa genre = mpaStorage.getMpaById(3);
        Mpa testMpa = new Mpa(3, "PG-13");
        assertThat(genre)
                .isEqualTo(testMpa);
    }

    @Test
    void getAllMpa() {
        List<Mpa> mpas = mpaStorage.getAllMpa();
        assertThat(mpas.size())
                .isEqualTo(5);
    }
}