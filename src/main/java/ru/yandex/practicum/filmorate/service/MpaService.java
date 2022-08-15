package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.Collection;

@Service
public class MpaService {
    @Autowired
    private MpaStorage mpaStorage;
    public Collection<Mpa> getAllMpa() {
        return mpaStorage.getAllMpa();
    }

    public Mpa getMpaById(int mpaId) {
        final Mpa resMpa = mpaStorage.getMpaById(mpaId);
        if (resMpa == null) {
            throw new NotFoundException("Рейтинг с id=" + mpaId + " не найден");
        }
        return resMpa;
    }
}
