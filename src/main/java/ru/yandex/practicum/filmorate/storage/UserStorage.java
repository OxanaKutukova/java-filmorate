package ru.yandex.practicum.filmorate.storage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    Collection<User> getAllUsers();
    User saveUser(User user);
    User updateUser(User user);
    User getUserById(int userId);
    void deleteUser(User user);

}
