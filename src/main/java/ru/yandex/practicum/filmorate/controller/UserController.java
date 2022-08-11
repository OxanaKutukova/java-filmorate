package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import ru.yandex.practicum.filmorate.exception.ControllerException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("users")
@Slf4j

public class UserController {
    protected HashMap<Integer, User> users = new HashMap<>();
    private Integer userId = 0;

    @GetMapping
    public Collection<User> getUsers() {
        return users.values();
    }

    @PostMapping
    public User saveUser(@RequestBody User user) {
        validate(user);
        user.setId(++userId);
        if (!users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.debug("Фильм {} успешно дообавлен", user.getName());
        } else {
            log.debug("Фильм с Id {} уже есть в списке", user.getId());
        }
        return user;
    }

    @PutMapping
    public User updateFilm(@RequestBody User user) {
        validate(user);
        if (!users.containsKey(user.getId())) {
            log.debug("Пользователь с Id {} в списке не найден", user.getId());
                       throw new ControllerException("Ошибка обновления пользователя: пользователь не был найден");
        }
        users.put(user.getId(), user);
        log.debug("Пользователь с именем {} успешно обновлен", user.getName());
        return user;
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
