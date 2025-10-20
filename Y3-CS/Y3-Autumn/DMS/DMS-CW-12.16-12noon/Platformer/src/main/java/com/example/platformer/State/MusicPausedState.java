package com.example.platformer.State;
import com.example.platformer.controller.MusicPlayerController;
import javafx.scene.media.MediaPlayer;

/**
 * Represents the state of the music player when the music is paused.
 * Implements the {@link MusicPlayerState} interface to define behavior for transitioning from paused to playing state.
 */
public class MusicPausedState implements MusicPlayerState{
    /**
     * Handles the transition from the paused state to the playing state.
     * Resumes the music playback, updates the music toggle button's text, and sets the music player to the {@link MusicPlayingState}.
     *
     * @param controller The {@link MusicPlayerController} that manages the music player and its state.
     */
    @Override
    public void handle(MusicPlayerController controller) {
        MediaPlayer player = controller.getMediaPlayer();
        if (player != null) {
            player.play(); // play the music
            if(controller.getMusicToggleButton()!=null){
                controller.updateButtonText("Music On");
            }
            controller.setState(new MusicPlayingState()); // Switch to play state
        }
    }
}
