package com.example.trucks;

import com.Logs.MyLogger;
import com.example.coffee.Coffee;
import com.example.coffee.CoffeeDetailsController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class CoffeeListController {

    @FXML
    private ListView<Coffee> coffeeListView;

    @FXML
    private Label coffeeDetailsLabel;

    private Truck truck;

    public void setTruck(Truck truck) {
        this.truck = truck;
        coffeeListView.setItems(FXCollections.observableArrayList(truck.getCoffeeList()));
    }

    @FXML
    private void handleCoffeeClick() throws IOException {
        Coffee selectedCoffee = coffeeListView.getSelectionModel().getSelectedItem();
        if (selectedCoffee != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/trucks/packed-coffee-details.fxml"));
            Parent root = loader.load();

            PackedCoffeeDetailsController controller = loader.getController();
            controller.setCoffee(selectedCoffee, truck);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            MyLogger.logInfo("No Coffee selected");
        }
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) coffeeListView.getScene().getWindow();
        stage.close();
    }
}
