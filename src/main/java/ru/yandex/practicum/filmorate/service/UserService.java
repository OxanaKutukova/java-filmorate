package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserStorage userStorage;

    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    public User getUserById(int userId) {
        final User user = userStorage.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id=" + userId + " не найден");
        }
        return user;
    }

    public User saveUser(User user) {
        if (userStorage.getUsers().contains(user)) {
            throw new ValidationException("Пользователь с Id "+ user.getId()+ " уже есть в списке");
        }
        return userStorage.saveUser(user);
    }

    public User updateUser(User user) {
        final User tmpUser = userStorage.getUserById(user.getId());
        if (tmpUser == null) {
            throw new NotFoundException("Пользователь с id=" + user.getId() + " не найден");
        }
        return userStorage.updateUser(user);
    }


    public void addFriend(int userId, int friendId) {
        final User user = userStorage.getUserById(userId);
        final User friend = userStorage.getUserById(friendId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id=" + userId + " не найден");
        }
        if (friend == null) {
            throw new NotFoundException("Друг с id=" + friendId + " не найден");
        }
        userStorage.addFriend(user, friend);
    }

    public void deleteFriend(int userId, int friendId) {
        final User user = userStorage.getUserById(userId);
        final User friend = userStorage.getUserById(friendId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id=" + userId + " не найден");
        }
        if (friend == null) {
            throw new NotFoundException("Друг с id=" + friendId + " не найден");
        }
        userStorage.deleteFriend(user, friend);

    }

    public Collection<User> getFriends(int userId) {
        final User user = userStorage.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id=" + userId + " не найден");
        }
        return user.getFriendIds().stream().map(this::getUserById).collect(Collectors.toList());
    }
    public Collection<User> getCommonFriends(int userId, int otherUserId) {
        final User user = userStorage.getUserById(userId);
        final User otherUser = userStorage.getUserById(otherUserId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id=" + userId + " не найден");
        }
        if (otherUser == null) {
            throw new NotFoundException("Пользователь с id=" + otherUserId + " не найден");
        }
        Set<Integer> resultUserIds = new HashSet<>(user.getFriendIds());
        resultUserIds.retainAll(otherUser.getFriendIds());
        return resultUserIds.stream().map(this::getUserById).collect(Collectors.toList());
    }

}
