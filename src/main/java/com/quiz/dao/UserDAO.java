package com.quiz.dao;

import com.quiz.model.User;
import com.quiz.util.DatabaseUtil;
import java.sql.*;

public class UserDAO {

    public User getUserByUserId(String userId) throws SQLException {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        String sql = "SELECT * FROM users WHERE userid = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("id"),
                        rs.getString("fname"),
                        rs.getString("lname"),
                        rs.getString("email"),
                        rs.getString("userid"),
                        rs.getString("password")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Database error. Please contact the administrator.", e);
        }
        return null;
    }

    public void addUser(User user) throws SQLException {
        validateUser(user);

        String sql = "INSERT INTO users (fname, lname, email, userid, password) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getUserId());
            pstmt.setString(5, user.getPassword());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Database error. Please contact the administrator.", e);
        }
    }

    public boolean userExists(String userId) throws SQLException {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        String sql = "SELECT COUNT(*) FROM users WHERE userid = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Database error. Please contact the administrator.", e);
        }
        return false;
    }

    private void validateUser(User user) {
        if (user.getFirstName() == null || user.getLastName() == null || user.getEmail() == null ||
                user.getUserId() == null || user.getPassword() == null ||
                user.getFirstName().isEmpty() || user.getLastName().isEmpty() || user.getEmail().isEmpty() ||
                user.getUserId().isEmpty() || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("All user fields are required and must not be empty");
        }
    }
}
