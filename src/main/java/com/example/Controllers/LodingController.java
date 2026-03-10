package com.example.Controllers;

import java.io.IOException;

import com.example.App;
import com.example.Database.DatabaseConnection;

import javafx.concurrent.Task;

public class LodingController {
    public static void setLoading(String setOnSucceeded, String errorMessage) {

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
                App.setRoot(setOnSucceeded);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        dbTask.setOnFailed(e -> {
            try {
                App.errorMessage = errorMessage;
                App.setRoot("errorPage");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        new Thread(dbTask).start();
    }
}
