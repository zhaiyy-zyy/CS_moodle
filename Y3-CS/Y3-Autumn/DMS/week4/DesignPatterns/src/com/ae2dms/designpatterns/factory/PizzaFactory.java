package com.ae2dms.designpatterns.factory;

@Deprecated
public class PizzaFactory {

    public Pizza createPizza(String type) {
        if (type.equals("cheese"))
            return new CheesePizza(type);
        else if (type.equals("greek"))
            return new GreekPizza(type);
        else if (type.equals("pepperoni"))
            return new PepperoniPizza(type);
        return null;
    }

}
