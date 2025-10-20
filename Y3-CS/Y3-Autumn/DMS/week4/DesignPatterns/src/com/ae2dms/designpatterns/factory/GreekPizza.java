package com.ae2dms.designpatterns.factory;

public class GreekPizza extends Pizza {
    public GreekPizza(String name) {
        super(name);
    }

    public void prepare(){
        System.out.println("Preparing greek pizza");
    }

    public void bake(){
        System.out.println("Baking pizza for 10 minutes at 250 Celsius");
    }

    public void cut(){
        System.out.println("Cutting pizza into 2 pieces.");
    }

    public void box(){
        System.out.println("Placing pizza into a green box");
    }
}
