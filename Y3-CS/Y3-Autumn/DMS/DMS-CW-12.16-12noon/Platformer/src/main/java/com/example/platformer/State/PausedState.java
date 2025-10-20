package com.example.platformer.State;

import com.example.platformer.controller.GameController;
/**
 * Represents the paused state of the game in the state design pattern.
 * Implements the {@link GameState} interface to handle behavior when the game is paused.
 */
public class PausedState implements GameState{
    /**
     * Executes actions when the game enters the paused state.
     * Pauses the game by setting the `isPaused` flag to {@code true} and records the current system time as the pause time.
     *
     * @param gameController The {@link GameController} managing the game logic and state transitions.
     */
    @Override
    public void enter(GameController gameController) {
       gameController.game.isPaused = true;
       gameController.game.pauseTime = System.currentTimeMillis();
    }
    /**
     * Updates the game logic in the paused state.
     * This implementation does nothing, as no updates are required while the game is paused.
     *
     * @param gameController The {@link GameController} managing the game logic.
     */
    @Override
    public void update(GameController gameController) {

    }
    /**
     * Executes actions when the game exits the paused state.
     * Adjusts the game's start time to account for the time spent in the paused state, effectively resuming the game.
     *
     * @param gameController The {@link GameController} managing the game logic and state transitions.
     */
    @Override
    public void exit(GameController gameController) {
        gameController.game.startTime += System.currentTimeMillis() - gameController.game.pauseTime;
    }
}
