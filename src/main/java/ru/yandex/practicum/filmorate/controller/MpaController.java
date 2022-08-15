package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;


import java.util.Collection;

@RestController
@RequestMapping("mpa")
@Slf4j

public class MpaController {
    @Autowired
    private MpaService mpaService;

    //Получить список всех рейтингов
    @GetMapping
    public Collection<Mpa> getAllMpa() {
        log.info("getAllMpa(GET /mpa/): Получить список всех рейтингов");
        Collection<Mpa> allMpas = mpaService.getAllMpa();
        log.info("getAllMpa(GET /mpa/): Результат = {}", allMpas);
        return allMpas;
    }

    //Получить рейтинг по Id
    @GetMapping("/{mpaId}")
    public Mpa getMpaById(@PathVariable int mpaId) {
        log.info("getMpaById(GET /mpa/{}): Получить рейтинг по id (входной параметр) = {}", mpaId);
        Mpa resMpa = mpaService.getMpaById(mpaId);
        log.info("getGenreById(GET /mpa/{}): Результат = {}", mpaId, resMpa);
        return resMpa;
    }
}
