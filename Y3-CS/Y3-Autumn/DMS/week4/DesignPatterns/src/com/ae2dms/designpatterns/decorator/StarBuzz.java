package com.ae2dms.designpatterns.decorator;

public class StarBuzz {
    public static void main(String args[]){
        //Create a Decaf with Mocha and Whip

        //First create a Decaf
        Beverage beverage = new Decaf();
        System.out.println("Created a Decaf with price: " + beverage.cost());

        //Then decorate it with Mocha
        beverage = new Mocha(beverage);
        System.out.println("Blended with Mocha, price becomes: " + beverage.cost());

        //Then decorate it with Whip
        beverage = new Whip(beverage);
        System.out.println("Topped with Whip, price becomes: " + beverage.cost());

        //Topped with another Whip?
    }
}
