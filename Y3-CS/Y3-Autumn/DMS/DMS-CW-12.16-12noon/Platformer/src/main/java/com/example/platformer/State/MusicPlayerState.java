package com.example.platformer.State;
import com.example.platformer.controller.MusicPlayerController;

/**
 * Represents a state in the music player's state machine.
 * Defines a method to handle transitions or actions specific to a particular state.
 */
public interface MusicPlayerState {

    /**
     * Handles the behavior and state transition for the music player in the current state.
     *
     * @param controller The {@link MusicPlayerController} that manages the music player and its state transitions.
     */
    void handle(MusicPlayerController controller);
}