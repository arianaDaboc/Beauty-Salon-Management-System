package ui.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.appointment;
import model.service;
import model.staff;
import service.appointment_service;
import service.service_service;
import service.staff_service;
import ui.session.Session;
import util.SceneLoader;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookAppointmentController {

    @FXML private ComboBox<service> serviceCombo;
    @FXML private ComboBox<staff> staffCombo;
    @FXML private DatePicker datePicker;
    @FXML private TextField timeField;
    @FXML private Label errorLabel;

    private final service_service serviceService = new service_service();
    private final staff_service staffService = new staff_service();
    private final appointment_service appointmentService = new appointment_service();

    @FXML
    private void initialize() {
        try {
            serviceCombo.setItems(
                    FXCollections.observableArrayList(serviceService.findAll())
            );
            staffCombo.setItems(
                    FXCollections.observableArrayList(staffService.findAll())
            );
        } catch (Exception e) {
            errorLabel.setText("Error loading data.");
        }
    }

    @FXML
    private void onBook(javafx.event.ActionEvent event) {
        try {
            service s = serviceCombo.getValue();
            staff st = staffCombo.getValue();
            LocalDate date = datePicker.getValue();
            LocalTime time = LocalTime.parse(timeField.getText());

            if (s == null || st == null || date == null) {
                throw new IllegalArgumentException("Please fill all fields.");
            }

            appointment a = new appointment();
            a.setUserid(Session.getCurrentUser().getUserId());
            a.setSericeid(s.getServiceid());
            a.setStaffId(st.getStaff_id());
            a.setAppointmentDate(date);
            a.setAppointmentTime(time);
            a.setStatus("Booked");

            appointmentService.create(a);

            SceneLoader.loadScene(event, "/views/my_appointments.fxml");

        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void onBack(javafx.event.ActionEvent event) {
        SceneLoader.loadScene(event, "/views/customer_home.fxml");
    }
}
