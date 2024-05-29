package com.example.trucks;

import com.Logs.MyLogger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ChangeTruckInfoController {

    @FXML
    private TextField truckNameField;

    @FXML
    private TextField maxWeightField;

    private Truck truck;

    public void setTruck(Truck truck) {
        this.truck = truck;
        truckNameField.setText(truck.getTruckName());
        maxWeightField.setText(String.valueOf(truck.getMaxWeight()));
    }

    @FXML
    public void handleSaveChanges() {
        String newTruckName = truckNameField.getText().trim();
        double newMaxWeight;

        try {
            newMaxWeight = Double.parseDouble(maxWeightField.getText().trim());
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Max weight must be a number.");
            return;
        }

        if (newTruckName.isEmpty()) {
            showAlert("Invalid Input", "Truck name cannot be empty.");
            return;
        }

        if (newMaxWeight < truck.getCurrentWeight()) {
            showAlert("Invalid Input", "Max weight cannot be less than the current weight.");
            return;
        }

        if (!newTruckName.equals(truck.getTruckName())) {
            renameTruckFiles(truck.getTruckName(), newTruckName);
            truck.setTruckName(newTruckName);
            MyLogger.logInfo("Truck Info Updated");
        }

        truck.setMaxWeight(newMaxWeight);
        truck.updateTruckFile();

        Stage stage = (Stage) truckNameField.getScene().getWindow();
        stage.close();
    }

    private void renameTruckFiles(String oldName, String newName) {
        Path oldTruckFilePath = Paths.get("trucks/" + oldName + ".txt");
        Path newTruckFilePath = Paths.get("trucks/" + newName + ".txt");

        Path oldPackedCoffeeFilePath = Paths.get("trucks/packedCoffee/" + oldName + "PackedCoffee.txt");
        Path newPackedCoffeeFilePath = Paths.get("trucks/packedCoffee/" + newName + "PackedCoffee.txt");

        try {
            Files.move(oldTruckFilePath, newTruckFilePath);
            Files.move(oldPackedCoffeeFilePath, newPackedCoffeeFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to rename truck files.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
