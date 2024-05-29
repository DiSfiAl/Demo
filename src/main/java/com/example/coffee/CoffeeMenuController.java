package com.example.coffee;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CoffeeMenuController {

    @FXML
    protected void addCoffee() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/coffee/add-coffee.fxml"));
            Parent root = fxmlLoader.load();
            AddCoffeeController controller = fxmlLoader.getController();
            Stage stage = new Stage();
            stage.setTitle("Add Coffee");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void viewCoffeeList() {
        openNewWindow("/com/example/coffee/view-coffee-list.fxml", "View Coffee List");
    }

    @FXML
    protected void updateCoffee() {
        openNewWindow("/com/example/coffee/update-coffee.fxml", "Update Coffee Information");
    }

    @FXML
    protected void deleteCoffee() {
        openNewWindow("/com/example/coffee/delete-coffee.fxml", "Delete Coffee");
    }

    private void openNewWindow(String fxmlPath, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(fxmlLoader.load(), 300, 200);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
