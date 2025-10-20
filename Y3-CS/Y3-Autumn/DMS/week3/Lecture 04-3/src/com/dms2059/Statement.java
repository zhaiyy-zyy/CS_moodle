package com.dms2059;

public abstract class Statement {
    public String getStatement(Customer customer) {
        String result = "Test";
        result += getHeader(customer);
        result = getLineItems(customer, result);
        result += getFooter(customer);
        return result;
    }

    public abstract String getFooter(Customer customer);

    public abstract String getLineItems(Customer customer, String result);

    public abstract String getHeader(Customer customer);
}
