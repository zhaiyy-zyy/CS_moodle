package com.ae2dms.designpatterns.factory;

public class PepperoniPizza extends Pizza {
    public PepperoniPizza(String name) {
        super(name);
    }

    public void prepare(){
        System.out.println("Preparing pepperoni pizza");
    }

    public void bake(){
        System.out.println("Baking pizza for 15 minutes at 180 Celsius");
    }

    public void cut(){
        System.out.println("Cutting pizza into 4 pieces.");
    }

    public void box(){
        System.out.println("Placing pizza into a red box");
    }
}
