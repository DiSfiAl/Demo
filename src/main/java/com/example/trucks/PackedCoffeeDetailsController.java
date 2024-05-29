package com.example.trucks;

import com.example.coffee.Coffee;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public class PackedCoffeeDetailsController {
    @FXML
    private Label coffeeDetailsLabel;
    private Coffee coffee;
    private Truck truck;
    private Stage stage;

    public void setCoffeeDetails(Coffee coffee, Truck truck, Stage stage) {
        this.coffee = coffee;
        this.truck = truck;
        this.stage = stage;
        coffeeDetailsLabel.setText(coffee.toWritePacked());
    }

    public void setCoffee(Coffee coffee, Truck truck) {
        coffeeDetailsLabel.setText(coffee.toWritePacked());
        this.coffee = coffee;
        this.truck = truck;
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) coffeeDetailsLabel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void changeQuantity() {
        TextInputDialog dialog = new TextInputDialog(String.valueOf(coffee.getQuantity()));
        dialog.setTitle("Змінити кількість");
        dialog.setHeaderText(null);
        dialog.setContentText("Нова кількість:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                int newQuantity = Integer.parseInt(result.get());
                if (newQuantity <= 0) {
                    showErrorAlert("Кількість повинна бути більшою за 0.");
                    return;
                }

                double oldWeight = coffee.getTotalWeight() * coffee.getQuantity() / 1000.0;
                double newWeight = coffee.getTotalWeight() * newQuantity / 1000.0;
                double difference = newWeight - oldWeight;

                if (truck.getCurrentWeight() + difference <= truck.getMaxWeight()) {
                    truck.updateCoffeeQuantity(coffee, newQuantity);
                    coffee.setQuantity(newQuantity);
                    truck.setCurrentWeight(truck.getCurrentWeight() + difference);
                    truck.updatingPackedCoffeeFile(coffee, newQuantity);
                    showInfoAlert("Кількість успішно змінено.");
                } else {
                    showErrorAlert("Не вистачає місця у вантажівці для такої кількості кави.");
                }
            } catch (NumberFormatException e) {
                showErrorAlert("Будь ласка, введіть дійсне число.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void deleteCoffee() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Видалити каву");
        alert.setHeaderText(null);
        alert.setContentText("Ви впевнені?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            truck.deletePackedCoffee(coffee);
            showInfoAlert("Кава успішно видалена.");
            closeWindow();
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Помилка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfoAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Інформація");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
