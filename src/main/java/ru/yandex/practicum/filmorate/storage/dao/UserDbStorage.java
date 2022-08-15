package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

@Repository("userDbStorage")
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getUserById(int userId) {
        final String sqlQuery = "SELECT USER_ID, USER_NAME, LOGIN, EMAIL, BIRTHDAY "+
                                "FROM USERS " +
                                "WHERE USER_ID = ?";
        final List<User> users = jdbcTemplate.query(sqlQuery, UserDbStorage::makeUser, userId);
        if (users.size() != 1) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public List<User> getAllUsers() {
        String sqlQuery =   "SELECT USER_ID, USER_NAME, LOGIN, EMAIL, BIRTHDAY " +
                            "FROM USERS " ;
        List<User> users = jdbcTemplate.query(sqlQuery, UserDbStorage::makeUser);
        return users;
    }

    @Override
    public User saveUser(User user) {
        String sqlQuery =   "INSERT INTO USERS (USER_NAME, LOGIN, EMAIL, BIRTHDAY) " +
                            "VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"USER_ID"});
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getEmail());
            final LocalDate birthDay = user.getBirthday();
            if (birthDay == null) {
                stmt.setNull(4, Types.DATE);
            } else {
                stmt.setDate(4, Date.valueOf(birthDay));
            }
            return stmt;
        }, keyHolder);
        user.setId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public User updateUser(User user) {
        String sqlQuery =   "UPDATE USERS SET " +
                            "USER_NAME = ?, LOGIN = ?, EMAIL = ?, BIRTHDAY = ? " +
                            "WHERE USER_ID = ?";
        jdbcTemplate.update(sqlQuery
                , user.getName()
                , user.getLogin()
                , user.getEmail()
                , user.getBirthday()
                , user.getId());
        return user;
    }

    @Override
    public void deleteUser(User user) {
        String sqlQuery =   "DELETE FROM USERS " +
                            "WHERE USER_ID = ?";
        jdbcTemplate.update(sqlQuery
                , user.getId());
    }
    static User makeUser(ResultSet rs, int rowNum) throws SQLException {
        return new User (rs.getInt("USER_ID"),
                rs.getString("USER_NAME"),
                rs.getString("LOGIN"),
                rs.getString("EMAIL"),
                rs.getDate("BIRTHDAY").toLocalDate()
        );
    }

}
