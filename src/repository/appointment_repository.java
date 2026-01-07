package repository;
import database.DatabaseConnection;
import model.appointment;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class appointment_repository {
    public void create(appointment a) throws SQLException {
        String sql = "INSERT INTO appointment " +
                "(user_id, service_id, staff_id, appointment_date, appointment_time, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, a.getUserid());
            ps.setInt(2, a.getSericeid());
            ps.setInt(3, a.getStaffId());
            ps.setDate(4, Date.valueOf(a.getAppointmentDate()));
            ps.setTime(5, Time.valueOf(a.getAppointmentTime()));
            ps.setString(6, a.getStatus());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    a.setAppoitmentid(rs.getInt(1));
                }
            }
        }
    }
    public void updateStatus(int id, String status) throws SQLException {
        // Use the same PostgreSQL connection and correct table name as the rest of the repo
        String sql = "UPDATE appointment SET status = ? WHERE appointment_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }
    public appointment findById(int id) throws SQLException {
        String sql = "SELECT * FROM appointment WHERE appointment_id = ?";
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
    public List<appointment> findAll() throws SQLException {
        String sql = "SELECT * FROM appointment ORDER BY appointment_date, appointment_time";
        List<appointment> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }
    public void update(appointment a) throws SQLException {
        String sql = "UPDATE appointment SET user_id=?, service_id=?, staff_id=?, appointment_date=?, " +
                "appointment_time=?, status=? WHERE appointment_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, a.getUserid());
            ps.setInt(2, a.getSericeid());
            ps.setInt(3, a.getStaffId());
            ps.setDate(4, Date.valueOf(a.getAppointmentDate()));
            ps.setTime(5, Time.valueOf(a.getAppointmentTime()));
            ps.setString(6, a.getStatus());
            ps.setInt(7, a.getAppoitmentid());
            ps.executeUpdate();
        }
    }
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM appointment WHERE appointment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    private appointment mapRow(ResultSet rs) throws SQLException {
        appointment a = new appointment();
        a.setAppoitmentid(rs.getInt("appointment_id"));
        a.setUserid(rs.getInt("user_id"));
        a.setSericeid(rs.getInt("service_id"));
        a.setStaffId(rs.getInt("staff_id"));
        a.setAppointmentDate(rs.getDate("appointment_date").toLocalDate());
        a.setAppointmentTime(rs.getTime("appointment_time").toLocalTime());
        a.setStatus(rs.getString("status"));
        return a;
    }
}
