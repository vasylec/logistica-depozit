package com.example.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.Database.DatabaseConnection;
import com.example.Model.Warehouse;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DatabaseWarehouseController implements Initializable {

    @FXML
    TableView<Warehouse> tableView;

    @FXML
    TableColumn<Warehouse, String> numeTableColumn;
    @FXML
    TableColumn<Warehouse, String> locatieTableColumn;
    @FXML
    TableColumn<Warehouse, Integer> capacitateTableColumn;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        numeTableColumn.setCellValueFactory(new PropertyValueFactory<>("nume"));
        locatieTableColumn.setCellValueFactory(new PropertyValueFactory<>("locatie"));
        capacitateTableColumn.setCellValueFactory(new PropertyValueFactory<>("capacitate"));

        tableView.setItems(DatabaseConnection.warehouseTable);
    }
}
