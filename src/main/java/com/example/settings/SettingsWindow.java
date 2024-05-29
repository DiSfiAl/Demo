package com.example.settings;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SettingsWindow extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Settings");

        ComboBox<String> languageComboBox = new ComboBox<>();
        languageComboBox.getItems().addAll("Українська", "English");

        VBox vbox = new VBox(languageComboBox);
        Scene scene = new Scene(vbox, 300, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
