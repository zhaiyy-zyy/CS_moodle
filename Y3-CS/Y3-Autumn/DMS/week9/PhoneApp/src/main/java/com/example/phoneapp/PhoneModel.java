package com.example.phoneapp;

import java.time.LocalTime;

public class PhoneModel implements ITelephone {
	private String number = ""; // where we store the phone number. Like the phone's internal memory.

	public void addDigit(String digit) {
		number = number + digit;
	}

	public String getNumber() {
		return number;
	}

	@Override
	public int makeCall(String number) {
		// this could just use the internal number variable. But we have to use the
		// interface so lets pass it as a parameter
		int duration = 0;
		if (number.matches("[0-9]+") && number.length() > 7) { // Only 'dial' if a valid number
			System.out.println(LocalTime.now() + " ... Dialling " + number);
			duration = 3000; // could make this random
			try {
				Thread.sleep(duration); // note how this hangs the interface! How can we fix that?
			} catch (InterruptedException e) {
				e.printStackTrace();
			} // faking phone call time.
			System.out.println(LocalTime.now() + " ... Hanging up");
		} else {
			System.out.println(LocalTime.now() + " ... Error");
		}
		return duration;
	}
}
