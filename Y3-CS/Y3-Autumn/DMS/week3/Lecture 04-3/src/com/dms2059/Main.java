package com.dms2059;

public class Main {

    public static void main(String[] args) {
	    TextStatement ts = new TextStatement();
        HTMLStatement hs = new HTMLStatement();

        Customer customer = new Customer();
        String st = customer.getStatement(ts);
        System.out.println(st);
    }

}
