package repository;
import database.DatabaseConnection;
import model.service_category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class service_category_repository {
    public void create(service_category c) throws SQLException {
        String SQL="INSERT INTO service_category (category_name) VALUES(?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1,c.getCategory_name());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    c.setCategory_id(rs.getInt(1));
                }
            }
        }
    }
    public service_category findById(int id) throws SQLException {
        String sql = "SELECT * FROM service_category WHERE category_id = ?";
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
    public List<service_category> findAll() throws SQLException {
        String sql = "SELECT * FROM service_category ORDER BY category_id";
        List<service_category> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }
    public void update(service_category c) throws SQLException {
        String sql = "UPDATE service_category SET category_name=? WHERE category_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getCategory_name());
            ps.setInt(2, c.getCategory_id());
            ps.executeUpdate();
        }
    }
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM service_category WHERE category_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    private service_category mapRow(ResultSet rs) throws SQLException {
        service_category c = new service_category();
        c.setCategory_id(rs.getInt("category_id"));
        c.setCategory_name(rs.getString("category_name"));
        return c;
    }
}
