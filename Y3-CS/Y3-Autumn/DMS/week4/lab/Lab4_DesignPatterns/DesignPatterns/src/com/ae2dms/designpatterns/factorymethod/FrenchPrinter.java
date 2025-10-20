package com.ae2dms.designpatterns.factorymethod;

public class FrenchPrinter extends Printer {
    public FrenchPrinter() {
    }

    public FrenchPrinter(boolean isSingleSided) {
        super(isSingleSided);
    }

    @Override
    public void print() {
        String word = isSingleSided ? "Single" : "Double";
        System.out.println("Printing French Materials, " + word +" sided.");
    }
}
