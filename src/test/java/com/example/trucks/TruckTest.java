package com.example.trucks;

import com.example.coffee.Coffee;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TruckTest {

    @Test
    void testSetTruckName() {
        Truck truck = new Truck();
        truck.setTruckName("Truck 1");
        assertEquals("Truck 1", truck.getTruckName());
    }

    @Test
    void testSetMaxWeight() {
        Truck truck = new Truck();
        truck.setMaxWeight(1000.0);
        assertEquals(1000.0, truck.getMaxWeight());
    }

    @Test
    void testSetCompleted() {
        Truck truck = new Truck();
        truck.setCompleted(true);
        assertTrue(truck.isCompleted());
    }

    @Test
    void testAddCoffee() {
        Truck truck = new Truck();
        truck.setMaxWeight(100);
        Coffee coffee = new Coffee();
        coffee.setTotalWeight(100);
        truck.addCoffee(coffee, 5);
        assertEquals(0.5, truck.getCurrentWeight());
        assertEquals(1, truck.getCoffeeList().size());
    }

    @Test
    void testUpdateCoffeeQuantity() throws IOException {
        Truck truck = new Truck();
        truck.setMaxWeight(100);
        Coffee coffee = new Coffee();
        coffee.setTotalWeight(100);
        coffee.setQuantity(1);
        truck.addCoffee(coffee, 5);
        truck.updateCoffeeQuantity(coffee, 10);
        assertEquals(1.4, truck.getCurrentWeight());
        assertEquals(10, coffee.getQuantity());
    }

    @Test
    void testDeletePackedCoffee() {
        Truck truck = new Truck();
        truck.setMaxWeight(100);
        Coffee coffee = new Coffee();
        coffee.setTotalWeight(100);
        coffee.setQuantity(5);
        truck.addCoffee(coffee, 5);
        truck.deletePackedCoffee(coffee);
        assertEquals(0.0, truck.getCurrentWeight());
        assertEquals(0, truck.getCoffeeList().size());
    }
}
