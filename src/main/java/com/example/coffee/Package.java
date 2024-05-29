package com.example.coffee;

public class Package {
    private double packageWeight;
    private double packagePrice;
    public Package() {
        this.packageWeight = 0.00;
    }
    public void setPackageWeight(double packageWeight) { this.packageWeight = packageWeight; }
    public void setPackageWeight(double oneHundredPackWeight, double coffeeWeight) { this.packageWeight = coffeeWeight / 100 * oneHundredPackWeight; }
    public void setPackagePrice(double oneGramPrice) { this.packagePrice = getPackageWeight() * oneGramPrice; }
    public double getPackageWeight() { return packageWeight; }
    public double getPackagePrice() { return packagePrice; }
}