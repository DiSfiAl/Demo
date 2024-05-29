package com.example.coffee;

import com.Logs.MyLogger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeleteCoffeeController {

    @FXML
    private TextField coffeeNameField;

    @FXML
    private TextField sortField;

    @FXML
    private TextField typeField;

    @FXML
    private TextField packageTypeField;

    @FXML
    private Label statusLabel;

    @FXML
    protected void deleteCoffee() {
        String coffeeName = coffeeNameField.getText().trim();
        String sort = sortField.getText().trim();
        String type = typeField.getText().trim();
        String packageType = packageTypeField.getText().trim();

        if (coffeeName.isEmpty() || sort.isEmpty() || type.isEmpty() || packageType.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Помилка", "Заповніть всі поля");
            return;
        }

        boolean coffeeFound = false;
        List<String> updatedCoffeeList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("coffee.txt"))) {
            String line;
            StringBuilder coffeeRecord = new StringBuilder();
            boolean isMatch = false;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Coffee name:")) {
                    if (coffeeRecord.length() > 0 && !isMatch) {
                        updatedCoffeeList.add(coffeeRecord.toString());
                    }
                    coffeeRecord.setLength(0);
                    coffeeRecord.append(line).append("\n");
                    isMatch = line.substring(13).trim().equals(coffeeName);
                } else {
                    coffeeRecord.append(line).append("\n");
                    if (line.startsWith("Sort:")) {
                        isMatch = isMatch && line.substring(6).trim().equals(sort);
                    } else if (line.startsWith("Type:")) {
                        isMatch = isMatch && line.substring(6).trim().equals(type);
                    } else if (line.startsWith("Package type:")) {
                        isMatch = isMatch && line.substring(14).trim().equals(packageType);
                    }
                }

                // Якщо знайдений збіг, не додавати цей запис до оновленого списку
                if (isMatch) {
                    coffeeFound = true;
                } else if (line.isEmpty() && coffeeRecord.length() > 0) {
                    updatedCoffeeList.add(coffeeRecord.toString());
                    coffeeRecord.setLength(0);
                }
            }

            if (coffeeRecord.length() > 0 && !isMatch) {
                updatedCoffeeList.add(coffeeRecord.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!coffeeFound) {
            showAlert(Alert.AlertType.ERROR, "Помилка", "Такої кави не існує в базі даних");
        } else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("coffee.txt"))) {
                for (String record : updatedCoffeeList) {
                    MyLogger.logInfo("Coffee Deleted");
                    writer.write(record);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            showAlert(Alert.AlertType.INFORMATION, "Успіх", "Каву " + coffeeName + " успішно видалено");
        }
    }



    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
