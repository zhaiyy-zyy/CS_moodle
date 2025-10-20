package com.example.platformer.view;

import com.example.platformer.model.Game;
import com.example.platformer.util.Util;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.stage.Stage;

/**
 * Represents the options view in the platformer game.
 * Provides methods for creating UI components such as a color picker for background customization and a return button.
 */
public class OptionsView {
    /**
     * Creates and returns a color picker for selecting the game's background color.
     * The selected color is applied to the game's background and stored as a global setting.
     *
     * @param game The {@link Game} object to apply the selected background color. If null, only the global setting is updated.
     * @return A {@link ColorPicker} initialized with the current background color.
     */
    public ColorPicker colorPicker(Game game) {
        // ColorPicker
        ColorPicker picker = new ColorPicker(Util.backgroundColor);
        picker.setLayoutX(540);
        picker.setLayoutY(200);
        picker.setOnAction(event -> {
            Util.backgroundColor = picker.getValue();
            if (game != null) {
                game.setBackgroundColor(Util.backgroundColor);
            }
        });
        return picker;
    }
    /**
     * Creates and returns a button for returning to the main menu or previous screen.
     * When clicked, the button closes the provided options window.
     *
     * @param stage The {@link Stage} representing the options window to be closed.
     * @return A {@link Button} configured as a return button.
     */
    public Button backButton(Stage stage) {
        Button backButton = new Button("Return");
        backButton.setLayoutX(540);
        backButton.setLayoutY(260);
        backButton.setPrefWidth(200);
        backButton.setOnAction(event -> {
            // Close the current window and return to the main window
            stage.close();
        });
        return backButton;
    }
}
