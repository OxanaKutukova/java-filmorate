package ru.yandex.practicum.filmorate.storage.dao;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureTestDatabase
@AllArgsConstructor(onConstructor_ = @Autowired)
class UserDbStorageTest {

    private final UserDbStorage userDbStorage;
    @Test
    void UserDbStorageTest() {

        User testUser = new User();
        testUser.setName("name");
        testUser.setLogin("login");
        testUser.setEmail("nekto@email.com");
        testUser.setBirthday(LocalDate.of(2000, 12, 28));
        userDbStorage.saveUser(testUser);

        assertThat(userDbStorage.getAllUsers().size())
                .isEqualTo(1);

        testUser.setLogin("updated login");
        userDbStorage.updateUser(testUser);
        User updateUser = userDbStorage.getUserById(testUser.getId());
        assertThat(updateUser.getLogin()).isEqualTo(testUser.getLogin());

        userDbStorage.deleteUser(testUser);
    }

}