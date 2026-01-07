package ui.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.appointment;
import service.appointment_service;
import util.SceneLoader;

public class ManageAppointmentsController {

    @FXML private TableView<appointment> appointmentTable;

    @FXML private TableColumn<appointment, Number> idColumn;
    @FXML private TableColumn<appointment, Number> userColumn;
    @FXML private TableColumn<appointment, Number> serviceColumn;
    @FXML private TableColumn<appointment, Number> staffColumn;

    @FXML private TableColumn<appointment, String> dateColumn;
    @FXML private TableColumn<appointment, String> timeColumn;
    @FXML private TableColumn<appointment, String> statusColumn;

    private final appointment_service appointmentService = new appointment_service();

    @FXML
    private void initialize() {

        idColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleIntegerProperty(c.getValue().getAppoitmentid()));

        userColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleIntegerProperty(c.getValue().getUserid()));

        serviceColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleIntegerProperty(c.getValue().getSericeid()));

        staffColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleIntegerProperty(c.getValue().getStaffId()));

        dateColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getAppointmentDate().toString()));

        timeColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getAppointmentTime().toString()));

        statusColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getStatus()));

        refreshTable();
    }

    private void refreshTable() {
        try {
            appointmentTable.setItems(
                    FXCollections.observableArrayList(appointmentService.findAll())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private appointment getSelected() {
        return appointmentTable.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void onSetCompleted() {
        appointment a = getSelected();
        if (a == null) return;

        try {
            appointmentService.updateStatus(a.getAppoitmentid(), "Completed");
            refreshTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onSetCancelled() {
        appointment a = getSelected();
        if (a == null) return;

        try {
            appointmentService.updateStatus(a.getAppoitmentid(), "Cancelled");
            refreshTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void onBack(ActionEvent event) {
        SceneLoader.loadScene(event, "/views/admin_dashboard.fxml");
    }
}
