package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;


@RestController
@RequestMapping("users")
@Slf4j

public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Collection<User> getUsers() {
        log.info("Запрос всех пользователей");
        return userService.getUsers();
    }
    @GetMapping("/{userId}")
    public User getUserById(@PathVariable int userId) {
        log.info("Получение пользователя id={}", userId);
        return userService.getUserById(userId);
    }

    @GetMapping("/{userId}/friends")
    public Collection<User> getFriends(@PathVariable int userId) {
        log.info("Запрос списка друзей у пользователя id={}", userId);
        return userService.getFriends(userId);
    }

    @GetMapping("/{userId}/friends/common/{otherUserId}")
    public Collection<User> getCommonFriends(@PathVariable int userId, @PathVariable int otherUserId) {
        log.info("Получение cписка общих друзей пользователя id={} с пользователем с id = {}", userId, otherUserId);
        return userService.getCommonFriends(userId, otherUserId);
    }

    @PostMapping
    public User saveUser(@Valid @RequestBody User user) {
        log.info("Добавление пользователя с логином {}", user.getLogin());
        validate(user);
        log.info("Произведена валидация пользователя");
        return userService.saveUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Изменение пользователя id = {}", user.getId());
        validate(user);
        log.info("Произведена валидация пользователя");
        return userService.updateUser(user);
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public void addFriend(@PathVariable int userId, @PathVariable int friendId) {
        log.info("Добавление в друзья к пользователю с id = {} пользователя с id = {}", userId, friendId);
        userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public void deleteFriend(@PathVariable int userId, @PathVariable int friendId) {
        log.info("Удаление из друзей у пользователя с id = {} друга с id = {}", userId, friendId);
        userService.deleteFriend(userId, friendId);
    }

    protected void validate(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getLogin() == null || user.getLogin().isBlank() ) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
    }
}
