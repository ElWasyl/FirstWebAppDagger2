package org.exercise;

import java.util.HashMap;
import java.util.Map;

public class UserDAO {
    private static final Map<String, User> USERS_BY_EMAIL = new HashMap();

    public static void addUser(User user) throws UserDAOException {
        if (USERS_BY_EMAIL.containsKey(user.getEmail())) {
            throw new UserDAOException();
        } else {
            USERS_BY_EMAIL.put(user.getEmail(), user);
        }
    }

    public static User getUser(String email) {
        return USERS_BY_EMAIL.get(email);
    }

}
