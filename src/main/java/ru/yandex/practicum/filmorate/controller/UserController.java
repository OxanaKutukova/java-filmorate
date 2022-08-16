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

    //Получить список всех пользователей
    @GetMapping
    public Collection<User> getAllUsers() {
        log.info("getAllUsers (GET /users/): Получить список всех пользователей");
        Collection<User> allUsers = userService.getAllUsers();
        log.info("getAllUsers (GET /users/): Результат = {}", allUsers);
        return allUsers;
    }

    //Получить пользователя по Id
    @GetMapping("/{userId}")
    public User getUserById(@PathVariable int userId) {
        log.info("getUserById (GET /users/{}): Получить пользователя по id(входной параметр) = {}", userId, userId);
        User resUser = userService.getUserById(userId);
        log.info("getUserById (GET /users/{}): Результат = {}",userId, resUser);
        return resUser;
    }

    //Добавить пользователя
    @PostMapping
    public User saveUser(@Valid @RequestBody User user) {
        log.info("saveUser (POST /users/): Добавить пользователя (входной параметр) = {}", user);
        validate(user);
        log.info("saveUser (POST /users/): Произведена валидация пользователя");
        User resUser = userService.saveUser(user);
        log.info("saveUser (POST /users/): Пользователь добавлен: {}", resUser);
        return resUser;
    }

    //Изменить пользователя
    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("updateUser (PUT /users/): Изменить пользователя (входной параметр) = {}", user);
        validate(user);
        log.info("updateUser (PUT /users/): Произведена валидация пользователя");
        User resUser = userService.updateUser(user);
        log.info("updateUser (PUT /users/): Пользователь изменен: {}", resUser);
        return resUser;
    }

    //Добавить в друзья
    @PutMapping("/{userId}/friends/{friendId}")
    public void addFriend(@PathVariable int userId, @PathVariable int friendId) {
        log.info("addFriend (PUT /users/{}/friends/{}): Добавить в друзья к пользователю с " +
                        "id (входной параметр) = {} пользователя с id (входной параметр) = {}",
                        userId, friendId, userId, friendId);
        userService.addFriend(userId, friendId);
    }

    //Удалить из друзей
    @DeleteMapping("/{userId}/friends/{friendId}")
    public void deleteFriend(@PathVariable int userId, @PathVariable int friendId) {
        log.info("deleteFriend (DELETE /users/{}/friends/{}): Удалить из друзей у пользователя с " +
                        "id (входной параметр) = {} друга с id (входной параметр) = {}",
                        userId, friendId, userId, friendId);
        userService.deleteFriend(userId, friendId);
    }

    //Получить список друзей
    @GetMapping("/{userId}/friends")
    public Collection<User> getFriends(@PathVariable int userId) {
        log.info("getFriends (GET /users/{}/friends): Получить список друзей пользователя " +
                "id (входной параметр) ={}", userId);
        Collection<User> friends = userService.getFriends(userId);
        log.info("getFriends (GET /users/{}/friends): Результат : {}", userId, friends);
        return friends;
    }

    //Получить список друзей, общих с другим пользователем
    @GetMapping("/{userId}/friends/common/{otherUserId}")
    public Collection<User> getCommonFriends(@PathVariable int userId, @PathVariable int otherUserId) {
        log.info("getCommonFriends (GET /users/{}/friends/common/{}): Получить cписок общих друзей пользователя " +
                "id (входной параметр) = {} с пользователем с id (входной параметр) = {}",
                userId, otherUserId, userId, otherUserId);
        Collection<User> commonFriends = userService.getCommonFriends(userId, otherUserId);
        log.info("getCommonFriends (GET /users/{}/friends/common/{}): Результат : {}", userId, otherUserId, commonFriends);

        return commonFriends;
    }

    //Валидация пользователя
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
