package com.example.Controllers;

import com.example.Database.DatabaseConnection;
import com.example.Model.Inventory;
import com.example.Model.Supplier;
import com.example.Model.Warehouse;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {

    @FXML TableView<Inventory> tableView;
    @FXML TableColumn<Inventory, Integer> warehouseIdTableColumn;
    @FXML TableColumn<Inventory, Integer> productIdTableColumn;
    @FXML TableColumn<Inventory, Integer> quantityTableColumn;
    @FXML TableColumn<Inventory, Timestamp> lastUpdatedTableColumn;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        warehouseIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("warehouseId"));
        productIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        quantityTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        lastUpdatedTableColumn.setCellValueFactory(new PropertyValueFactory<>("lastUpdated"));
        tableView.setItems(DatabaseConnection.inventoryTable);
    }
}
