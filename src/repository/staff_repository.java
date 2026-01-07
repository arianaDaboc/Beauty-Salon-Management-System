package repository;
import database.DatabaseConnection;
import model.staff;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class staff_repository {
    public void create(staff s) throws SQLException {
        String sql = "INSERT INTO staff(staff_name, specialization, phone) VALUES (?,?,?)";
        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, s.getStaff_name());
            ps.setString(2, s.getSpecialization());
            ps.setString(3, s.getPhone_number());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    s.setStaff_id(rs.getInt(1));
                }
            }
        }
    }
    public staff findbyid(int id) throws SQLException {
        String sql = "SELECT * FROM staff WHERE staff_id = ?";
        try(Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapRow(rs);
                    }
                }
            }
            return null;
            }
        }
    public List<staff> findall() throws SQLException {
        String sql = "SELECT * FROM staff ORDER BY staff_id";
        List<staff> staffList= new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                staffList.add(mapRow(rs));
            }
        }
        return staffList;
    }
    public void update(staff s) throws SQLException {
        String sql = "UPDATE staff SET staff_name = ?, specialization = ?, phone = ? WHERE staff_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getStaff_name());
            ps.setString(2, s.getSpecialization());
            ps.setString(3, s.getPhone_number());
            ps.setInt(4, s.getStaff_id());
            ps.executeUpdate();
        }
    }
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM staff WHERE staff_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    private staff mapRow(ResultSet rs) throws SQLException {
        staff s = new staff();
        s.setStaff_id(rs.getInt("staff_id"));
        s.setStaff_name(rs.getString("staff_name"));
        s.setSpecialization(rs.getString("specialization"));
        s.setPhone_number(rs.getString("phone"));
        return s;
    }
    }