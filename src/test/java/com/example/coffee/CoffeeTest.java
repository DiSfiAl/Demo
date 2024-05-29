package com.example.coffee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoffeeTest {

    private Coffee coffee;

    @BeforeEach
    void setUp() {
        coffee = new Coffee();
    }

    @Test
    void formatDouble() {
        assertEquals(12.34, coffee.formatDouble(12.3456));
        assertEquals(12.30, coffee.formatDouble(12.3));
    }

    @Test
    void setCoffeeName() {
        coffee.setCoffeeName("Arabica");
        assertEquals("Arabica", coffee.getCoffeeName());
    }

    @Test
    void setPackageType() {
        coffee.setPackageType("Jar");
        assertEquals("Jar", coffee.getPackageType());
    }

    @Test
    void setManufacturer() {
        coffee.setManufacturer("Coffee Co.");
        assertEquals("Coffee Co.", coffee.getManufacturer());
    }

    @Test
    void setSort() {
        coffee.setSort("Premium");
        assertEquals("Premium", coffee.getSort());
    }

    @Test
    void setType() {
        coffee.setType("Ground");
        assertEquals("Ground", coffee.getType());
    }

    @Test
    void setTotalPrice() {
        coffee.setTotalPrice(100.50);
        assertEquals(100.50, coffee.getTotalPrice());
    }

    @Test
    void setPriceFor100G() {
        coffee.setPriceFor100G(20.75);
        assertEquals(20.75, coffee.getPriceFor100G());
    }

    @Test
    void setCoffeeWeight() {
        coffee.setCoffeeWeight(250);
        assertEquals(250, coffee.getCoffeeWeight());
    }

    @Test
    void setTotalWeight() {
        coffee.setCoffeeWeight(250);
        coffee.setPackageWeight(20);
        coffee.setTotalWeight();
        assertEquals(270, coffee.getTotalWeight());
    }

    @Test
    void setGrade() {
        coffee.setGrade(4.5);
        assertEquals(4.5, coffee.getGrade());
    }

    @Test
    void setTerm() {
        coffee.setTerm(12);
        assertEquals(12, coffee.getTerm());

        coffee.setTerm(-5);
        assertEquals(0, coffee.getTerm());
    }

    @Test
    void testSetTotalWeight() {
        coffee.setCoffeeWeight(250);
        coffee.setPackageWeight(20);
        coffee.setTotalWeight();
        assertEquals(270, coffee.getTotalWeight());
    }

    @Test
    void getValueFromLine() {
        assertEquals("example line", coffee.getValueFromLine("example line", 0));
        assertEquals("line", coffee.getValueFromLine("example line", 8));
        assertEquals("", coffee.getValueFromLine("example", 20));
    }

    @Test
    void getCoffeeName() {
        coffee.setCoffeeName("Arabica");
        assertEquals("Arabica", coffee.getCoffeeName());
    }

    @Test
    void getPackageType() {
        coffee.setPackageType("Jar");
        assertEquals("Jar", coffee.getPackageType());
    }

    @Test
    void getSort() {
        coffee.setSort("Premium");
        assertEquals("Premium", coffee.getSort());
    }

    @Test
    void getType() {
        coffee.setType("Ground");
        assertEquals("Ground", coffee.getType());
    }

    @Test
    void getManufacturer() {
        coffee.setManufacturer("Coffee Co.");
        assertEquals("Coffee Co.", coffee.getManufacturer());
    }

    @Test
    void getCoffeeWeight() {
        coffee.setCoffeeWeight(250);
        assertEquals(250, coffee.getCoffeeWeight());
    }

    @Test
    void getTotalWeight() {
        coffee.setCoffeeWeight(250);
        coffee.setPackageWeight(20);
        coffee.setTotalWeight();
        assertEquals(270, coffee.getTotalWeight());
    }

    @Test
    void getPriceFor100G() {
        coffee.setPriceFor100G(20.75);
        assertEquals(20.75, coffee.getPriceFor100G());
    }

    @Test
    void getCoffeePrice() {
        coffee.setCoffeeWeight(250);
        coffee.setPriceFor100G(20.0);
        coffee.calcTotalPrice();
        assertEquals(50.0, coffee.getCoffeePrice());
    }

    @Test
    void getTotalPrice() {
        coffee.setCoffeeWeight(250);
        coffee.setPriceFor100G(20.0);
        coffee.calcTotalPrice();
        assertEquals(50.0, coffee.getCoffeePrice());  // Coffee price without package
        assertEquals(50.0, coffee.getTotalPrice());   // Total price without package weight
    }

    @Test
    void getGrade() {
        coffee.setGrade(4.5);
        assertEquals(4.5, coffee.getGrade());
    }

    @Test
    void getTerm() {
        coffee.setTerm(12);
        assertEquals(12, coffee.getTerm());
    }

    @Test
    void calcTotalWeight() {
        coffee.setPackageType("Can");
        coffee.setCoffeeWeight(200);
        coffee.calcTotalWeight();
        assertEquals(260, coffee.getTotalWeight());

        coffee.setPackageType("Jar");
        coffee.setCoffeeWeight(200);
        coffee.calcTotalWeight();
        assertEquals(248, coffee.getTotalWeight());

        coffee.setPackageType("Pack");
        coffee.setCoffeeWeight(200);
        coffee.calcTotalWeight();
        assertEquals(208, coffee.getTotalWeight());
    }

    @Test
    void calcGrade() {
        coffee.calcGrade(5, 4, 3, 2);
        assertEquals(3.5, coffee.getGrade());
    }

    @Test
    void calcTotalPrice() {
        coffee.setCoffeeWeight(200);
        coffee.setPriceFor100G(10);
        coffee.setPackageType("Can");
        coffee.calcTotalWeight();
        coffee.calcTotalPrice();
        assertEquals(20, coffee.getCoffeePrice());
        assertEquals(110, coffee.getTotalPrice());  // Coffee price (20) + Package price (30)
    }

    @Test
    void setInfo() {
        Coffee anotherCoffee = new Coffee();
        anotherCoffee.setCoffeeName("Arabica");
        anotherCoffee.setManufacturer("Coffee Co.");
        anotherCoffee.setSort("Premium");
        anotherCoffee.setType("Ground");
        anotherCoffee.setTotalPrice(100);
        anotherCoffee.setPackageType("Jar");
        anotherCoffee.setTotalWeight(500);
        anotherCoffee.setCoffeeWeight(450);
        anotherCoffee.setPackageWeight(50);
        anotherCoffee.setTerm(12);
        anotherCoffee.setGrade(4.5);

        coffee.setInfo(anotherCoffee);

        assertEquals("Arabica", coffee.getCoffeeName());
        assertEquals("Coffee Co.", coffee.getManufacturer());
        assertEquals("Premium", coffee.getSort());
        assertEquals("Ground", coffee.getType());
        assertEquals(100, coffee.getTotalPrice());
        assertEquals("Jar", coffee.getPackageType());
        assertEquals(500, coffee.getTotalWeight());
        assertEquals(450, coffee.getCoffeeWeight());
        assertEquals(50, coffee.getPackageWeight());
        assertEquals(12, coffee.getTerm());
        assertEquals(4.5, coffee.getGrade());
    }

    @Test
    void showInfo() {
        // Since showInfo() just calls toWrite() and prints it, we can test toWrite() method.
        coffee.setCoffeeName("Arabica");
        coffee.setManufacturer("Coffee Co.");
        coffee.setSort("Premium");
        coffee.setType("Ground");
        coffee.setTotalPrice(100);
        coffee.setPackageType("Jar");
        coffee.setTotalWeight(500);
        coffee.setCoffeeWeight(450);
        coffee.setPackageWeight(50);
        coffee.setTerm(12);
        coffee.setGrade(4.5);

        String expected = "Coffee name: Arabica\n" +
                "Manufacturer: Coffee Co.\n" +
                "Sort: Premium\n" +
                "Type: Ground\n" +
                "Price: 100.0 UAH\n" +
                "Package type: Jar\n" +
                "Weight: 500.0 grams\n" +
                "Coffee Weight: 450.0 grams\n" +
                "Package Weight: 50.0 grams\n" +
                "Term: 12 month\n" +
                "Grade: 4.5";

        assertEquals(expected, coffee.toWrite());
    }

    @Test
    void toWrite() {
        coffee.setCoffeeName("Arabica");
        coffee.setManufacturer("Coffee Co.");
        coffee.setSort("Premium");
        coffee.setType("Ground");
        coffee.setTotalPrice(100);
        coffee.setPackageType("Jar");
        coffee.setTotalWeight(500);
        coffee.setCoffeeWeight(450);
        coffee.setPackageWeight(50);
        coffee.setTerm(12);
        coffee.setGrade(4.5);

        String expected = "Coffee name: Arabica\n" +
                "Manufacturer: Coffee Co.\n" +
                "Sort: Premium\n" +
                "Type: Ground\n" +
                "Price: 100.0 UAH\n" +
                "Package type: Jar\n" +
                "Weight: 500.0 grams\n" +
                "Coffee Weight: 450.0 grams\n" +
                "Package Weight: 50.0 grams\n" +
                "Term: 12 month\n" +
                "Grade: 4.5";

        assertEquals(expected, coffee.toWrite());
    }

    @Test
    void toWritePacked() {
        coffee.setCoffeeName("Arabica");
        coffee.setManufacturer("Coffee Co.");
        coffee.setSort("Premium");
        coffee.setType("Ground");
        coffee.setTotalPrice(100);
        coffee.setPackageType("Jar");
        coffee.setTotalWeight(500);
        coffee.setCoffeeWeight(450);
        coffee.setPackageWeight(50);
        coffee.setTerm(12);
        coffee.setGrade(4.5);
        coffee.setQuantity(10);

        String expected = "Coffee name: Arabica\n" +
                "Manufacturer: Coffee Co.\n" +
                "Sort: Premium\n" +
                "Type: Ground\n" +
                "Price: 100.0 UAH\n" +
                "Package type: Jar\n" +
                "Weight: 500.0 grams\n" +
                "Coffee Weight: 450.0 grams\n" +
                "Package Weight: 50.0 grams\n" +
                "Term: 12 month\n" +
                "Grade: 4.5\n" +
                "Quantity: 10";

        assertEquals(expected, coffee.toWritePacked());
    }

    @Test
    void testToString() {
        coffee.setCoffeeName("Arabica");
        assertEquals("Arabica", coffee.toString());
    }

    @Test
    void setQuantity() {
        coffee.setQuantity(10);
        assertEquals(10, coffee.getQuantity());
    }

    @Test
    void getQuantity() {
        coffee.setQuantity(10);
        assertEquals(10, coffee.getQuantity());
    }
}
