package com.example.advphoneapp;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class PhoneModel implements ITelephone {
	private String number = ""; // where we store the phone number. Like the phone's internal memory.
	HashMap<String, String> contacts = new HashMap<>();
	private PhoneControl pc;

	public PhoneModel(PhoneControl pc) {
		contacts.put("Simon", "0111541567");
		contacts.put("Dominoes", "0111545706");
		contacts.put("Sarah", "01435644552");
		contacts.put("Taxi", "01115660330");
		this.pc = pc;
	}

	public ArrayList<String> getContactNames() {
		ArrayList<String> list = new ArrayList<>();
		list.addAll(contacts.keySet());
		return list;
	}

	public void resetNumber() {
		number = "";
	}

	public void addDigit(String digit) {
		number = number + digit;
	}

	public String getNumber() {
		return number;
	}

	@Override
	public int makeCall(String number) {
		int duration = 0;
		if (number.matches("[0-9]+") && number.length() > 7) {
			duration = 10000;
			Caller caller = new Caller(pc);
			caller.setNumber(number);
			caller.setDuration(duration);
			new Thread(caller).start();
		} else {
			System.out.println(LocalTime.now() + " ... Error");
		}
		return duration;
	}

	public String getNumberForName(String name) {
		this.number = contacts.get(name);
		return number;
	}
}
