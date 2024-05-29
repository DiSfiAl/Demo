package com.example.coffee;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CoffeeDetailsController {

    @FXML
    private Label coffeeDetailsLabel;

    private String coffeeName;

    public void setCoffeeName(String coffeeName) {
        this.coffeeName = coffeeName;
        loadCoffeeDetails();
    }

    private void loadCoffeeDetails() {
        StringBuilder details = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("coffee.txt"))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Coffee name:")) {
                    if (line.substring(12).trim().equals(coffeeName)) {
                        found = true;
                        details.append(line).append("\n");
                    } else if (found) {
                        break;
                    }
                } else if (found) {
                    details.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        coffeeDetailsLabel.setText(details.toString());
    }
}
