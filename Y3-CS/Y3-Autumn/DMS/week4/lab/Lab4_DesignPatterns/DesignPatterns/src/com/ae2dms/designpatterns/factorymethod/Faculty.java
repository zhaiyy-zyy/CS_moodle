package com.ae2dms.designpatterns.factorymethod;

public abstract class Faculty {
    public abstract Printer createPrinter(String type, String side);
}
