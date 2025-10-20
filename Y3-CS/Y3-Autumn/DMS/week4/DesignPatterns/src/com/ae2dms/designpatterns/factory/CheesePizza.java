package com.ae2dms.designpatterns.factory;

public class CheesePizza extends Pizza {
    public CheesePizza(String name) {
        super(name);
    }

    public void prepare(){
        System.out.println("Preparing cheese pizza");
    }

    public void bake(){
        System.out.println("Baking pizza for 20 minutes at 200 Celsius");
    }

    public void cut(){
        System.out.println("Cutting pizza into 6 pieces.");
    }

    public void box(){
        System.out.println("Placing pizza into a yellow box");
    }
}
