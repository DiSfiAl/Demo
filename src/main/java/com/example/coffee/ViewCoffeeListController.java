package com.example.coffee;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewCoffeeListController {

    @FXML
    private ListView<String> coffeeListView;

    private ObservableList<String> coffeeNames;

    @FXML
    public void initialize() {
        coffeeNames = FXCollections.observableArrayList(loadCoffeeNames());
        coffeeListView.setItems(coffeeNames);

        coffeeListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String selectedCoffeeName = coffeeListView.getSelectionModel().getSelectedItem();
                if (selectedCoffeeName != null) {
                    showCoffeeDetails(selectedCoffeeName);
                }
            }
        });
    }

    private List<String> loadCoffeeNames() {
        List<String> coffeeNames = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("coffee.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Coffee name:")) {
                    coffeeNames.add(line.substring(12).trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return coffeeNames;
    }

    private void showCoffeeDetails(String coffeeName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/coffee/coffee-details.fxml"));
            Parent root = loader.load();
            CoffeeDetailsController controller = loader.getController();
            controller.setCoffeeName(coffeeName);

            Stage stage = new Stage();
            stage.setTitle("Coffee Details");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
