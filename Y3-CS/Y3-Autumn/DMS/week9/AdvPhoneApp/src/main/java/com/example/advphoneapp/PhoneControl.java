package com.example.advphoneapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class PhoneControl {
	@FXML
	ImageView imvPhoneImage1;
	@FXML
	ImageView imvPhoneImage2;
	@FXML
	TextArea teaNumberDisplay;
	private PhoneModel pm = new PhoneModel(this);
	@FXML
	ComboBox<String> namesCB;
	@FXML
	HBox logoHBox;
	private Image logo = new Image(getClass().getResource("phone.png").toExternalForm());
	private Image onCall = new Image(getClass().getResource("on_call.png").toExternalForm());
	private Image offCall = new Image(getClass().getResource("off_call.png").toExternalForm());

	public void initialize() {
		ObservableList<String> contacts = FXCollections.observableArrayList(pm.getContactNames());
		logoHBox.setAlignment(Pos.CENTER);
		namesCB.setItems(contacts);
		imvPhoneImage1.setImage(logo);
		imvPhoneImage2.setImage(offCall);
	}

	public Image getOffCall() {
		return offCall;
	}

	public Image getOnCall() {
		return onCall;
	}

	@FXML
	public void onButtonPressed(ActionEvent event) {
		Button b = (Button) (event.getSource());
		pm.addDigit(b.getText());
		updateNumberDisplay();
	}

	@FXML
	public void onMakeCall() {
		pm.makeCall(pm.getNumber());
	}

	private void updateNumberDisplay() {
		teaNumberDisplay.setText(pm.getNumber());
	}

	public void resetNumber() {
		pm.resetNumber();
	}

	@FXML
	public void onNameSelectionMade() {
		String selectedName = (String) namesCB.getSelectionModel().getSelectedItem();
		String number = pm.getNumberForName(selectedName);
		teaNumberDisplay.setText(number);
	}

	@FXML
	public void onClearButtonPressed() {
		resetNumber();
		teaNumberDisplay.setText("");

	}
}
