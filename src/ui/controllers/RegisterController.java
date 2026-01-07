package ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.user;
import service.user_service;
import util.SceneLoader;

public class RegisterController {

    @FXML private TextField fullNameField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private Label errorLabel;

    private final user_service userService = new user_service();

    @FXML
    private void onRegisterClicked(ActionEvent event) {
        try {
            user u = new user();
            u.setFullName(fullNameField.getText());
            u.setUsername(usernameField.getText());
            u.setPhone(phoneField.getText());
            u.setEmail(emailField.getText());
            u.setRoleId(2);
            userService.register(u, passwordField.getText());

            SceneLoader.loadScene(event, "/views/login.fxml");

        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void onBackToLogin(ActionEvent event) {
        SceneLoader.loadScene(event, "/views/login.fxml");
    }
}
