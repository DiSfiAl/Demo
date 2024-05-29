package com.example.coffee;

import com.example.coffee.Package;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Scanner;

public class Coffee extends Package {
    private String coffeeName;
    private String packageType;
    private String manufacturer;
    private String sort;
    private String type;
    private double coffeeWeight;
    private double totalWeight;
    private double totalPrice;
    private double priceFor100G;
    private double coffeePrice;
    private double grade;
    private int term;
    private int quantity;
    private static final int DECIMAL_PLACES = 2;

    public Coffee() {
        this.coffeeName = "";
        this.packageType = "";
        this.manufacturer = "";
        this.totalPrice = 0.00;
        this.sort = "";
        this.type = "";
        this.coffeeWeight = 0.00;
        this.totalWeight = 0.00;
        this.term = 0;
        this.quantity = 0;
    }
    public double formatDouble(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(DECIMAL_PLACES, RoundingMode.DOWN);
        return bd.doubleValue();
    }
    public void setCoffeeName(String coffeeName) { this.coffeeName = coffeeName; }
    public void setPackageType(String packageType) { this.packageType = packageType; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    public void setSort(String sort) { this.sort = sort; }
    public void setType(String type) { this.type = type; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public void setPriceFor100G(double priceFor100G) { this.priceFor100G = priceFor100G; }
    public void setCoffeeWeight(double coffeeWeight) { this.coffeeWeight = coffeeWeight; }
    public void setTotalWeight(double totalWeight) { this.totalWeight = totalWeight; }
    public void setGrade(double grade) { this.grade = grade; }
    public void setTerm(int term) {
        this.term = Math.max(term, 0);
    }
    public void setTotalWeight() { totalWeight = coffeeWeight + getPackageWeight();  }
    protected String getValueFromLine(String line, int start) {
        if (line.length() > start) {
            return line.substring(start);
        }
        return "";
    }
    public String getCoffeeName() { return coffeeName; }
    public String getPackageType() { return packageType; }
    public String getSort() { return sort; }
    public String getType() { return type; }
    public String getManufacturer() { return manufacturer; }
    public double getCoffeeWeight() { return coffeeWeight; }
    public double getTotalWeight() { return totalWeight; }
    public double getPriceFor100G() { return priceFor100G; }
    public double getCoffeePrice() { return coffeePrice; }
    public double getTotalPrice() { return totalPrice; }
    public double getGrade() { return grade; }
    public int getTerm() { return term; }
    public void calcTotalWeight() {
        double oneHundredPackWeight = 0;
        double oneGramPrice = 0;
        boolean correctType = false;
        if(Objects.equals(packageType, "Can")) { oneHundredPackWeight = 30; oneGramPrice = 1.5; correctType = true; }
        else if(Objects.equals(packageType, "Jar")) { oneHundredPackWeight = 24; oneGramPrice = 0.8; correctType = true; }
        else if(Objects.equals(packageType, "Pack")) { oneHundredPackWeight = 4; oneGramPrice = 0.05; correctType = true; }
        if(correctType) {
            setPackageWeight(oneHundredPackWeight, getCoffeeWeight());
            setPackagePrice(oneGramPrice);
            setTotalWeight();
        }
        else {
            setPackageWeight(0, getCoffeeWeight());
            setPackagePrice(0);
            setTotalWeight();
        }
    }
    public void calcGrade(int aroma, int flavor, int density, int acidity) {
        this.grade = (double) (aroma + flavor + density + acidity) / 4;
    }

    public void calcTotalPrice() {
        coffeePrice = getPriceFor100G() / 100 * getCoffeeWeight();
        totalPrice = getCoffeePrice() + getPackagePrice();
    }
    public void setInfo(Coffee obj) {
        this.coffeeName = obj.coffeeName;
        this.manufacturer = obj.manufacturer;
        this.sort = obj.sort;
        this.type = obj.type;
        this.totalPrice = obj.totalPrice;
        this.packageType = obj.packageType;
        this.totalWeight = obj.totalWeight;
        this.coffeeWeight = obj.coffeeWeight;
        this.setPackageWeight(obj.getPackageWeight());
        this.term = obj.term;
        this.grade = obj.grade;
    }
    public String toWrite() {
        return "Coffee name: " + getCoffeeName() + "\n" +
                "Manufacturer: " + getManufacturer() + "\n" +
                "Sort: " + getSort() + "\n" +
                "Type: " + getType() + "\n" +
                "Price: " + formatDouble(getTotalPrice()) + " UAH\n" +
                "Package type: " + getPackageType() + "\n" +
                "Weight: " + formatDouble(getTotalWeight()) + " grams\n" +
                "Coffee Weight: " + formatDouble(getCoffeeWeight()) + " grams\n" +
                "Package Weight: " + formatDouble(getPackageWeight()) + " grams\n" +
                "Term: " + getTerm() + " month\n" +
                "Grade: " + getGrade();
    }

    public String toWritePacked() {
        return "Coffee name: " + getCoffeeName() + "\n" +
                "Manufacturer: " + getManufacturer() + "\n" +
                "Sort: " + getSort() + "\n" +
                "Type: " + getType() + "\n" +
                "Price: " + formatDouble(getTotalPrice()) + " UAH\n" +
                "Package type: " + getPackageType() + "\n" +
                "Weight: " + formatDouble(getTotalWeight()) + " grams\n" +
                "Coffee Weight: " + formatDouble(getCoffeeWeight()) + " grams\n" +
                "Package Weight: " + formatDouble(getPackageWeight()) + " grams\n" +
                "Term: " + getTerm() + " month\n" +
                "Grade: " + getGrade() + "\n" +
                "Quantity: " + getQuantity();
    }

    @Override
    public String toString() {
        return coffeeName; // Ви можете змінити на будь-яке інше поле, яке хочете відображати
    }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getQuantity() { return quantity; }
}
