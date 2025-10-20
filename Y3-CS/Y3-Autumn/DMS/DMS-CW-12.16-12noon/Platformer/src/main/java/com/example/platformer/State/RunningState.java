package com.example.platformer.State;

import com.example.platformer.controller.GameController;
/**
 * Represents the running state of the game in the state design pattern.
 * Implements the {@link GameState} interface to handle behavior when the game is actively running.
 */
public class RunningState implements GameState{
    /**
     * Executes actions when the game enters the running state.
     * Initializes the game content, sets the `isPaused` flag to {@code false}, and records the current system time as the game start time.
     *
     * @param gameController The {@link GameController} managing the game logic and state transitions.
     */
    @Override
    public void enter(GameController gameController) {
        gameController.initContent();
        gameController.game.isPaused = false;
        gameController.game.startTime = System.currentTimeMillis();
    }
    /**
     * Updates the game logic while the game is in the running state.
     * Includes updates for player and enemy behavior and game time.
     *
     * @param gameController The {@link GameController} managing the game logic.
     */
    @Override
    public void update(GameController gameController) {
        gameController.getPlayerController().update(); // Update player logic
        gameController.getEnemyController().updateEnemies(); // Update enemy logic
        gameController.updateTime(); // Update time
    }
    /**
     * Executes actions when the game exits the running state.
     * This implementation does nothing, as no specific cleanup is required when exiting the running state.
     *
     * @param gameController The {@link GameController} managing the game logic and state transitions.
     */
    @Override
    public void exit(GameController gameController) {

    }
}
