package ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import model.user;
import service.user_service;
import ui.session.Session;
import util.SceneLoader;

public class LoginController {

    private final user_service userService = new user_service();

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;
    @FXML
    private void onRegisterClicked(ActionEvent event) {
        SceneLoader.loadScene(event, "/views/register.fxml");
    }

    @FXML
    private void onLoginClicked(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Username or password cannot be empty");
            return;
        }

        user u = userService.login(username, password);

        if (u == null) {
            errorLabel.setText("Invalid credentials");
            return;
        }
        Session.setCurrentUser(u);
        System.out.println("Login OK: " + u.getUsername() + " | role = " + u.getRole());

        if ("ADMIN".equalsIgnoreCase(u.getRole())) {
            SceneLoader.loadScene(event, "/views/admin_dashboard.fxml");
        } else {
            SceneLoader.loadScene(event, "/views/customer_home.fxml");
        }
    }

}
