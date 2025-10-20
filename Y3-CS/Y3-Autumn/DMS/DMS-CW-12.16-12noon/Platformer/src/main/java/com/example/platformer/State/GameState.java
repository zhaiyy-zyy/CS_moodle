package com.example.platformer.State;
import com.example.platformer.controller.GameController;

/**
 * Represents a state in the game's state machine.
 * Defines the structure for game states with methods for entering, updating, and exiting the state.
 */
public interface GameState {

    /**
     * Called when the game enters this state.
     * Contains logic for initializing the state and setting up necessary configurations.
     *
     * @param controller The {@link GameController} managing the game logic and state transitions.
     */
    void enter(GameController controller);

    /**
     * Called during each game update while this state is active.
     * Contains logic for handling actions or updates specific to this state.
     *
     * @param controller The {@link GameController} managing the game logic.
     */
    void update(GameController controller);

    /**
     * Called when the game exits this state.
     * Contains cleanup logic or actions needed before transitioning to another state.
     *
     * @param controller The {@link GameController} managing the game logic and state transitions.
     */
    void exit(GameController controller);
}