package com.ae2dms.designpatterns.factory;

public class ArabinaTest {
    public static void main(String args[]){
        Arabina NBshop = new NBArabina();

        Pizza pizza = NBshop.orderPizza("greek");
        System.out.println("Ordered: " + pizza.getName());
        System.out.println();

        pizza = NBshop.orderPizza("cheese");
        System.out.println("Ordered: " + pizza.getName());

    }
}
