package com.example.coffee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CoffeePack {
    private List<Coffee> coffeeList;
    private List<Integer> quantity;

    public CoffeePack() {
        this.coffeeList = new ArrayList<>();
        this.quantity = new ArrayList<>();
    }
    List<Coffee> getCoffeeList() { return coffeeList; }
    List<Integer> getQuantity() { return quantity; }
}
