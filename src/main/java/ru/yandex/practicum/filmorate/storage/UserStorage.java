package ru.yandex.practicum.filmorate.storage;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    Collection<User> getUsers();
    User saveUser(User user);
    User updateUser(User user);

    User getUserById(int userId);

    void addFriend(User user, User friend);

    void deleteFriend(User user, User friend);
}
