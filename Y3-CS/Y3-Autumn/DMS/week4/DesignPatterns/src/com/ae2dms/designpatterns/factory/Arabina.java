package com.ae2dms.designpatterns.factory;

public abstract class Arabina {

    //other normal methods
    public Pizza orderPizza(String type) {
        Pizza pizza = createPizza(type);

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }

    //product creation method
    public abstract Pizza createPizza(String type);
}


/**
 public Pizza orderPizza(String type) {
 Pizza pizza = null;

 if(type.equals("cheese"))
 pizza = new CheesePizza();
 else if(type.equals("greek"))
 pizza = new GreekPizza();
 else if(type.equals("pepperoni"))
 pizza = new PepperoniPizza();

 pizza.prepare();
 pizza.bake();
 pizza.cut();
 pizza.box();
 return pizza;
 }
 */

/*
PizzaFactory factory;

    public Arabina(PizzaFactory factory){
        this.factory = factory;
    }

    public Pizza orderPizza(String type) {
        Pizza pizza = factory.createPizza(type);

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }
    */
