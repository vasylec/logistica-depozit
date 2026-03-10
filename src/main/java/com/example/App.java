package com.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import com.example.Database.DatabaseConnection;

public class App extends Application {

    private static Scene scene;
    public static Long warehouseID;
    public static String errorMessage;
    public static String reception_tranfer;

    @Override
    @SuppressWarnings("exports")
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("loadingScreen"), 640, 480);
        App.setRoot("loadingScreen");

        Task<Void> dbTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                DatabaseConnection.initConnect();
                return null;
            }
        };

        dbTask.setOnSucceeded(e -> {
            try {
                App.setRoot("login");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        dbTask.setOnFailed(e -> {
            Throwable ex = dbTask.getException(); // captura excepția din task
            ex.printStackTrace(); // vezi ce a cauzat fail

            App.errorMessage = "Database connection failure: " + ex.getMessage();
            try {
                App.setRoot("errorPage");
            } catch (Exception ex2) {
                ex2.printStackTrace();
            }
        });

        new Thread(dbTask).start();

        stage.setMinHeight(400);
        stage.setMinWidth(600);
        stage.setScene(scene);
        stage.show();
    }

    @SuppressWarnings("exports")
    public static Parent getRoot() {
        return scene.getRoot();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    @SuppressWarnings("exports")
    public static void setRoot(Parent root) throws IOException {
        scene.setRoot(root);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}