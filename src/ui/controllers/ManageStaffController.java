package ui.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.staff;
import service.staff_service;
import util.SceneLoader;

public class ManageStaffController {

    @FXML private TableView<staff> staffTable;
    @FXML private TableColumn<staff, String> nameColumn;
    @FXML private TableColumn<staff, String> specializationColumn;
    @FXML private TableColumn<staff, String> phoneColumn;

    @FXML private TextField nameField;
    @FXML private TextField specializationField;
    @FXML private TextField phoneField;
    @FXML private Label errorLabel;

    private final staff_service staffService = new staff_service();

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getStaff_name()
                )
        );
        specializationColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getSpecialization()
                )
        );
        phoneColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getPhone_number()
                )
        );

        refreshTable();

        staffTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldSel, newSel) -> {
                    if (newSel != null) {
                        nameField.setText(newSel.getStaff_name());
                        specializationField.setText(newSel.getSpecialization());
                        phoneField.setText(newSel.getPhone_number());
                    }
                });
    }

    private void refreshTable() {
        try {
            staffTable.setItems(
                    FXCollections.observableArrayList(staffService.findAll())
            );
        } catch (Exception e) {
            errorLabel.setText("Error loading staff.");
        }
    }

    @FXML
    private void onAdd(ActionEvent event) {
        try {
            staff s = new staff();
            s.setStaff_name(nameField.getText());
            s.setSpecialization(specializationField.getText());
            s.setPhone_number(phoneField.getText());
            staffService.create(s);
            refreshTable();
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void onUpdate(ActionEvent event) {
        staff selected = staffTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            selected.setStaff_name(nameField.getText());
            selected.setSpecialization(specializationField.getText());
            selected.setPhone_number(phoneField.getText());
            staffService.update(selected);
            refreshTable();
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void onDelete(ActionEvent event) {
        staff selected = staffTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            staffService.delete(selected.getStaff_id());
            refreshTable();
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void onBack(ActionEvent event) {
        SceneLoader.loadScene(event, "/views/admin_dashboard.fxml");
    }
}
