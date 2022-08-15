package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;


import java.sql.*;
import java.time.LocalDate;
import java.util.List;

@Repository("filmDbStorage")
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film getFilmById(int filmId) {
        final String sqlQuery = "SELECT f.FILM_ID, f.FILM_NAME, f.DESCRIPTION, " +
                                "f.RELEASE_DATE, f.DURATION, " +
                                "m.MPA_ID, m.MPA_NAME "+
                                "FROM FILMS f " +
                                "JOIN MPA m ON (m.MPA_ID = f.MPA_ID) " +
                                "WHERE f.FILM_ID = ?";
        final List<Film> films = jdbcTemplate.query(sqlQuery, FilmDbStorage::makeFilm, filmId);
        if (films.size() != 1) {
            return null;
        }
        return films.get(0);
    }

    @Override
    public List<Film> getAllFilms() {
        String sqlQuery =   "SELECT f.FILM_ID, f.FILM_NAME, f.DESCRIPTION," +
                            "f.RELEASE_DATE, f.DURATION, " +
                            "m.MPA_ID, m.MPA_NAME " +
                            "FROM FILMS f " +
                            "JOIN MPA m ON (m.MPA_ID = f.MPA_ID)";
        List<Film> films = jdbcTemplate.query(sqlQuery, FilmDbStorage::makeFilm);
        return films;
    }

    @Override
    public Film saveFilm(Film film) {
        String sqlQuery =   "INSERT INTO FILMS (FILM_NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID) " +
                            "VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"FILM_ID"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            final LocalDate releaseDate = film.getReleaseDate();
            if (releaseDate == null) {
                stmt.setNull(3, Types.DATE);
            } else {
                stmt.setDate(3, Date.valueOf(releaseDate));
            }
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);
        film.setId(keyHolder.getKey().intValue());
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        String sqlQuery =   "UPDATE FILMS " +
                            "SET FILM_NAME = ?, " +
                                "DESCRIPTION = ?, " +
                                "DURATION = ?, " +
                                "RELEASE_DATE = ?, " +
                                "MPA_ID = ? " +
                            "WHERE FILM_ID = ?";
        jdbcTemplate.update(sqlQuery
                , film.getName()
                , film.getDescription()
                , film.getDuration()
                , film.getReleaseDate()
                , film.getMpa().getId()
                , film.getId());
        return film;
    }

    @Override
    public void deleteFilm(Film film) {
        String sqlQuery =   "DELETE FROM FILMS " +
                            "WHERE FILM_ID = ?";
        jdbcTemplate.update(sqlQuery
                , film.getId());
    }

    static Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
        return new Film (rs.getInt("FILM_ID"),
                rs.getString("FILM_NAME"),
                rs.getString("DESCRIPTION"),
                rs.getDate("RELEASE_DATE").toLocalDate(),
                rs.getInt("DURATION"),
                new Mpa(rs.getInt("MPA_ID"), rs.getString("MPA_NAME"))
        );
    }
}
