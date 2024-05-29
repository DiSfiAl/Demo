package com.example.coffee;

import com.Logs.MyLogger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpdateCoffeeFormController {

    @FXML
    private TextField coffeeNameField;

    @FXML
    private TextField manufacturerField;

    @FXML
    private TextField sortField;

    @FXML
    private TextField typeField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField packageTypeField;

    @FXML
    private TextField weightField;

    @FXML
    private TextField coffeeWeightField;

    @FXML
    private TextField packageWeightField;

    @FXML
    private TextField termField;

    @FXML
    private TextField gradeField;

    private String oldCoffeeData;

    public void setCoffeeData(String coffeeData) {
        oldCoffeeData = coffeeData;
        String[] lines = coffeeData.split("\n");
        if (lines.length >= 11) {
            coffeeNameField.setText(lines[0].substring(13).trim());
            manufacturerField.setText(lines[1].substring(14).trim());
            sortField.setText(lines[2].substring(6).trim());
            typeField.setText(lines[3].substring(6).trim());
            priceField.setText(lines[4].substring(7).trim());
            packageTypeField.setText(lines[5].substring(14).trim());
            weightField.setText(lines[6].substring(8).trim());
            coffeeWeightField.setText(lines[7].substring(14).trim());
            packageWeightField.setText(lines[8].substring(16).trim());
            termField.setText(lines[9].substring(6).trim());
            gradeField.setText(lines[10].substring(7).trim());
        } else {
            showAlert(Alert.AlertType.ERROR, "Помилка", "Неправильний формат даних кави");
        }
    }

    @FXML
    protected void updateCoffee() {
        String coffeeName = coffeeNameField.getText().trim();
        String manufacturer = manufacturerField.getText().trim();
        String sort = sortField.getText().trim();
        String type = typeField.getText().trim();
        String price = priceField.getText().trim();
        String packageType = packageTypeField.getText().trim();
        String weight = weightField.getText().trim();
        String coffeeWeight = coffeeWeightField.getText().trim();
        String packageWeight = packageWeightField.getText().trim();
        String term = termField.getText().trim();
        String grade = gradeField.getText().trim();

        if (coffeeName.isEmpty() || manufacturer.isEmpty() || sort.isEmpty() || type.isEmpty() ||
                price.isEmpty() || packageType.isEmpty() || weight.isEmpty() || coffeeWeight.isEmpty() ||
                packageWeight.isEmpty() || term.isEmpty() || grade.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Помилка", "Заповніть всі поля");
            return;
        }

        List<String> updatedCoffeeList = new ArrayList<>();
        boolean coffeeFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("coffee.txt"))) {
            String line;
            StringBuilder coffeeRecord = new StringBuilder();
            boolean isMatch = false;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Coffee name:")) {
                    if (coffeeRecord.length() > 0) {
                        if (isMatch) {
                            coffeeFound = true;
                            addUpdatedCoffeeRecord(updatedCoffeeList, coffeeName, manufacturer, sort, type, price,
                                    packageType, weight, coffeeWeight, packageWeight, term, grade);
                        } else {
                            updatedCoffeeList.add(coffeeRecord.toString());
                        }
                        coffeeRecord.setLength(0);
                    }
                    coffeeRecord.append(line).append("\n");
                    isMatch = line.equals("Coffee name: " + coffeeNameField.getText().trim());
                } else {
                    coffeeRecord.append(line).append("\n");
                    if (line.startsWith("Manufacturer:")) {
                        isMatch = isMatch && line.equals("Manufacturer: " + manufacturerField.getText().trim());
                    } else if (line.startsWith("Sort:")) {
                        isMatch = isMatch && line.equals("Sort: " + sortField.getText().trim());
                    } else if (line.startsWith("Type:")) {
                        isMatch = isMatch && line.equals("Type: " + typeField.getText().trim());
                    } else if (line.startsWith("Package type:")) {
                        isMatch = isMatch && line.equals("Package type: " + packageTypeField.getText().trim());
                    }
                }
            }
            // Check the last record
            if (coffeeRecord.length() > 0) {
                if (isMatch) {
                    coffeeFound = true;
                    addUpdatedCoffeeRecord(updatedCoffeeList, coffeeName, manufacturer, sort, type, price,
                            packageType, weight, coffeeWeight, packageWeight, term, grade);
                } else {
                    updatedCoffeeList.add(coffeeRecord.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (coffeeFound) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("coffee.txt"))) {
                for (String record : updatedCoffeeList) {
                    writer.write(record);
                    writer.newLine();
                }
                MyLogger.logInfo("Coffee Info Updated");
                showAlert(Alert.AlertType.INFORMATION, "Успіх", "Каву " + coffeeName + " успішно оновлено");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Помилка", "Такої кави не існує в базі даних");
        }
    }

    private void addUpdatedCoffeeRecord(List<String> updatedCoffeeList, String coffeeName, String manufacturer, String sort,
                                        String type, String price, String packageType, String weight, String coffeeWeight,
                                        String packageWeight, String term, String grade) {
        updatedCoffeeList.add("Coffee name: " + coffeeName);
        updatedCoffeeList.add("Manufacturer: " + manufacturer);
        updatedCoffeeList.add("Sort: " + sort);
        updatedCoffeeList.add("Type: " + type);
        updatedCoffeeList.add("Price: " + price);
        updatedCoffeeList.add("Package type: " + packageType);
        updatedCoffeeList.add("Weight: " + weight);
        updatedCoffeeList.add("Coffee Weight: " + coffeeWeight);
        updatedCoffeeList.add("Package Weight: " + packageWeight);
        updatedCoffeeList.add("Term: " + term);
        updatedCoffeeList.add("Grade: " + grade);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
