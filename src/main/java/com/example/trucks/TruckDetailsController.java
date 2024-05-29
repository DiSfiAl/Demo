package com.example.trucks;

import javafx.fxml.FXML;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class TruckDetailsController {

    @FXML
    private Label truckNameLabel;

    @FXML
    private Label maxWeightLabel;

    @FXML
    private Label currentWeightLabel;

    @FXML
    private Label isCompleted;

    private Truck truck;

    public void setTruck(Truck truck) {
        this.truck = truck;
        updateTruckDetails();
    }

    private void updateTruckDetails() {
        DecimalFormat df = new DecimalFormat("#.00");
        truckNameLabel.setText(truck.getTruckName());
        maxWeightLabel.setText("Max Weight : " + df.format(truck.getMaxWeight()) + " kg");
        currentWeightLabel.setText("Current Weight : " + df.format(truck.getCurrentWeight()) + " kg");
        if(!truck.isCompleted())
            isCompleted.setText("Not sent");
        else
            isCompleted.setText("Sent");
    }

    @FXML
    void packCoffee() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/trucks/pack-coffee.fxml"));
        Parent root = loader.load();

        PackCoffeeController controller = loader.getController();
        controller.setTruck(truck);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void viewPackedCoffeeList() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/trucks/coffee-list.fxml"));
        Parent root = loader.load();

        CoffeeListController controller = loader.getController();
        controller.setTruck(truck);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void changeInfoAboutTruck() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/trucks/change-truck-info.fxml"));
        Parent root = loader.load();

        ChangeTruckInfoController controller = loader.getController();
        controller.setTruck(truck);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void deleteTruck() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete Truck");
        alert.setContentText("Are you sure you want to delete this truck?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            // Delete truck logic
            String truckFileName = "trucks/" + truck.getTruckName() + ".txt";
            String packedCoffeeFileName = "trucks/packedCoffee/" + truck.getTruckName() + "PackedCoffee.txt";

            try {
                Files.deleteIfExists(Paths.get(truckFileName));
                Files.deleteIfExists(Paths.get(packedCoffeeFileName));
                Stage stage = (Stage) truckNameLabel.getScene().getWindow();
                stage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
