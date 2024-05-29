package com.example.coffee;

import com.Logs.MyLogger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AddCoffeeController {

    @FXML
    private TextField coffeeName;
    @FXML
    private TextField manufacturer;
    @FXML
    private TextField sort;
    @FXML
    private TextField type;
    @FXML
    private TextField priceFor100G;
    @FXML
    private TextField coffeeWeight;
    @FXML
    private TextField packageType;
    @FXML
    private TextField term;
    @FXML
    private TextField aroma;
    @FXML
    private TextField flavor;
    @FXML
    private TextField density;
    @FXML
    private TextField acidity;

    private static final String[] VALID_SORTS = {"Arabica", "Robusta", "Sublimated"};
    private static final String[] VALID_TYPES = {"Grains", "Soluble", "Powdery"};
    private static final String[] VALID_PACKAGE_TYPES = {"Can", "Jar", "Pack"};
    private static final int[] VALID_GRADE_VALUES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    @FXML
    protected void handleAddCoffee() {
        if (!validateFields()) {
            return;
        }

        Coffee coffee = new Coffee();
        coffee.setCoffeeName(coffeeName.getText());
        coffee.setManufacturer(manufacturer.getText());
        coffee.setSort(sort.getText());
        coffee.setType(type.getText());
        coffee.setPriceFor100G(Double.parseDouble(priceFor100G.getText()));
        coffee.setCoffeeWeight(Double.parseDouble(coffeeWeight.getText()));
        coffee.setPackageType(packageType.getText());
        coffee.setTerm(Integer.parseInt(term.getText()));

        coffee.calcTotalWeight();
        coffee.calcTotalPrice();
        coffee.calcGrade(Integer.parseInt(aroma.getText()), Integer.parseInt(flavor.getText()), Integer.parseInt(density.getText()), Integer.parseInt(acidity.getText()));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("coffee.txt", true))) {
            writer.write(coffee.toWrite());
            MyLogger.logInfo("Coffee Added");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Close the window
        Stage stage = (Stage) coffeeName.getScene().getWindow();
        stage.close();
    }

    private boolean validateFields() {
        String name = coffeeName.getText();
        String manuf = manufacturer.getText();
        String sortVal = sort.getText();
        String typeVal = type.getText();
        String price = priceFor100G.getText();
        String weight = coffeeWeight.getText();
        String pkgType = packageType.getText();
        String termVal = term.getText();
        String aromaVal = aroma.getText();
        String flavorVal = flavor.getText();
        String densityVal = density.getText();
        String acidityVal = acidity.getText();

        if (name.isEmpty() || manuf.isEmpty() || sortVal.isEmpty() || typeVal.isEmpty() || price.isEmpty() || weight.isEmpty() || pkgType.isEmpty() || termVal.isEmpty() || aromaVal.isEmpty() || flavorVal.isEmpty() || densityVal.isEmpty() || acidityVal.isEmpty()) {
            showAlert("All fields must be filled out.");
            return false;
        }

        if (!isValidSort(sortVal)) {
            showAlert("Sort must be one of the following: Arabica, Robusta, Sublimated.");
            return false;
        }

        if (!isValidType(typeVal)) {
            showAlert("Type must be one of the following: Grains, Soluble, Powdery.");
            return false;
        }

        if (!isNonNegativeDouble(price) || !isNonNegativeDouble(weight)) {
            showAlert("Price and Weight must be non-negative numbers.");
            return false;
        }

        if (!isNonNegativeInteger(termVal)) {
            showAlert("Term must be a non-negative integer.");
            return false;
        }

        if (!isValidPackageType(pkgType)) {
            showAlert("Package type must be one of the following: Can, Jar, Pack.");
            return false;
        }

        if (!isValidGradeValue(aromaVal) || !isValidGradeValue(flavorVal) || !isValidGradeValue(densityVal) || !isValidGradeValue(acidityVal)) {
            showAlert("Aroma, Flavor, Density, and Acidity must be integers between 1 and 10.");
            return false;
        }

        return true;
    }

    private boolean isValidSort(String sort) {
        for (String s : VALID_SORTS) {
            if (s.equalsIgnoreCase(sort)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidType(String type) {
        for (String t : VALID_TYPES) {
            if (t.equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidPackageType(String pkgType) {
        for (String pt : VALID_PACKAGE_TYPES) {
            if (pt.equalsIgnoreCase(pkgType)) {
                return true;
            }
        }
        return false;
    }

    private boolean isNonNegativeDouble(String value) {
        try {
            return Double.parseDouble(value) >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isNonNegativeInteger(String value) {
        try {
            int intValue = Integer.parseInt(value);
            return intValue >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidGradeValue(String value) {
        try {
            int intValue = Integer.parseInt(value);
            for (int validValue : VALID_GRADE_VALUES) {
                if (validValue == intValue) {
                    return true;
                }
            }
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
