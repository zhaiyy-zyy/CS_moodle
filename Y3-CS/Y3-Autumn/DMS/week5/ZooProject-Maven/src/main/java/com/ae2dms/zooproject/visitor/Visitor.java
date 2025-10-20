package com.ae2dms.zooproject.visitor;

public class Visitor {
    int width, height;

    public void setValue(String name, int value) {
        if(name == "Height") height = value;
        else if(name == "Width") width = value;
    }

    void setHeight(int height) {
        this.height = height;
    }

    void setWidth(int width) {
        this.width = width;
    }

    public int calculatePrice(int country, int state, int[] rate) {
        int price = 0;
        if(country == 0)  price = rate[country];        // China
        else price = rate[state];
        return price;
    }

    public int calculateTax(int country, int state, int[] rate) {
        int tax = 0;
        if(country == 0) tax = rate[country];           // China
        else tax = rate[state];
        return tax;
    }
}
