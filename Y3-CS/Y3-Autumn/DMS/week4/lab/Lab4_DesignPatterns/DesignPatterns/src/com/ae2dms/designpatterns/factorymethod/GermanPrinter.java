package com.ae2dms.designpatterns.factorymethod;

public class GermanPrinter extends Printer{

	public GermanPrinter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GermanPrinter(boolean single) {
		super(single);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void print() {
		String word = isSingleSided ? "Single" : "Double";
		System.out.println("Printing German Materials, " + word +" sided.");
	}
}
