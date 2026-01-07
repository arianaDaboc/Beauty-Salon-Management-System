package ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import util.SceneLoader;
import ui.session.Session;

public class CustomerHomeController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private void initialize() {
        if (Session.getCurrentUser() != null) {
            welcomeLabel.setText(
                    "Welcome, " + Session.getCurrentUser().getUsername()
            );
        } else {
            welcomeLabel.setText("Welcome");
        }
    }

    @FXML
    private void onViewServices(ActionEvent event) {
        SceneLoader.loadScene(event, "/views/service_list.fxml");
    }

    @FXML
    private void onBookAppointment(ActionEvent event) {
        SceneLoader.loadScene(event, "/views/book_appointment.fxml");
    }

    @FXML
    private void onMyAppointments(ActionEvent event) {
        SceneLoader.loadScene(event, "/views/my_appointments.fxml");
    }

    @FXML
    private void onLogout(ActionEvent event) {
        Session.clear();
        SceneLoader.loadScene(event, "/views/login.fxml");
    }
}
