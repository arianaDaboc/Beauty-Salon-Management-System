package ui.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.appointment;
import service.appointment_service;
import ui.session.Session;
import util.SceneLoader;

public class MyAppointmentsController {

    @FXML private TableView<appointment> appointmentTable;
    @FXML private TableColumn<appointment, String> serviceColumn;
    @FXML private TableColumn<appointment, String> staffColumn;
    @FXML private TableColumn<appointment, String> dateColumn;
    @FXML private TableColumn<appointment, String> timeColumn;
    @FXML private TableColumn<appointment, String> statusColumn;

    private final appointment_service appointmentService = new appointment_service();

    @FXML
    private void initialize() {

        dateColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getAppointmentDate().toString()
                )
        );

        timeColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getAppointmentTime().toString()
                )
        );

        statusColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getStatus()
                )
        );

        serviceColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        String.valueOf(c.getValue().getSericeid())
                )
        );

        staffColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        String.valueOf(c.getValue().getStaffId())
                )
        );

        try {
            appointmentTable.setItems(
                    FXCollections.observableArrayList(
                            appointmentService.findByUser(
                                    Session.getCurrentUser().getUserId()
                            )
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onBack(javafx.event.ActionEvent event) {
        SceneLoader.loadScene(event, "/views/customer_home.fxml");
    }
}
