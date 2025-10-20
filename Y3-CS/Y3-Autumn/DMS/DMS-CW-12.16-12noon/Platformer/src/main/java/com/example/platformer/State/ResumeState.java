package com.example.platformer.State;

import com.example.platformer.controller.GameController;
/**
 * Represents the resumed state of the game in the state design pattern.
 * Implements the {@link GameState} interface to handle behavior when the game is resumed.
 */
public class ResumeState implements GameState{
    /**
     * Executes actions when the game enters the resumed state.
     * Resumes the game by setting the `isPaused` flag to {@code false} and adjusts the start time to compensate for the time spent in the paused state.
     *
     * @param gameController The {@link GameController} managing the game logic and state transitions.
     */
    @Override
    public void enter(GameController gameController) {
        gameController.game.isPaused = false;
        gameController.game.startTime += System.currentTimeMillis() - gameController.game.startTime;
    }
    /**
     * Updates the game logic while the game is in the resumed state.
     * Includes updates for player and enemy behavior and game time.
     *
     * @param gameController The {@link GameController} managing the game logic.
     */
    @Override
    public void update(GameController gameController) {
        gameController.getPlayerController().update(); // Update player logic
        gameController.getEnemyController().updateEnemies(); // Update enemy logic
        gameController.updateTime(); // update time
    }
    /**
     * Executes actions when the game exits the resumed state.
     * This implementation does nothing, as no specific cleanup is required when exiting the resumed state.
     *
     * @param gameController The {@link GameController} managing the game logic and state transitions.
     */
    @Override
    public void exit(GameController gameController) {

    }
}
