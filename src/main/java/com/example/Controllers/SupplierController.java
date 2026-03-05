package com.example.Controllers;

import com.example.Database.DatabaseConnection;
import com.example.Model.Supplier;
import com.example.Model.Warehouse;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class SupplierController implements Initializable {

    @FXML TableView<Supplier> tableView;
    @FXML TableColumn<Supplier, String> nameTableColumn;
    @FXML TableColumn<Supplier, String> descriptionTableColumn;
    @FXML TableColumn<Supplier, String> contactTableColumn;
    @FXML TableColumn<Supplier, String> addressTableColumn;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        contactTableColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        addressTableColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        tableView.setItems(DatabaseConnection.supplierTable);
    }
}
