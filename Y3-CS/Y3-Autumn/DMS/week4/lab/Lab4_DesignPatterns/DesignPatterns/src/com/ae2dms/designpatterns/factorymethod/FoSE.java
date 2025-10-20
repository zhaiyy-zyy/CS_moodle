package com.ae2dms.designpatterns.factorymethod;

public class FoSE extends Faculty {
    @Override
    public Printer createPrinter(String type, String side) {
        if (type.equalsIgnoreCase("English"))
            if(side.equalsIgnoreCase("Single"))
                return new EnglishPrinter(true);
            else if(side.equalsIgnoreCase("Double"))
                return new EnglishPrinter(false);
            else
                return new EnglishPrinter();
        System.out.println("Printer type not supported");
        return null;
    }
}
