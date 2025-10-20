package com.example.advphoneapp;

import javafx.concurrent.Task;

import java.time.LocalTime;

public class Caller extends Task<String> {
	private String number;
	private long duration;
	private PhoneControl pc;

	public Caller(PhoneControl pc) {
		this.pc = pc;
	}

	@Override
	protected String call() throws Exception {
		String selectedName = (String) pc.namesCB.getSelectionModel().getSelectedItem();
		pc.teaNumberDisplay.setText("Hi " + selectedName);
		pc.imvPhoneImage2.setImage(pc.getOnCall());
		System.out.println(LocalTime.now() + " ... Dialling " + number);

		try {
			Thread.sleep(duration); // note how this hangs the interface! How can we fix that?
		} catch (InterruptedException e) {
			e.printStackTrace();
		} // faking phone call time.

		pc.teaNumberDisplay.setText("Bye " + selectedName);
		pc.imvPhoneImage2.setImage(pc.getOffCall());
		pc.resetNumber();
		System.out.println(LocalTime.now() + " ... Hanging up");
		return null;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}
}