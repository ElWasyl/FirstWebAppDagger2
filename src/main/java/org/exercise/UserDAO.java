package org.exercise;

import java.sql.*;
import java.time.LocalDateTime;

public class UserDAO {

    private static final String URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";
    private static final String INSERT_USER_SQL = "INSERT INTO users (email, password) VALUES (?,?)";
    private static final String SELECT_USERS_BY_EMAIL_SQL = "SELECT email, password FROM users WHERE email = ?";
    private static final String REGISTER_USERS_TOKEN_SQL = "INSERT INTO tokens (email, token, expiration) VALUES (?,?,?)";
    private static final String CHECK_IF_TOKEN_VALID_SQL = "SELECT expiration FROM tokens WHERE token = ?";

    public static void addUser(User user) throws UserDAOException {

        try (Connection connection = createConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(INSERT_USER_SQL)) {
                statement.setString(1, user.getEmail());
                statement.setString(2, user.getPassword());
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new UserDAOException("There's been a conflict with the current state of the target resource");
            }
        } catch (SQLException e) {
            throw new UserDAOException("The server is currently unable to handle the request");
        }
    }

    public static User getUser(String email) throws UserDAOException {
        try (Connection connection = createConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_USERS_BY_EMAIL_SQL)) {
                statement.setString(1, email);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return createUserFromResultSet(resultSet);
                    } else {
                        return null;
                    }
                }
            } catch (SQLException e) {
                throw new UserDAOException("There's been a conflict with the current state of the target resource");
            }
        } catch (SQLException e) {
            throw new UserDAOException("The server is currently unable to handle the request");
        }
    }

    public static void registerToken(User user, String token) throws UserDAOException {
        try (Connection connection = createConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(REGISTER_USERS_TOKEN_SQL)) {
                statement.setString(1, user.getEmail());
                statement.setString(2, token);
                statement.setTimestamp(3, Timestamp.valueOf((LocalDateTime.now().plusDays(1))));
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new UserDAOException("The server is currently unable to handle the request");
        }
    }

    public static boolean checkToken(String token) throws UserDAOException {
        try (Connection connection = createConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(CHECK_IF_TOKEN_VALID_SQL)) {
                statement.setString(1, token);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Timestamp timestamp = resultSet.getTimestamp("expiration");
                        return timestamp.after(new Timestamp(System.currentTimeMillis()));
                    } else {
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            throw new UserDAOException("The server is currently unable to handle the request");
        }
    }

    private static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    private static User createUserFromResultSet(ResultSet resultSet) throws SQLException {
        return new User.UserBuilder(resultSet.getString("email"), resultSet.getString("password")).build();
    }

}
