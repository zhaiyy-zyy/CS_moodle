package com.ae2dms.designpatterns.decorator;

public abstract class Beverage {
    String description = "Beverage (feature to be added on.)";

    public String getDescription() {
        return description;
    }

    public abstract double cost();
}
