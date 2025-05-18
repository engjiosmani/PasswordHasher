package utils;

import java.sql.*;

public class DBUserFunctions {
    public Connection connection;
    public DBUserFunctions(){
        this.connection = DBConnector.getConnection();
    }
    public boolean getByEmail(String email) {
        String query = "SELECT 1 FROM users WHERE TRIM(LOWER(email)) = LOWER(TRIM(?))";

        try (PreparedStatement pstm = connection.prepareStatement(query)) {
            pstm.setString(1, email);
            try (ResultSet res = pstm.executeQuery()) {
                return res.next();
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return false;
    }
    public boolean create(Users user) {
        String query = "INSERT INTO users (username, password_hash, salt, firstName, lastName, email) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPasswordHash());
            statement.setString(3, user.getSalt());
            statement.setString(4, user.getFirstName());
            statement.setString(5, user.getLastName());
            statement.setString(6, user.getEmail());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet res = statement.getGeneratedKeys()) {
                    if (res.next()) {
                        int id = res.getInt(1);
                        System.out.println("User created with ID: " + id);
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error creating user: " + e.getMessage());
        }

        return false;
    }
    public Users getUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";

        try (PreparedStatement pstm = this.connection.prepareStatement(query)) {
            pstm.setString(1, email);
            try (ResultSet res = pstm.executeQuery()) {
                if (res.next()) {
                    return fromResultSet(res);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user by email: " + e.getMessage());
        }
        return null;
    }

    private Users fromResultSet(ResultSet res) throws SQLException {
        Users user = new Users();
        user.setId(res.getInt("id"));
        user.setUsername(res.getString("username"));
        user.setPasswordHash(res.getString("password_hash"));
        user.setSalt(res.getString("salt"));
        user.setFirstName(res.getString("firstName"));
        user.setLastName(res.getString("lastName"));
        user.setEmail(res.getString("email"));
        return user;

    }


}
