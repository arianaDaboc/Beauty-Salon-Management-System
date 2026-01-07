package ui.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.service;
import model.service_category;
import service.service_service;
import service.service_category_service;
import util.SceneLoader;

public class ManageServicesController {

    @FXML private TableView<service> serviceTable;
    @FXML private TableColumn<service, String> nameColumn;
    @FXML private TableColumn<service, Number> priceColumn;
    @FXML private TableColumn<service, Number> durationColumn;
    @FXML private TableColumn<service, Number> categoryColumn;

    @FXML private TextField nameField;
    @FXML private TextField priceField;
    @FXML private TextField durationField;
    @FXML private ComboBox<service_category> categoryCombo;
    @FXML private Label errorLabel;

    private final service_service serviceService = new service_service();
    private final service_category_service categoryService = new service_category_service();

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getServicename()
                )
        );
        priceColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleDoubleProperty(
                        c.getValue().getPrice()
                )
        );
        durationColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleIntegerProperty(
                        c.getValue().getDurationmin()
                )
        );
        categoryColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleIntegerProperty(
                        c.getValue().getCategoryid()
                )
        );

        try {
            categoryCombo.setItems(
                    FXCollections.observableArrayList(categoryService.findAll())
            );
        } catch (Exception e) {
            errorLabel.setText("Error loading categories.");
        }

        refreshTable();

        serviceTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldSel, newSel) -> {
                    if (newSel != null) {
                        nameField.setText(newSel.getServicename());
                        priceField.setText(String.valueOf(newSel.getPrice()));
                        durationField.setText(String.valueOf(newSel.getDurationmin()));
                        categoryCombo.getItems().stream()
                                .filter(c -> c.getCategory_id() == newSel.getCategoryid())
                                .findFirst()
                                .ifPresent(categoryCombo::setValue);
                    }
                });
    }

    private void refreshTable() {
        try {
            serviceTable.setItems(
                    FXCollections.observableArrayList(serviceService.findAll())
            );
        } catch (Exception e) {
            errorLabel.setText("Error loading services.");
        }
    }

    @FXML
    private void onAdd(ActionEvent event) {
        try {
            service_category cat = categoryCombo.getValue();
            if (cat == null) throw new IllegalArgumentException("Select a category.");

            service s = new service();
            s.setServicename(nameField.getText());
            s.setPrice(Double.parseDouble(priceField.getText()));
            s.setDurationmin(Integer.parseInt(durationField.getText()));
            s.setCategoryid(cat.getCategory_id());

            serviceService.create(s);
            refreshTable();
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void onUpdate(ActionEvent event) {
        service selected = serviceTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            service_category cat = categoryCombo.getValue();
            if (cat == null) throw new IllegalArgumentException("Select a category.");

            selected.setServicename(nameField.getText());
            selected.setPrice(Double.parseDouble(priceField.getText()));
            selected.setDurationmin(Integer.parseInt(durationField.getText()));
            selected.setCategoryid(cat.getCategory_id());

            serviceService.update(selected);
            refreshTable();
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void onDelete(ActionEvent event) {
        service selected = serviceTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            serviceService.delete(selected.getServiceid());
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
