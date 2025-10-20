package com.ae2dms.designpatterns.factory;

public class NBArabina extends Arabina {
    @Override
    public Pizza createPizza(String type) {
        if(type.equals("cheese"))
            return new CheesePizza(type);
        else if(type.equals("greek"))
            return new GreekPizza(type);
        return null;
    }
}
