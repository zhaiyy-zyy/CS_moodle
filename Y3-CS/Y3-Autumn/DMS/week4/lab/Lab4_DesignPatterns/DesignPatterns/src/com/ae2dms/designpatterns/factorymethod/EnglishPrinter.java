package com.ae2dms.designpatterns.factorymethod;

public class EnglishPrinter extends Printer{
	@Override
	public void print() {
		String word = isSingleSided ? "Single" : "Double";
		System.out.println("Printing English Materials, " + word +" sided.");
	}

	public EnglishPrinter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EnglishPrinter(boolean single) {
		super(single);
		// TODO Auto-generated constructor stub
	}

}
