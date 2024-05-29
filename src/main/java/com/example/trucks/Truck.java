package com.example.trucks;

import com.Logs.MyLogger;
import com.example.coffee.Coffee;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Truck {
    private String truckName;
    private double maxWeight;
    private double currentWeight;
    private List<Coffee> coffeeList;
    private boolean isCompleted;

    public Truck() {
        this.truckName = "";
        this.maxWeight = 0.00;
        this.currentWeight = 0.00;
        this.coffeeList = new ArrayList<>();
        this.isCompleted = false;
        try {
            String fileName = "trucks/packedCoffee/" + truckName + "PackedCoffee.txt";
            if(Files.exists(Path.of(fileName)))
                readFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Truck(String truckName, double maxWeight) {
        this.truckName = truckName;
        this.maxWeight = maxWeight;
        this.currentWeight = 0.00;
        this.coffeeList = new ArrayList<>();
        this.isCompleted = false;
        try {
            String fileName = "trucks/packedCoffee/" + truckName + "PackedCoffee.txt";
            if(Files.exists(Path.of(fileName))) {
                readFromFile();
            }
            else
                MyLogger.logInfo(fileName + "don't exist");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTruckName(String name) {
        this.truckName = name;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getTruckName() {
        return truckName;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public List<Coffee> getCoffeeList() { return coffeeList; }

    public boolean setCurrentWeight(double totalWeight) {
        if ((currentWeight + totalWeight) <= getMaxWeight()) {
            currentWeight += totalWeight;
            return true;
        }
        return false;
    }

    public void addCoffee(Coffee coffee, int quantity) {
        try {
            readFromFile(coffee);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        double weightInKg = (coffee.getTotalWeight() * quantity) / 1000.0; // перетворюємо грами в кілограми
        if ((currentWeight + weightInKg) <= maxWeight) {
            coffeeList.add(coffee);
            currentWeight += weightInKg;
            updatePackedCoffeeFile(coffee, quantity);
            updateTruckFile();
        } else {
            throw new IllegalArgumentException("Not enough space in the truck.");
        }
    }

    public void updateCoffeeQuantity(Coffee coffee, int newQuantity) throws IOException {
        double oldWeight = coffee.getTotalWeight() * coffee.getQuantity() / 1000.0;
        double newWeight = coffee.getTotalWeight() * newQuantity / 1000.0;
        currentWeight = currentWeight - oldWeight + newWeight;
        coffee.setQuantity(newQuantity);
        updatePackedCoffeeFile(coffee, newQuantity);
        updateTruckFile();
    }

    public void deletePackedCoffee(Coffee coffee) {
        // Remove the coffee object from the list
        coffeeList.remove(coffee);

        // Update the current weight of the truck
        currentWeight -= coffee.getTotalWeight() * coffee.getQuantity() / 1000.0;

        // Delete the coffee record from the file
        boolean coffeeFound = false;
        List<String> updatedCoffeeList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("trucks/packedCoffee/" + truckName + "PackedCoffee.txt"))) {
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
                    isMatch = line.substring(13).trim().equals(coffee.getCoffeeName());
                } else {
                    coffeeRecord.append(line).append("\n");
                    if (line.startsWith("Manufacturer:")) {
                        isMatch = isMatch && line.substring(14).trim().equals(coffee.getManufacturer());
                    } else if (line.startsWith("Sort:")) {
                        isMatch = isMatch && line.substring(6).trim().equals(coffee.getSort());
                    } else if (line.startsWith("Type:")) {
                        isMatch = isMatch && line.substring(6).trim().equals(coffee.getType());
                    } else if (line.startsWith("Package type:")) {
                        isMatch = isMatch && line.substring(14).trim().equals(coffee.getPackageType());
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

            // Перевірити останній запис
            if (coffeeRecord.length() > 0 && !isMatch) {
                updatedCoffeeList.add(coffeeRecord.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!coffeeFound) {
            MyLogger.logInfo("Такої кави не існує в базі даних");
        } else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("trucks/packedCoffee/" + truckName + "PackedCoffee.txt"))) {
                for (String record : updatedCoffeeList) {
                    writer.write(record);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Update the truck file after deleting the coffee record
        updateTruckFile();
    }


    void updateTruckFile() {
        String truckFileName = "trucks/" + truckName + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(truckFileName))) {
            writer.write("Truck Name: " + truckName);
            writer.newLine();
            writer.write("Max Weight: " + maxWeight);
            writer.newLine();
            writer.write("Current Weight: " + currentWeight);
            writer.newLine();
            writer.write("Status: " + isCompleted);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updatePackedCoffeeFile(Coffee coffee, int quantity) {
        String packedCoffeeFileName = "trucks/packedCoffee/" + truckName + "PackedCoffee.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(packedCoffeeFileName, true))) {
            writer.write( coffee.toWrite() + "\nQuantity: " + quantity);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updatingPackedCoffeeFile(Coffee coffee, int newQuantity) throws IOException {
        String packedCoffeeFileName = "trucks/packedCoffee/" + truckName + "PackedCoffee.txt";
        List<String> lines = Files.readAllLines(Path.of(packedCoffeeFileName));
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains("Name: " + coffee.getCoffeeName()) && lines.get(i + 2).contains("Sort: " + coffee.getSort())) {
                lines.set(i + 10, "Quantity: " + newQuantity);
                break;
            }
        }
        Files.write(Path.of(packedCoffeeFileName), lines);
    }

    public void readTruckInfoFromFile(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String truckNameLine = reader.readLine();
            String maxWeightLine = reader.readLine();
            String currentWeightLine = reader.readLine();
            String isCompleted = reader.readLine();

            if (truckNameLine == null || maxWeightLine == null || currentWeightLine == null) {
                throw new IOException("Truck file is missing required information.");
            }

            this.truckName = truckNameLine.split(": ")[1].trim();
            this.maxWeight = Double.parseDouble(maxWeightLine.split(": ")[1].trim());
            this.currentWeight = Double.parseDouble(currentWeightLine.split(": ")[1].trim());
            String status = isCompleted.split(": ")[1].trim();
            if(status.equals("false")) { this.isCompleted = false; }
            else if(status.equals("true")) { this.isCompleted = true; }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String getValueFromLine(String line, int start) {
        if (line.length() > start) {
            return line.substring(start);
        }
        return "";
    }

    protected double getDoubleValueFromLine(String line, int start, String delimiter) {
        int endIndex = line.indexOf(delimiter);
        if (endIndex != -1 && line.length() > start && endIndex > start) {
            return Double.parseDouble(line.substring(start, endIndex));
        }
        return 0.0;
    }

    protected double getDoubleValueFromLine(String line, int start) {
        return Double.parseDouble(line.substring(start));
    }
    protected int getIntValueFromLine(String line, int start, String delimiter) {
        int endIndex = line.indexOf(delimiter);
        if (endIndex != -1 && line.length() > start && endIndex > start) {
            return Integer.parseInt(line.substring(start, endIndex));
        }
        return 0;
    }

    protected int getIntValueFromLine(String line, int start) {
        return Integer.parseInt(line.substring(start));
    }

    public void readFromFile(Coffee coffeeToAdd) throws IOException {
        String fileName = "coffee.txt";
        List<String> lines = Files.readAllLines(Path.of(fileName));
        coffeeList.clear();
        int linesSize = lines.size();
        for (int i = 0; i < linesSize; i += 11) {
            if (i + 9 < linesSize) {
                Coffee coffee = new Coffee();
                coffee.setCoffeeName(getValueFromLine(lines.get(i), 13));
                coffee.setManufacturer(getValueFromLine(lines.get(i + 1), 14));
                coffee.setSort(getValueFromLine(lines.get(i + 2), 6));
                coffee.setType(getValueFromLine(lines.get(i + 3), 6));
                coffee.setTotalPrice(getDoubleValueFromLine(lines.get(i + 4), 7, " UAH"));
                coffee.setPackageType(getValueFromLine(lines.get(i + 5), 14));
                coffee.setTotalWeight(getDoubleValueFromLine(lines.get(i + 6), 8, " grams"));
                coffee.setCoffeeWeight(getDoubleValueFromLine(lines.get(i + 7), 15, " grams"));
                coffee.setPackageWeight(getDoubleValueFromLine(lines.get(i + 8), 16, " grams"));
                coffee.setTerm(getIntValueFromLine(lines.get(i + 9), 6, " month"));
                coffee.setGrade(getDoubleValueFromLine(lines.get(i + 10), 7));
                if(Objects.equals(coffeeToAdd.getCoffeeName(), coffee.getCoffeeName()) && Objects.equals(coffeeToAdd.getType(), coffee.getType()) && Objects.equals(coffeeToAdd.getSort(), coffee.getSort()) && Objects.equals(coffeeToAdd.getPackageType(), coffee.getPackageType())) {
                    coffeeToAdd.setManufacturer(coffee.getManufacturer());
                    coffeeToAdd.setTotalPrice(coffee.getTotalPrice());
                    coffeeToAdd.setTotalWeight(coffee.getTotalWeight());
                    coffeeToAdd.setCoffeeWeight(coffee.getTotalWeight());
                    coffeeToAdd.setPackageWeight(coffee.getPackageWeight());
                    coffeeToAdd.setTerm(coffee.getTerm());
                    coffeeToAdd.setGrade(coffee.getGrade());
                }
            }
        }
    }

    public boolean isCoffeePacked() {
        String fileName = "trucks/packedCoffee/" + truckName + "PackedCoffee.txt";
        if(Files.exists(Path.of(fileName)))
            return true;
        else
            return false;
    }

    public void readFromFile() throws IOException {
        String fileName = "trucks/packedCoffee/" + truckName + "PackedCoffee.txt";
        List<String> lines = Files.readAllLines(Path.of(fileName));
        coffeeList.clear();
        int linesSize = lines.size();
        for (int i = 0; i < linesSize; i += 12) {
            if (i + 11 < linesSize) {
                Coffee coffee = new Coffee();
                coffee.setCoffeeName(getValueFromLine(lines.get(i), 13));
                coffee.setManufacturer(getValueFromLine(lines.get(i + 1), 14));
                coffee.setSort(getValueFromLine(lines.get(i + 2), 6));
                coffee.setType(getValueFromLine(lines.get(i + 3), 6));
                coffee.setTotalPrice(getDoubleValueFromLine(lines.get(i + 4), 7, " UAH"));
                coffee.setPackageType(getValueFromLine(lines.get(i + 5), 14));
                coffee.setTotalWeight(getDoubleValueFromLine(lines.get(i + 6), 8, " grams"));
                coffee.setCoffeeWeight(getDoubleValueFromLine(lines.get(i + 7), 15, " grams"));
                coffee.setPackageWeight(getDoubleValueFromLine(lines.get(i + 8), 16, " grams"));
                coffee.setTerm(getIntValueFromLine(lines.get(i + 9), 6, " month"));
                coffee.setGrade(getDoubleValueFromLine(lines.get(i + 10), 7));
                coffee.setQuantity(getIntValueFromLine(lines.get(i + 11), 10));
                coffeeList.add(coffee);
            }
        }
    }
}
