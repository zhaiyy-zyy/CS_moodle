package com.example.platformer.State;
import com.example.platformer.controller.MusicPlayerController;
import javafx.scene.media.MediaPlayer;

/**
 * Represents the state of the music player when the music is playing.
 * Implements the {@link MusicPlayerState} interface to define behavior for transitioning from playing to paused state.
 */
public class MusicPlayingState implements MusicPlayerState {

    /**
     * Handles the transition from the playing state to the paused state.
     * Pauses the music playback, updates the music toggle button's text, and sets the music player to the {@link MusicPausedState}.
     *
     * @param controller The {@link MusicPlayerController} that manages the music player and its state.
     */
    @Override
    public void handle(MusicPlayerController controller) {
        MediaPlayer player = controller.getMediaPlayer();
        if (player != null) {
            player.pause(); // Pause music playback
            if (controller.getMusicToggleButton() != null) {
                controller.updateButtonText("Music Off"); // Update the button text
            }
            controller.setState(new MusicPausedState()); // Switch to the paused state
        }
    }
}