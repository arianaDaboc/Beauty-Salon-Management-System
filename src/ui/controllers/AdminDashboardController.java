package ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ui.session.Session;
import util.SceneLoader;

public class AdminDashboardController {

    @FXML
    private Label headerLabel;

    private Stage getStage() {
        return (Stage) headerLabel.getScene().getWindow();
    }

    @FXML
    private void onManageStaff(ActionEvent event) {
        SceneLoader.loadScene(event, "/views/manage_staff.fxml");
    }

    @FXML
    private void onManageCategories(ActionEvent event) {
        SceneLoader.loadScene(event, "/views/manage_categories.fxml");
    }

    @FXML
    private void onManageServices(ActionEvent event) {
        SceneLoader.loadScene(event, "/views/manage_services.fxml");
    }

    @FXML
    private void onManageAppointments(ActionEvent event) {
        SceneLoader.loadScene(event, "/views/manage_appointments.fxml");
    }

    @FXML
    private void onLogout(ActionEvent event) {
        Session.clear();
        SceneLoader.loadScene(event, "/views/login.fxml");
    }
}
