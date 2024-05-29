package com.example.trucks;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TruckMenuController {

    @FXML
    private ListView<String> truckListView;

    @FXML
    private Button addTruckButton;

    private List<Truck> trucks;

    @FXML
    void initialize() {
        trucks = new ArrayList<>();
        loadTrucksFromFiles();
    }

    @FXML
    void addTruck(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/trucks/add-truck.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void truckSelected() throws IOException {
        String selectedTruckName = truckListView.getSelectionModel().getSelectedItem();
        if (selectedTruckName != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/trucks/truck-details.fxml"));
            Parent root = loader.load();
            TruckDetailsController controller = loader.getController();
            controller.setTruck(getTruckByName(selectedTruckName));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    private void loadTrucksFromFiles() {
        File folder = new File("trucks");
        if (!folder.exists()) {
            folder.mkdir();
        }

        File[] truckFiles = folder.listFiles((dir, name) -> name.endsWith(".txt"));
        if (truckFiles != null) {
            for (File file : truckFiles) {
                try {
                    Truck truck = new Truck();
                    truck.readTruckInfoFromFile(file.getPath());
                    if(truck.isCoffeePacked())
                        truck.readFromFile();
                    trucks.add(truck);
                    truckListView.getItems().add(truck.getTruckName());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Truck getTruckByName(String name) {
        return trucks.stream()
                .filter(truck -> truck.getTruckName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
