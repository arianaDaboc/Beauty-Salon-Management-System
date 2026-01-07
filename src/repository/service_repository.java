package repository;
import database.DatabaseConnection;
import model.service;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class service_repository {
    public void create(service s) throws SQLException{
        String sql="INSERT INTO service (service_name, price, duration_minutes, category_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, s.getServicename());
            ps.setDouble(2, s.getPrice());
            ps.setInt(3, s.getDurationmin());
            ps.setInt(4, s.getCategoryid());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    s.setServiceId(rs.getInt(1));
                }
            }
        }
    }
    public service findById(int id) throws SQLException {
        String sql = "SELECT * FROM service WHERE service_id = ?";
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
    public List<service> findAll() throws SQLException {
        String sql = "SELECT * FROM service ORDER BY service_id";
        List<service> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }
    public void update(service s) throws SQLException {
        String sql = "UPDATE service SET service_name=?, price=?, duration_minutes=?, category_id=? WHERE service_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getServicename());
            ps.setDouble(2, s.getPrice());
            ps.setInt(3, s.getDurationmin());
            ps.setInt(4, s.getCategoryid());
            ps.setInt(5, s.getServiceid());
            ps.executeUpdate();
        }
    }
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM service WHERE service_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    private service mapRow(ResultSet rs) throws SQLException {
        service s = new service();
        s.setServiceId(rs.getInt("service_id"));
        s.setServicename(rs.getString("service_name"));
        s.setPrice(rs.getDouble("price"));
        s.setDurationmin(rs.getInt("duration_minutes"));
        s.setCategoryid(rs.getInt("category_id"));
        return s;
    }
}

