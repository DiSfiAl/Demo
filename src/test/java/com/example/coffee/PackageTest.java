package com.example.coffee;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PackageTest {

    @Test
    void testSetPackageWeightSingleParam() {
        Package coffeePackage = new Package();
        coffeePackage.setPackageWeight(100.0);
        assertEquals(100.0, coffeePackage.getPackageWeight());
    }

    @Test
    void testSetPackageWeightTwoParams() {
        Package coffeePackage = new Package();
        coffeePackage.setPackageWeight(100.0, 50.0);
        assertEquals(50.0, coffeePackage.getPackageWeight());
    }

    @Test
    void testSetPackagePrice() {
        Package coffeePackage = new Package();
        coffeePackage.setPackageWeight(100.0);
        coffeePackage.setPackagePrice(5.0);
        assertEquals(500.0, coffeePackage.getPackagePrice());
    }

    @Test
    void testGetPackageWeight() {
        Package coffeePackage = new Package();
        coffeePackage.setPackageWeight(200.0);
        assertEquals(200.0, coffeePackage.getPackageWeight());
    }

    @Test
    void testGetPackagePrice() {
        Package coffeePackage = new Package();
        coffeePackage.setPackageWeight(50.0);
        coffeePackage.setPackagePrice(10.0);
        assertEquals(500.0, coffeePackage.getPackagePrice());
    }
}
