package com.ae2dms.zooproject.food;

import com.ae2dms.zooproject.animal.Amphibian;
import com.ae2dms.zooproject.animal.Animal;
import com.ae2dms.zooproject.zoo.Zoo;

public class Meat {
    int numberOfCapacity;
    double weight, rate, budget;

    public double calcTotalWeight(Animal animal, Zoo zoo,
                                  int day, double rate, int quantity,
                                  String country, String state,
                                  double budget) {

        if(basePrice() > 1000) return basePrice() * 0.95;
        else return basePrice() * 0.98;
    }

    double FIX_RATE = 120;
    public double basePrice() {
        return weight * rate + (budget - FIX_RATE);
    }

    public double discount(double price, int quantity) {
        double result = price;
        if(price > 120) { result -= 20; }
        return result;
    }

//    String type = null;
    //        int weight = 0;
//
//        // get animal type
//        if(animal.getClass() == Amphibian.class) {
//            type = "Frog";
//        } else { }
//
//        // calculate the changing rate
//        if(type == "Frog") {
//            rate = getRate();
//        } else { }
//
//        if(country == "China") {
//            budget = 200;
//        } else { }

    private double getRate() {
        return numberOfCapacity > 10 ? 5 : 1;
    }

//    private double getRate() {
//        return moreThanCapacity() ? 5 : 1;
//    }

    private boolean moreThanCapacity() {
        return numberOfCapacity > 10;
    }

    private String getAnimalType(Animal animal) {
        String type = null;
        if(animal.getClass() == Amphibian.class) {
            type = "Frog";
        } else { }

        return type;
    }
}
