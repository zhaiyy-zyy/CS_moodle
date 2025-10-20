package com.ae2dms.designpatterns.factorymethod;

public class FHSS extends Faculty {
    public Printer createPrinter(String type, String side){
        assert type != null;
        if (type.equalsIgnoreCase("English"))
            if(side.equalsIgnoreCase("Single"))
                return new EnglishPrinter(true);
            else if(side.equalsIgnoreCase("Double"))
                return new EnglishPrinter(false);
            else
                return new EnglishPrinter();
        else if (type.equalsIgnoreCase("German"))
            if(side.equalsIgnoreCase("Single"))
                return new GermanPrinter(true);
            else if(side.equalsIgnoreCase("Double"))
                return new GermanPrinter(false);
            else
                return new GermanPrinter();
        if (type.equalsIgnoreCase("French"))
            if(side.equalsIgnoreCase("Single"))
                return new EnglishPrinter(true);
            else if(side.equalsIgnoreCase("Double"))
                return new EnglishPrinter(false);
            else
                return new FrenchPrinter();
        System.out.println("Printer type not supported");
        return null;
    }
}
