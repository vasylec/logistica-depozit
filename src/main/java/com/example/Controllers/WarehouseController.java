package com.example.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.Database.DatabaseConnection;
import com.example.Model.Warehouse;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class WarehouseController implements Initializable {

    @FXML
    TableView<Warehouse> tableView;

    @FXML
    TableColumn<Warehouse, String> nameTableColumn;
    @FXML
    TableColumn<Warehouse, String> locationTableColumn;
    @FXML
    TableColumn<Warehouse, Integer> capacityTableColumn;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        locationTableColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        capacityTableColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        tableView.setItems(DatabaseConnection.warehouseTable);
    }
}
