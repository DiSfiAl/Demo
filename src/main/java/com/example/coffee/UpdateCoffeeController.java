package com.example.coffee;

import com.Logs.MyLogger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UpdateCoffeeController {

    @FXML
    private TextField coffeeNameField;

    @FXML
    private TextField sortField;

    @FXML
    private TextField typeField;

    @FXML
    private TextField packageTypeField;

    @FXML
    protected void findCoffee() {
        String coffeeName = coffeeNameField.getText().trim();
        String sort = sortField.getText().trim();
        String type = typeField.getText().trim();
        String packageType = packageTypeField.getText().trim();

        if (coffeeName.isEmpty() || sort.isEmpty() || type.isEmpty() || packageType.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Помилка", "Заповніть всі поля");
            return;
        }

        StringBuilder coffeeData = new StringBuilder();
        boolean coffeeFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("coffee.txt"))) {
            String line;
            String currentRecord = "";
            boolean isRecord = false;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Coffee name:")) {
                    if (isRecord) {
                        if (currentRecord.contains("Coffee name: " + coffeeName)
                                && currentRecord.contains("Sort: " + sort)
                                && currentRecord.contains("Type: " + type)
                                && currentRecord.contains("Package type: " + packageType)) {
                            coffeeData.append(currentRecord);
                            coffeeFound = true;
                            break;
                        }
                        currentRecord = "";
                    }
                    isRecord = true;
                }
                if (isRecord) {
                    currentRecord += line + "\n";
                }
            }
            // Check the last record
            if (isRecord && currentRecord.contains("Coffee name: " + coffeeName)
                    && currentRecord.contains("Sort: " + sort)
                    && currentRecord.contains("Type: " + type)
                    && currentRecord.contains("Package type: " + packageType)) {
                coffeeData.append(currentRecord);
                coffeeFound = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (coffeeFound) {
            openUpdateForm(coffeeData.toString());
            MyLogger.logInfo("Coffee Found In DataBase");
        } else {
            showAlert(Alert.AlertType.ERROR, "Помилка", "Такої кави не існує в базі даних");
        }
    }

    private void openUpdateForm(String coffeeData) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("update-coffee-form.fxml"));
            Parent root = loader.load();
            UpdateCoffeeFormController controller = loader.getController();
            controller.setCoffeeData(coffeeData);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Update Coffee");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
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
