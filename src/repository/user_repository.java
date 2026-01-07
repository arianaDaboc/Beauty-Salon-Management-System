package repository;

import database.DatabaseConnection;
import model.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class user_repository {

    private user mapRow(ResultSet rs) throws Exception {
        user u = new user();
        u.setUserId(rs.getInt("user_id"));
        u.setUsername(rs.getString("username"));
        u.setPasswordHash(rs.getString("password_hash"));
        u.setFullName(rs.getString("full_name"));
        u.setPhone(rs.getString("phone"));
        u.setEmail(rs.getString("email"));
        u.setRoleId(rs.getInt("role_id"));
        String roleName = null;
        try {
            roleName = rs.getString("role_name");
        } catch (Exception ignored) {
        }
        u.setRole(roleName);
        return u;
    }

    public user findByUsername(String username) {
        String sql = """
            SELECT u.*, r.role_name
            FROM app_user u
            JOIN role r ON u.role_id = r.role_id
            WHERE u.username = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRow(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public user findById(int id) {
        String sql = """
            SELECT u.*, r.role_name
            FROM app_user u
            JOIN role r ON u.role_id = r.role_id
            WHERE u.user_id = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRow(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void create(user u) throws Exception {
        String sql = """
            INSERT INTO app_user (username, password_hash, full_name, phone, email, role_id)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getUsername());
            stmt.setString(2, u.getPasswordHash());
            stmt.setString(3, u.getFullName());
            stmt.setString(4, u.getPhone());
            stmt.setString(5, u.getEmail());
            stmt.setInt(6, u.getRoleId());

            stmt.executeUpdate();
        }
    }

    public void updatePasswordHash(int userId, String newPasswordHash) throws Exception {
        String sql = """
            UPDATE app_user 
            SET password_hash = ? 
            WHERE user_id = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newPasswordHash);
            stmt.setInt(2, userId);

            stmt.executeUpdate();
        }
    }
}
