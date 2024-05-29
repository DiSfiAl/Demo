package com.example.reports;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ReportsMenu extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        Button coffeeInventoryButton = new Button("Generate Coffee Inventory Report");
        Button truckInventoryButton = new Button("Generate Truck Inventory Report");
        Button coffeeSalesButton = new Button("Generate Coffee Sales Report");
        Button truckStatusButton = new Button("Generate Truck Status Report");

        coffeeInventoryButton.setOnAction(e -> generateAndDisplayReport(this::getCoffeeInventory, "Coffee Inventory Report"));
        truckInventoryButton.setOnAction(e -> generateAndDisplayReport(this::getTruckInventory, "Truck Inventory Report"));
        coffeeSalesButton.setOnAction(e -> generateAndDisplayReport(this::getCoffeeSales, "Coffee Sales Report"));
        truckStatusButton.setOnAction(e -> generateAndDisplayReport(this::getTruckStatus, "Truck Status Report"));

        root.getChildren().addAll(coffeeInventoryButton, truckInventoryButton, coffeeSalesButton, truckStatusButton);
        Scene scene = new Scene(root, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Reports Menu");
        primaryStage.show();
    }

    private void generateAndDisplayReport(ReportGenerator generator, String reportTitle) {
        try {
            String report = generator.generate();
            saveReportToFile(report, reportTitle);
            showReportWindow(report, reportTitle);
            showAlert("Report Generated", reportTitle + " has been generated and saved successfully.");
        } catch (IOException ex) {
            showAlert("Error", "An error occurred while generating the " + reportTitle.toLowerCase() + ".");
            ex.printStackTrace();
        }
    }

    @FunctionalInterface
    interface ReportGenerator {
        String generate() throws IOException;
    }

    private String getCoffeeInventory() throws IOException {
        Path coffeePath = Paths.get("coffee.txt");
        StringBuilder result = new StringBuilder("Coffee Inventory:\n");

        try (BufferedReader reader = Files.newBufferedReader(coffeePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        }

        return result.toString();
    }

    private String getTruckInventory() throws IOException {
        Path trucksDir = Paths.get("trucks");
        StringBuilder result = new StringBuilder("Truck Inventory:\n");

        Files.list(trucksDir)
                .filter(path -> !path.getFileName().toString().equals("coffeePacked"))
                .forEach(path -> result.append(path.getFileName().toString()).append("\n"));

        return result.toString();
    }

    private String getCoffeeSales() throws IOException {
        Path coffeePackedDir = Paths.get("trucks", "coffeePacked");
        Map<String, Integer> coffeeSales = new HashMap<>();

        if (Files.exists(coffeePackedDir)) {
            Files.list(coffeePackedDir).forEach(path -> {
                try (BufferedReader reader = Files.newBufferedReader(path)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        coffeeSales.put(line, coffeeSales.getOrDefault(line, 0) + 1);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else {
            return "Coffee Sales: Directory trucks/coffeePacked does not exist.\n";
        }

        StringBuilder result = new StringBuilder("Coffee Sales:\n");
        for (Map.Entry<String, Integer> entry : coffeeSales.entrySet()) {
            result.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return result.toString();
    }

    private String getTruckStatus() throws IOException {
        Path trucksDir = Paths.get("trucks");
        long sentCount = 0;
        long pendingCount = 0;

        if (Files.exists(trucksDir)) {
            sentCount = Files.list(trucksDir)
                    .filter(path -> path.getFileName().toString().endsWith("_sent.txt"))
                    .count();
            pendingCount = Files.list(trucksDir)
                    .filter(path -> path.getFileName().toString().endsWith("_pending.txt"))
                    .count();
        } else {
            return "Truck Status: Directory trucks does not exist.\n";
        }

        return "Truck Status:\nTrucks Sent: " + sentCount + "\nTrucks Pending: " + pendingCount + "\n";
    }

    private void saveReportToFile(String report, String reportTitle) throws IOException {
        LocalDate date = LocalDate.now();
        Path reportDir = Paths.get("reports");
        if (!Files.exists(reportDir)) {
            Files.createDirectory(reportDir);
        }
        String fileName = reportTitle.replace(" ", "_").toLowerCase() + "_" + date + ".txt";
        Path reportFile = reportDir.resolve(fileName);
        try (FileWriter writer = new FileWriter(reportFile.toFile())) {
            writer.write(report);
        }
    }

    private void showReportWindow(String report, String reportTitle) {
        Stage reportStage = new Stage();
        TextArea reportArea = new TextArea(report);
        VBox reportRoot = new VBox(reportArea);
        Scene reportScene = new Scene(reportRoot, 600, 400);
        reportStage.setScene(reportScene);
        reportStage.setTitle(reportTitle);
        reportStage.show();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
