package com.ae2dms.designpatterns.factorymethod;

public class Tester {

	public static void main(String[] args) {
		// Using Factory Pattern allows users to input request without knowing details.
		Faculty faculty = new FHSS();
		Printer printer1 = faculty.createPrinter("English", "Single");
		printer1.print();

		faculty = new FoSE();
		//FoSE cannot create a GermanPrinter. 'NullPointerExcepter' error occurs.
		Printer printer2 = faculty.createPrinter("German", "Double");
		printer2.print();

		//FoSE can create an EnglishPrinter
		printer2 = faculty.createPrinter("English", "Double");
		printer2.print();
	}
}
