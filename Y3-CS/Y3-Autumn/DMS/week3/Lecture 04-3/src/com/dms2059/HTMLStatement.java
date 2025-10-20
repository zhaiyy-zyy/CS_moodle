package com.dms2059;

public class HTMLStatement extends Statement {
    @Override
    public String getFooter(Customer customer) {
        return "<p> Owed: <em>" + customer.totalCharge() + "<em></p>";
    }

    @Override
    public String getLineItems(Customer customer, String result) {
        for(Rental rental : customer.getRentals())
            result += rental.getMovie().getTitle() + "<br />";
        return result;
    }

    @Override
    public String getHeader(Customer customer) {
        return "<h1>Rental record:<em> " + customer.getName() + "</em></hl><br />";
    }
}
