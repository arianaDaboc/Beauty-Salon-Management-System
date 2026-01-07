package service;

import model.appointment;
import model.staff;
import model.service;
import model.user;

import repository.appointment_repository;
import repository.staff_repository;
import repository.service_repository;
import repository.user_repository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;
public class appointment_service {

    private final appointment_repository repo = new appointment_repository();
    private final staff_repository staffRepo = new staff_repository();
    private final service_repository serviceRepo = new service_repository();
    private final user_repository userRepo = new user_repository();
    public void updateStatus(int id, String status) throws Exception {
        if (repo.findById(id) == null) {
            throw new Exception("Appointment not found.");
        }
        repo.updateStatus(id, status);
    }
    public void create(appointment a) throws Exception {
        // Validate date/time
        if (a.getAppointmentDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Appointment date must be in the future.");
        }
        if (a.getAppointmentTime().isBefore(LocalTime.now()) &&
                a.getAppointmentDate().isEqual(LocalDate.now())) {
            throw new IllegalArgumentException("Appointment time must be in the future.");
        }
        // Validate foreign keys
        if (userRepo.findById(a.getUserid()) == null) {
            throw new Exception("User not found.");
        }
        if (staffRepo.findbyid(a.getStaffId()) == null) {
            throw new Exception("Staff not found.");
        }
        if (serviceRepo.findById(a.getSericeid()) == null) {
            throw new Exception("Service not found.");
        }
        // check double booking
        List<appointment> existing = repo.findAll();
        for (appointment ap : existing) {
            if (ap.getStaffId() == a.getStaffId() &&
                    ap.getAppointmentDate().equals(a.getAppointmentDate()) &&
                    ap.getAppointmentTime().equals(a.getAppointmentTime()) &&
                    ap.getStatus().equals("Booked")) {

                throw new Exception("Staff is not available at this timeslot.");
            }
        }

        repo.create(a);
    }
    public appointment findById(int id) throws Exception {
        appointment a = repo.findById(id);
        if (a == null) throw new Exception("Appointment not found.");
        return a;
    }
    public List<appointment> findAll() {
        try {
            return repo.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<appointment> findByUser(int userId) {
        List<appointment> result = new ArrayList<>();

        for (appointment a : findAll()) {
            if (a.getUserid() == userId) {
                result.add(a);
            }
        }
        return result;
    }
    public void update(appointment a) throws Exception {
        if (repo.findById(a.getAppoitmentid()) == null) {
            throw new Exception("Appointment not found.");
        }
        repo.update(a);
    }
    public void cancel(int id) throws Exception {
        appointment a = repo.findById(id);
        if (a == null) throw new Exception("Appointment not found.");

        a.setStatus("Cancelled");
        repo.update(a);
    }
    public void complete(int id) throws Exception {
        appointment a = repo.findById(id);
        if (a == null) throw new Exception("Appointment not found.");

        a.setStatus("Completed");
        repo.update(a);
    }
    public void delete(int id) throws Exception {
        if (repo.findById(id) == null) {
            throw new Exception("Appointment not found.");
        }
        repo.delete(id);
    }
}
