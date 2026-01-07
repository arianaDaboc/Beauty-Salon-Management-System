package repository;
import database.DatabaseConnection;
import model.role;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class role_repository {
    public void create(role r) throws SQLException {
        String sql = "INSERT INTO role (role_name) VALUES (?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, r.getrolename());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    r.setroleid(rs.getInt(1));
                }
            }
        }
    }
    public role findById(int id) throws SQLException {
        String sql = "SELECT * FROM role WHERE role_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }
    public List<role> findAll() throws SQLException {
        String sql = "SELECT * FROM role ORDER BY role_id";
        List<role> roles = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                roles.add(mapRow(rs));
            }
        }
        return roles;
    }
    public void update(role r) throws SQLException {
        String sql = "UPDATE role SET role_name=? WHERE role_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, r.getrolename());
            ps.setInt(2, r.getroleid());

            ps.executeUpdate();
        }
    }
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM role WHERE role_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    private role mapRow(ResultSet rs) throws SQLException {
        role r = new role();
        r.setroleid(rs.getInt("role_id"));
        r.setrolename(rs.getString("role_name"));
        return r;
    }
}


