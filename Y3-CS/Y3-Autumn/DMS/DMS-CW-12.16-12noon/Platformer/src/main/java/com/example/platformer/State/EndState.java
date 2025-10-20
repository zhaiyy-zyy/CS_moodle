package com.example.platformer.State;

import com.example.platformer.controller.GameController;
/**
 * Represents the end state of the game in the state design pattern.
 * Handles actions to be performed when the game ends, such as stopping music, pausing the game, and displaying the end game screen.
 * Implements the {@link GameState} interface.
 */
public class EndState  implements GameState {
    /**
     * Indicates whether the player has won the game.
     */
    private boolean isWin;
    /**
     * Constructs an {@code EndState} with the specified game result.
     *
     * @param isWin {@code true} if the player has won the game; {@code false} otherwise.
     */
    public EndState(boolean isWin) {
        this.isWin = isWin;
    }
    /**
     * Performs actions when the game enters the end state.
     * Stops the background music, pauses the game, calculates the elapsed time, and displays the end game screen.
     *
     * @param gameController The {@link GameController} managing the game logic and state transitions.
     */
    @Override
    public void enter(GameController gameController) {
        gameController.getMusicPlayerController().stopMusic();
        gameController.game.isPaused = true;
        long elapsedTime = (System.currentTimeMillis() - gameController.game.startTime) / 1000; // computing time
        var endGamePane = gameController.endView.endGamePane(isWin, gameController.game, elapsedTime);
        gameController.endView.setEvent(endGamePane, gameController);
    }
    /**
     * Updates the game logic in the end state.
     * This implementation does nothing, as the game is paused in the end state.
     *
     * @param gameController The {@link GameController} managing the game logic.
     */
    @Override
    public void update(GameController gameController) {

    }
    /**
     * Performs cleanup actions when exiting the end state.
     * This implementation does nothing, as no cleanup is required for the end state.
     *
     * @param gameController The {@link GameController} managing the game logic.
     */
    @Override
    public void exit(GameController gameController) {

    }
}
