package ui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.service;
import service.service_service;
import util.SceneLoader;
import ui.session.Session;

public class ServiceListController {

    @FXML private TableView<service> serviceTable;
    @FXML private TableColumn<service, String> nameColumn;
    @FXML private TableColumn<service, Number> priceColumn;
    @FXML private TableColumn<service, Number> durationColumn;

    private final service_service serviceService = new service_service();

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

        ObservableList<service> list =
                FXCollections.observableArrayList(serviceService.findAll());

        serviceTable.setItems(list);
    }

    @FXML
    private void onBack(javafx.event.ActionEvent event) {
        SceneLoader.loadScene(event, "/views/customer_home.fxml");
    }

    @FXML
    private void onBookSelected(javafx.event.ActionEvent event) {
        service selected = serviceTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        Session.setSelectedServiceId(selected.getServiceid());
        SceneLoader.loadScene(event, "/views/book_appointment.fxml");
    }

}
