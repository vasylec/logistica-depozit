package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import com.example.Database.DatabaseConnection;

public class App extends Application {

    private static Scene scene;
    static Long warehouseID;

    @Override
    @SuppressWarnings("exports")
    public void start(Stage stage) throws IOException {

        DatabaseConnection.warehouseTable = DatabaseConnection.selectFromWarehouse();

        scene = new Scene(loadFXML("login"), 640, 480);
        stage.setMinHeight(400);
        stage.setMinWidth(600);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}