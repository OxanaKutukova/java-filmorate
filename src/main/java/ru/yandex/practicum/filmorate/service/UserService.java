package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;

@Service
public class UserService {
    @Autowired
    @Qualifier("userDbStorage")
    private UserStorage userStorage;

    @Autowired
    private FriendStorage friendStorage;
    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User getUserById(int userId) {
        final User resUser = userStorage.getUserById(userId);
        if (resUser == null) {
            throw new NotFoundException("Пользователь с id=" + userId + " не найден");
        }
        return resUser;
    }

    public User saveUser(User user) {
        if (userStorage.getAllUsers().contains(user)) {
            throw new ValidationException("Пользователь с id = "+ user.getId()+ " уже есть в списке");
        }
        return userStorage.saveUser(user);
    }

    public User updateUser(User user) {
        final User tmpUser = userStorage.getUserById(user.getId());
        if (tmpUser == null) {
            throw new NotFoundException("Пользователь с id = " + user.getId() + " не найден");
        }
        return userStorage.updateUser(user);
    }


    public void addFriend(int userId, int friendId) {
        final User user = userStorage.getUserById(userId);
        final User friend = userStorage.getUserById(friendId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден");
        }
        if (friend == null) {
            throw new NotFoundException("Друг с id = " + friendId + " не найден");
        }
        friendStorage.addFriend(user, friend);
    }

    public void deleteFriend(int userId, int friendId) {
        final User user = userStorage.getUserById(userId);
        final User friend = userStorage.getUserById(friendId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден");
        }
        if (friend == null) {
            throw new NotFoundException("Друг с id = " + friendId + " не найден");
        }
        friendStorage.deleteFriend(user, friend);

    }

    public Collection<User> getFriends(int userId) {
        final User user = userStorage.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден");
        }
        return friendStorage.getFriends(userId);
    }
    public Collection<User> getCommonFriends(int userId, int otherUserId) {
        final User user = userStorage.getUserById(userId);
        final User otherUser = userStorage.getUserById(otherUserId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден");
        }
        if (otherUser == null) {
            throw new NotFoundException("Пользователь с id = " + otherUserId + " не найден");
        }
        return friendStorage.getCommonFriends(userId, otherUserId);
    }

}
