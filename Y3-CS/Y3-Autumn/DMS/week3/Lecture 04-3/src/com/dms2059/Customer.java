package com.dms2059;

import java.util.ArrayList;

public class Customer {
    private String name;
    private ArrayList<Rental> rentals = new ArrayList<>();

    public Customer() {
        name = "COMP2059";
        rentals.add(new Rental());
        rentals.add(new Rental());
        rentals.add(new Rental());
    }

    public String getStatement(Statement statementFormatter) {
        return statementFormatter.getStatement(this);
    }

    public String totalCharge() {
        return "Charge!";
    }

    public String getName() {
        return name;
    }

    public ArrayList<Rental> getRentals() {
        return rentals;
    }
}
