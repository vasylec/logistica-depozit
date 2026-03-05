package com.example.Controllers;

import com.example.Database.DatabaseConnection;
import com.example.Model.Receipt;
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

public class ReceiptController implements Initializable {

    @FXML TableView<Receipt> tableView;
    @FXML TableColumn<Receipt, Integer> supplierIdTableColumn;
    @FXML TableColumn<Receipt, Integer> warehouseIdTableColumn;
    @FXML TableColumn<Receipt, Timestamp> receiptDateTableColumn;
    @FXML TableColumn<Receipt, String> documentNumberTableColumn;
    @FXML TableColumn<Receipt, String> notesTableColumn;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        supplierIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        warehouseIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("warehouseId"));
        receiptDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("receiptDate"));
        documentNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("documentNumber"));
        notesTableColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));
        tableView.setItems(DatabaseConnection.receiptTable);
    }
}
