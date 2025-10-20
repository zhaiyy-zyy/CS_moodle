package com.example.phoneapptest;

import com.example.phoneapp.PhoneModel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PhoneTest {
	private PhoneModel pm = new PhoneModel();

	@Test
	public void makePhoneCall_realNumber_dialsOK() {
		int duration = pm.makeCall("0115675558933");
		assertTrue(duration > 0);
	}

	@Test
	public void makePhoneCall_notANumber_doesNotDial() {
		int duration = pm.makeCall("01aaffff3");
		assertTrue(duration == 0);
	}
}
