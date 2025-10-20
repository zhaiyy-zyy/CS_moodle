package com.dms2059;

public class TextStatement extends Statement {
    @Override
    public String getFooter(Customer customer) {
        return "Owed: " + customer.totalCharge() + "\n";
    }

    @Override
    public String getLineItems(Customer customer, String result) {
        for(Rental rental : customer.getRentals())
            result += "\t" + rental.getMovie().getTitle() + "\n";
        return result;
    }

    @Override
    public String getHeader(Customer customer) {
        return "Rental record: " + customer.getName() + "\n";
    }
}
