package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;
    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Mpa getMpaById(int id) {
        final String sqlQuery = "SELECT MPA_ID, MPA_NAME " +
                                "FROM MPA " +
                                "WHERE MPA_ID = ?";
        final List<Mpa> mpas = jdbcTemplate.query(sqlQuery, MpaDbStorage::makeMpa, id);
        if (mpas.size() != 1) {
            return null;
        }
        return mpas.get(0);
    }

    @Override
    public List<Mpa> getAllMpa() {
        String sqlQuery =   "SELECT MPA_ID, MPA_NAME " +
                            "FROM MPA";
        List<Mpa> mpas = jdbcTemplate.query(sqlQuery, MpaDbStorage::makeMpa);
        return mpas;
    }

    static Mpa makeMpa(ResultSet rs, int rowNum) throws SQLException {
        return new Mpa(rs.getInt("MPA_ID"),
                rs.getString("MPA_NAME")
        );
    }

}
