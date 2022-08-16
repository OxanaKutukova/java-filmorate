package ru.yandex.practicum.filmorate.storage.dao;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureTestDatabase
@AllArgsConstructor(onConstructor_ = @Autowired)
class LikeDbStorageTest {
    private final LikeDbStorage likeDbStorage;
    private final FilmDbStorage filmDbStorage;
    private final UserDbStorage userDbStorage;

    @Test
    void likeDbStorageTest() {
        User testUser = new User();
        testUser.setName("User");
        testUser.setLogin("login");
        testUser.setEmail("nekto@email.com");
        testUser.setBirthday(LocalDate.of(2000, 12, 28));
        userDbStorage.saveUser(testUser);

        User testFriend1 = new User();
        testFriend1.setName("Friend1User");
        testFriend1.setLogin("friend1Login");
        testFriend1.setEmail("friend1@email.com");
        testFriend1.setBirthday(LocalDate.of(2000, 12, 28));
        userDbStorage.saveUser(testFriend1);


        Film testFilm = new Film();
        testFilm.setName("Film");
        testFilm.setDescription("Description");
        testFilm.setDuration(200);
        testFilm.setReleaseDate(LocalDate.of(1956, 12, 28));
        testFilm.setMpa(new Mpa(1, null));
        filmDbStorage.saveFilm(testFilm);
        likeDbStorage.addLike(testFilm.getId(),testUser.getId());
        likeDbStorage.addLike(testFilm.getId(), testFriend1.getId());
        assertThat(likeDbStorage.getMostLikedFilms(10).size())
                .isEqualTo(1);

        userDbStorage.deleteUser(testUser);
        userDbStorage.deleteUser(testFriend1);
        filmDbStorage.deleteFilm(testFilm);

    }

}