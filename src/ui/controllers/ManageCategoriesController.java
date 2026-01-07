package ui.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.service_category;
import service.service_category_service;
import util.SceneLoader;

public class ManageCategoriesController {

    @FXML private TableView<service_category> categoryTable;
    @FXML private TableColumn<service_category, String> nameColumn;
    @FXML private TextField nameField;
    @FXML private Label errorLabel;

    private final service_category_service categoryService = new service_category_service();

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getCategory_name()
                )
        );

        refreshTable();

        categoryTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldSel, newSel) -> {
                    if (newSel != null) {
                        nameField.setText(newSel.getCategory_name());
                    }
                });
    }

    private void refreshTable() {
        try {
            categoryTable.setItems(
                    FXCollections.observableArrayList(categoryService.findAll())
            );
        } catch (Exception e) {
            errorLabel.setText("Error loading categories.");
        }
    }

    @FXML
    private void onAdd(ActionEvent event) {
        try {
            service_category c = new service_category();
            c.setCategory_name(nameField.getText());
            categoryService.create(c);
            refreshTable();
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void onUpdate(ActionEvent event) {
        service_category selected =
                categoryTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            selected.setCategory_name(nameField.getText());
            categoryService.update(selected);
            refreshTable();
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void onDelete(ActionEvent event) {
        service_category selected =
                categoryTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            categoryService.delete(selected.getCategory_id());
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
