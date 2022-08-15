package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.LikeStorage;

import java.util.Collection;
import java.util.List;

@Repository
public class LikeDbStorage implements LikeStorage {
    private final JdbcTemplate jdbcTemplate;
    public LikeDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addLike(int filmId, int userId) {
        String sqlQuery =   "MERGE INTO LIKES (USER_ID, FILM_ID) " +
                            "VALUES (?, ?)";
        jdbcTemplate.update(sqlQuery, userId, filmId);
        String sqlQueryU =  "UPDATE FILMS " +
                            "SET RATE = RATE + 1 " +
                            "WHERE FILM_ID = ?";
        jdbcTemplate.update(sqlQueryU, filmId);
    }

    @Override
    public void deleteLike(int filmId, int userId) {
        String sqlQuery =   "DELETE FROM LIKES  " +
                            "WHERE USER_ID = ? AND FILM_ID = ?";
        jdbcTemplate.update(sqlQuery, userId, filmId);
        String sqlQueryU =  "UPDATE FILMS " +
                            "SET RATE = RATE - 1 " +
                            "WHERE FILM_ID = ?";
        jdbcTemplate.update(sqlQueryU, filmId);
    }

    @Override
    public Collection<Film> getMostLikedFilms(int count) {
        String sqlQuery =   "SELECT f.FILM_ID, f.FILM_NAME, f.DESCRIPTION," +
                            "f.RELEASE_DATE, f.DURATION, " +
                            "m.MPA_ID, m.MPA_NAME " +
                            "FROM FILMS f " +
                            "JOIN MPA m ON (m.MPA_ID = f.MPA_ID) " +
                            "ORDER BY f.RATE DESC " +
                            "LIMIT ?";
        List<Film> likedFilms = jdbcTemplate.query(sqlQuery, FilmDbStorage::makeFilm, count);
        return likedFilms;
    }

}
