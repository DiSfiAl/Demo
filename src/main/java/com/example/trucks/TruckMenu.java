package com.example.trucks;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TruckMenu extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Truck Menu");

        Label label = new Label("Truck Menu");
        VBox vbox = new VBox(label);
        Scene scene = new Scene(vbox, 1080, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
