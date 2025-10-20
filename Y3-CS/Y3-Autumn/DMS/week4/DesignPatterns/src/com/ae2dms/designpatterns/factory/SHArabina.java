package com.ae2dms.designpatterns.factory;

public class SHArabina extends Arabina {
    @Override
    public Pizza createPizza(String type) {
        if(type.equals("pepperoni"))
            return new PepperoniPizza(type);
        return null;
    }
}
