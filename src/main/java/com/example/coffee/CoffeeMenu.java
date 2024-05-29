package com.example.coffee;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CoffeeMenu extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CoffeeMenu.class.getResource("/com/example/coffee/coffee-menu.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            primaryStage.setTitle("Coffee Menu");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
