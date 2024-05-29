package com.example.trucks;

import com.example.coffee.Coffee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PackCoffeeController {

    @FXML
    private TextField coffeeNameField;
    @FXML
    private TextField coffeeQuantityField;
    @FXML
    private TextField sortField;
    @FXML
    private TextField typeField;
    @FXML
    private TextField packageTypeField;
    @FXML
    private Label statusLabel;

    private Truck truck;

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    @FXML
    void packCoffee(ActionEvent event) {
        String coffeeName = coffeeNameField.getText();
        String sort = sortField.getText();
        String type = typeField.getText();
        String packageType = packageTypeField.getText();
        String quantityStr = coffeeQuantityField.getText();

        if (coffeeName.isEmpty() || sort.isEmpty() || type.isEmpty() || packageType.isEmpty() || quantityStr.isEmpty()) {
            statusLabel.setText("All fields must be filled.");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            statusLabel.setText("Quantity must be an integer.");
            return;
        }

        if (!coffeeExists(coffeeName, sort, type, packageType)) {
            statusLabel.setText("Coffee with given details not found in Coffee.txt.");
            return;
        }

        Coffee coffee = new Coffee();
        coffee.setCoffeeName(coffeeName);
        coffee.setSort(sort);
        coffee.setType(type);
        coffee.setPackageType(packageType);

        try {
            truck.addCoffee(coffee, quantity);
            truck.updateTruckFile();
            statusLabel.setText("Coffee packed successfully.");
            truck.readFromFile();

            // Закриваємо вікно після пакування
            Stage stage = (Stage) coffeeNameField.getScene().getWindow();
            stage.close();
        } catch (IllegalArgumentException e) {
            statusLabel.setText(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean coffeeExists(String coffeeName, String sort, String type, String packageType) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Coffee.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Coffee name: " + coffeeName)) {
                    String currentSort = reader.readLine().split(": ")[1].trim();
                    String currentType = reader.readLine().split(": ")[1].trim();
                    String currentPackageType = reader.readLine().split(": ")[1].trim();
                    if (currentSort.equals(sort) && currentType.equals(type) && currentPackageType.equals(packageType)) {
                        return false;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
