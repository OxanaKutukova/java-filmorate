package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;


@Repository
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;
    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre getGenreById(int genreId) {
        final String sqlQuery = "SELECT GENRE_ID, GENRE_NAME " +
                                "FROM GENRES " +
                                "WHERE GENRE_ID = ?";
        final List<Genre> genres = jdbcTemplate.query(sqlQuery, GenreDbStorage::makeGenre, genreId);
        if (genres.size() != 1) {
            return null;
        }
        return genres.get(0);
    }

    @Override
    public List<Genre> getAllGenres() {
        String sqlQuery =   "SELECT GENRE_ID, GENRE_NAME " +
                            "FROM GENRES";
        List<Genre> genres = jdbcTemplate.query(sqlQuery, GenreDbStorage::makeGenre);
        return genres;
    }

    @Override
    public void saveFilmGenres(Film film) {
        String sqlQuery =   "DELETE FROM FILM_GENRE " +
                            "WHERE FILM_ID = ?";
        jdbcTemplate.update(sqlQuery,film.getId());
        if (film.getGenres() == null || film.getGenres().isEmpty()) {
            return;
        }
        LinkedHashSet<Genre> genres = film.getGenres();
        String sqlQueryI =  "INSERT INTO FILM_GENRE (FILM_ID, GENRE_ID) " +
                            "VALUES (?, ?)";
        for(Genre genre:genres) {
            jdbcTemplate.update(sqlQueryI,film.getId(), genre.getId());
        }
    }

    @Override
    public LinkedHashSet<Genre> loadFilmGenres(int filmId) {
        final String sqlQuery = "SELECT g.GENRE_ID, g.GENRE_NAME "+
                                "FROM FILM_GENRE fg " +
                                "JOIN GENRES g ON (g.GENRE_ID = fg.GENRE_ID) " +
                                "WHERE fg.FILM_ID = ?";
        List<Genre> genres = jdbcTemplate.query(sqlQuery, GenreDbStorage::makeGenre, filmId);
        LinkedHashSet<Genre> setGenres = new LinkedHashSet<>(genres);
        return setGenres;
    }

    static Genre makeGenre(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(rs.getInt("GENRE_ID"),
                rs.getString("GENRE_NAME")
        );
    }

}