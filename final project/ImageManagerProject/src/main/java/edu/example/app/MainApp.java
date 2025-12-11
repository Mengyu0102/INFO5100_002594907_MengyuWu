package edu.example.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * MainApp - entry point for the JavaFX application.
 * Sets up the main window and loads the Controller.
 */
public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Controller controller = new Controller(primaryStage);
            Scene scene = new Scene(controller.getRoot(), 1000, 600);
            primaryStage.setTitle("Image Management Tool - Demo");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR, "Failed to start application: " + e.getMessage());
            a.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
