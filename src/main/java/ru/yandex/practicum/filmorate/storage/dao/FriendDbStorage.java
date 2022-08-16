package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendStorage;

import java.util.Collection;
import java.util.List;

@Repository
public class FriendDbStorage implements FriendStorage {

    private final JdbcTemplate jdbcTemplate;
    public FriendDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void addFriend(User user, User friend) {
        String sqlQuery =   "MERGE INTO FRIENDS (USER_ID, FRIEND_ID) " +
                            "VALUES (?, ?)";
        jdbcTemplate.update(sqlQuery, user.getId(), friend.getId());
    }

    @Override
    public void deleteFriend(User user, User friend) {
        String sqlQuery =   "DELETE FROM FRIENDS  " +
                            "WHERE USER_ID = ? AND FRIEND_ID = ?";
        jdbcTemplate.update(sqlQuery, user.getId(), friend.getId());
    }

    @Override
    public Collection<User> getFriends(int userId) {
        String sqlQuery =   "SELECT u.USER_ID, u.USER_NAME, u.LOGIN, u.EMAIL, u.BIRTHDAY " +
                            "FROM FRIENDS f " +
                            "JOIN USERS u ON (u.USER_ID = f.FRIEND_ID) " +
                            "WHERE f.USER_ID = ?" ;
        List<User> friends = jdbcTemplate.query(sqlQuery, UserDbStorage::makeUser, userId);
        return friends;
    }

    @Override
    public Collection<User> getCommonFriends(int userId, int otherUserId) {
        String sqlQuery =   "SELECT u.USER_ID, u.USER_NAME, u.LOGIN, u.EMAIL, u.BIRTHDAY " +
                            "FROM FRIENDS f1 " +
                            "JOIN FRIENDS f2 ON (f1.FRIEND_ID = f2.FRIEND_ID AND f1.user_id<> f2.user_id ) " +
                            "JOIN USERS u ON (u.USER_ID = f1.FRIEND_ID) " +
                            "WHERE f1.USER_ID = ? AND f2.USER_ID = ?";
        List<User> commonFriends = jdbcTemplate.query(sqlQuery, UserDbStorage::makeUser, userId, otherUserId);
        return commonFriends;
    }
}
