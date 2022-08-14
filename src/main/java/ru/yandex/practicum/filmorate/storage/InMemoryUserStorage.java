package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;


@Component
public class InMemoryUserStorage  implements UserStorage {

    private HashMap<Integer, User> users = new HashMap<>();
    private Integer generateUserId = 0;

    @Override
    public Collection<User> getUsers() {
        return users.values();
    }
    @Override
    public User getUserById(int userId) {
        return users.get(userId);
    }
    @Override
    public User saveUser(User user) {
        user.setId(++generateUserId);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void addFriend(User user, User friend) {
        users.get(user.getId()).getFriendIds().add(friend.getId());
        users.get(friend.getId()).getFriendIds().add(user.getId());
    }

    @Override
    public void deleteFriend(User user, User friend) {
        users.get(user.getId()).getFriendIds().remove(friend.getId());
        users.get(friend.getId()).getFriendIds().remove(user.getId());
    }
}
