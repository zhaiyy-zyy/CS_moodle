package com.example.phoneapp;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PhoneControl {
	@FXML ImageView imvPhoneImage;
	@FXML TextArea teaNumberDisplay;
	private PhoneModel pm = new PhoneModel();

	public void initialize() {
		Image image = new Image(getClass().getResource("phone.png").toExternalForm());
		imvPhoneImage.setImage(image);
	}
	
	public void on1Pressed() {
		pm.addDigit("1");
		updateNumberDisplay();
	}
	
	public void on2Pressed() {
		pm.addDigit("2");
		updateNumberDisplay();
	}
  
	public void on3Pressed() {
		pm.addDigit("3");
		updateNumberDisplay();
	}

	public void on4Pressed() {
		pm.addDigit("4");
		updateNumberDisplay();
	}

	public void on5Pressed() {
		pm.addDigit("5");
		updateNumberDisplay();
	}

	public void on6Pressed() {
		pm.addDigit("6");
		updateNumberDisplay();
	}

	public void on7Pressed() {
		pm.addDigit("7");
		updateNumberDisplay();
	}

	public void on8Pressed() {
		pm.addDigit("8");
		updateNumberDisplay();
	}

	public void on9Pressed() {
		pm.addDigit("9");
		updateNumberDisplay();
	}

	public void on0Pressed() {
		pm.addDigit("0");
		updateNumberDisplay();
	}
	
	/*
	 * @FXML public void onPressed(ActionEvent event) { Button
	 * b=(Button)(event.getSource()); pm.addDigit(b.getText());
	 * updateNumberDisplay(); }
	*/

	public void onMakeCall() {
		pm.makeCall(pm.getNumber());
	}

	private void updateNumberDisplay() {
		teaNumberDisplay.setText(pm.getNumber());
	}
}
