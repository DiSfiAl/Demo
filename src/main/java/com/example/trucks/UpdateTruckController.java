package com.example.trucks;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class UpdateTruckController {
    @FXML
    private TextField nameField;

    @FXML
    private TextField maxWeightField;

    @FXML
    void sentTruck() throws IOException {
        String name = nameField.getText();
        double maxWeight = Double.parseDouble(maxWeightField.getText());

        Truck newTruck = new Truck(name, maxWeight);
        saveTruckToFile(newTruck);

        // Закриваємо вікно
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    @FXML
    void updateTruck() throws IOException {
        String name = nameField.getText();
        double maxWeight = Double.parseDouble(maxWeightField.getText());

        Truck newTruck = new Truck(name, maxWeight);
        saveTruckToFile(newTruck);

        // Закриваємо вікно
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    private void saveTruckToFile(Truck truck) throws IOException {
        File file = new File("trucks/" + truck.getTruckName() + ".txt");
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println("Truck name: " + truck.getTruckName());
            writer.println("Max Weight: " + truck.getMaxWeight());
            writer.println("Current Weight: " + truck.getCurrentWeight());
            writer.println("Status: " + truck.isCompleted());
        }
    }
}
