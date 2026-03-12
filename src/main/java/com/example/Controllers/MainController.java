package com.example.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.App;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

public class MainController implements Initializable {

    @FXML MenuItem logout;
    @FXML MenuItem reportSpecificReceipt;
    @FXML MenuItem reportSpecificWarehouse;
    @FXML MenuItem receiptMenuItem;
    @FXML MenuItem warehouseMenuItem;
    @FXML MenuItem supplierMenuItem;
    @FXML MenuItem inventoryMenuItem;
    @FXML MenuItem sendTransfer;
    @FXML MenuItem getTransfer;
    @FXML MenuItem regReception;
    @FXML MenuItem getTransferHistory;
    @FXML MenuItem historyReception;
    @FXML MenuItem reportWarehouses;

    @FXML BorderPane root;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        setMethodsToAllObjects();
    }

    private void setMethodsToAllObjects() {

        warehouseMenuItem.setOnAction(eventHanderMenuItem("warehouse",""));
        supplierMenuItem.setOnAction(eventHanderMenuItem("supplier",""));
        inventoryMenuItem.setOnAction(eventHanderMenuItem("inventory",""));
        receiptMenuItem.setOnAction(eventHanderMenuItem("receipt",""));
        sendTransfer.setOnAction(eventHanderMenuItem("sendTransfer",""));

        reportWarehouses.setOnAction(eventHanderMenuItem("reports/reportWarehouses",""));
        reportSpecificWarehouse.setOnAction(eventHanderMenuItem("reports/reportSpecificWarehouse",""));
        reportSpecificReceipt.setOnAction(eventHanderMenuItem("reports/reportSpecificReceipt",""));

        regReception.setOnAction(eventHanderMenuItem("regReceptie","reception"));
        getTransfer.setOnAction(eventHanderMenuItem("regReceptie","get transfer"));
        getTransferHistory.setOnAction(eventHanderMenuItem("regReceptie","get transfer history"));
        historyReception.setOnAction(eventHanderMenuItem("regReceptie","get receipt history"));

//        regReception.setOnAction(eventHanderMenuItem_regReception("regReceptie"));
//        getTransfer.setOnAction(eventHanderMenuItem_regGetTransfer("regReceptie"));
//        getTransferHistory.setOnAction(eventHanderMenuItem_regGetTransferHistory("regReceptie"));
//        historyReception.setOnAction(eventHanderMenuItem_regGetReceptionHistory("regReceptie"));

        logout.setOnAction(logout());

    }

    private EventHandler<ActionEvent> logout() {
        return event -> {
            try {
                App.reception_tranfer = "";
                App.warehouseID = null;
                App.errorMessage = null;

                App.setRoot("login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    private EventHandler<ActionEvent> eventHanderMenuItem(String fxmlFileName, String receptionTransfer) {
        return event -> {
            try {
                if(!receptionTransfer.isEmpty())
                    App.reception_tranfer=receptionTransfer;

                FXMLLoader loader = new FXMLLoader(App.class.getResource("fxml/" + fxmlFileName + ".fxml"));
                Node view = loader.load();
                root.setCenter(view);

            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
