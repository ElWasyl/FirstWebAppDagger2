package org.exercise;

import java.util.HashMap;
import java.util.Map;

public class UserDAO {
    private static final Map<String, User> usersByEmail = new HashMap();

    public static void addUser(User user) throws UserDAOException {
        if (usersByEmail.containsKey(user.getEmail())) {
            throw new UserDAOException();
        } else {
            usersByEmail.put(user.getEmail(), user);
        }
    }

    public static User getUser(String email) {
        return usersByEmail.get(email);
    }

}
