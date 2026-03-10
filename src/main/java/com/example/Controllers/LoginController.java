package com.example.Controllers;

import java.io.IOException;
import java.sql.SQLException;

import com.example.App;
import com.example.Database.DatabaseConnection;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    TextField textField;

    @FXML
    Label label;

    @FXML
    public void login() throws SQLException {

        boolean logged = false;

        textField.setOnKeyPressed(e -> {
            label.setVisible(false);
        });

        try {
            logged = DatabaseConnection.getLogin(Long.parseLong(textField.getText()));
        } catch (Exception e) {
            label.setVisible(true);
            e.printStackTrace();
        }

        if (logged == false) {
            label.setVisible(true);
        }

        if (logged) {

            App.warehouseID = Long.parseLong(textField.getText());
            Task<Void> dbTask = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    DatabaseConnection.init();
                    return null;
                }
            };

            dbTask.setOnRunning(e -> {
                try {
                    App.setRoot("loadingScreen");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });

            dbTask.setOnSucceeded(e -> {
                try {
                    App.setRoot("main");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });

            dbTask.setOnFailed(e -> {
                Throwable ex = dbTask.getException(); // captura excepția din task
                ex.printStackTrace(); // vezi ce a cauzat fail
                App.errorMessage = "Database connection failure: " + ex.getMessage();
                try {
                    // App.errorMessage = "Database connection failure";
                    App.setRoot("errorPage");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });

            new Thread(dbTask).start();
        }
    }
}
