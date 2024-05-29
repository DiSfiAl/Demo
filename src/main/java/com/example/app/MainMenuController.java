package com.example.app;

import com.Logs.MyLogger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.coffee.CoffeeMenu;
import com.example.trucks.TruckMenu;
import com.example.reports.ReportsMenu;
import com.example.settings.SettingsWindow;

import java.io.IOException;

public class MainMenuController {

    @FXML
    protected void openCoffeeMenu() {
        MyLogger.logInfo("Opening Coffee Menu");
        new CoffeeMenu().start(new Stage());
    }

    @FXML
    protected void openTruckMenu() throws IOException {
        MyLogger.logInfo("Opening Truck Menu");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/trucks/truck-menu.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    protected void openReportsMenu() {
        MyLogger.logInfo("Opening Reports Menu");
        new ReportsMenu().start(new Stage());
    }
}
