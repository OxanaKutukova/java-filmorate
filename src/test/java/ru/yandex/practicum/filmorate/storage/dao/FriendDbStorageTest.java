package ru.yandex.practicum.filmorate.storage.dao;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureTestDatabase
@AllArgsConstructor(onConstructor_ = @Autowired)
class FriendDbStorageTest {
    private final FriendDbStorage friendDbStorage;
    private final UserDbStorage userDbStorage;

    @Test
    void FriendDbStorageTest() {
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

        User testFriend2 = new User();
        testFriend2.setName("Friend2User");
        testFriend2.setLogin("friend2Login");
        testFriend2.setEmail("friend2@email.com");
        testFriend2.setBirthday(LocalDate.of(2000, 12, 28));
        userDbStorage.saveUser(testFriend2);

        friendDbStorage.addFriend(testUser, testFriend1);
        friendDbStorage.addFriend(testUser, testFriend2);
        assertThat(friendDbStorage.getFriends(testUser.getId()).size())
                .isEqualTo(2);

        friendDbStorage.deleteFriend(testUser,testFriend1);
        assertThat(friendDbStorage.getFriends(testUser.getId()).size())
                .isEqualTo(1);

        friendDbStorage.addFriend(testFriend1,testFriend2);
        assertThat(friendDbStorage.getCommonFriends(testUser.getId(), testFriend1.getId()).contains(testFriend2))
                .isTrue();

        userDbStorage.deleteUser(testUser);
        userDbStorage.deleteUser(testFriend1);
        userDbStorage.deleteUser(testFriend2);
    }
}