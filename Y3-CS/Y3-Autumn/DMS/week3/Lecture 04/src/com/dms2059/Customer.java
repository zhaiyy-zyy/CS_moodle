package com.dms2059;

import java.util.ArrayList;

public class Customer {
    private String name;
    private ArrayList<Rental> rentals = new ArrayList<>();

    public String getStatement() {
        String result = null;
        result += "Rental record: " + name + "\n";
        for(Rental rental : rentals)
            result += "\t" + rental.getMovie().getTitle() + "\n";
        result += "Owed: " + totalCharge() + "\n";
        return result;
    }

    public String getHTMLStatement() {
        String result = null;
        result += "<h1>Rental record:<em> " + name + "</em></hl><br />";
        for(Rental rental : rentals)
            result += rental.getMovie().getTitle() + "<br />";
        result += "<p> Owed: <em>" + totalCharge() + "<em></p>";
        return result;
    }

    public String totalCharge() {
        return "Charge!";
    }
}
