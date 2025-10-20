package com.example.platformer.controller;

import com.example.platformer.Observer.ScoreLabelObserver;
import com.example.platformer.State.*;
import com.example.platformer.model.Enemy;
import com.example.platformer.model.Game;
import com.example.platformer.view.EndView;
import com.example.platformer.view.GameView;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
/**
 * Controller class for managing the overall game flow and state transitions in the platformer game.
 * This class handles initializing the game, updating game states, and managing interactions between the player, enemies, levels, and UI components.
 */
public class GameController {
    /**
     * The current state of the game, represented as a {@link GameState}.
     */
    private GameState currentState;
    /**
     * The view for rendering the main game elements.
     */
    public GameView gameView = new GameView();
    /**
     * The view for rendering the end game screen.
     */
    public EndView endView = new EndView();
    /**
     * The game model that stores the current game data and state.
     */
    public Game game = new Game(this);
    /**
     * Controller for managing the player's actions and interactions.
     */
    private PlayerController playController = new PlayerController(this.game);
    /**
     * Controller for managing the enemy's actions and interactions.
     */
    private EnemyController enemyController = new EnemyController(this.game);
    /**
     * Controller for managing the music player.
     */
    private MusicPlayerController musicPlayerController = new MusicPlayerController(this.game);
    /**
     * Controller for managing the level data.
     */
    private LevelController levelController = new LevelController(this.game);
    /**
     * Sets the current game state and transitions to the new state.
     * Exits the current state before entering the new state.
     *
     * @param state The new {@link GameState} to transition to.
     */
    public void setState(GameState state) {
        if(currentState != null) {
            currentState.exit(this);
        }
        currentState = state;
        currentState.enter(this);
    }
    /**
     * Updates the current state of the game by invoking the state's update logic.
     */
    public void update() {
        if(currentState != null) {
            currentState.update(this);
        }
    }
    /**
     * Resumes the game from a paused state and updates the pause button text to "Pause".
     *
     * @param pauseButton The button used to toggle pause/resume.
     */
    public void resumeGame(Button pauseButton) {
        setState(new ResumeState());
        if(pauseButton != null) {
            pauseButton.setText("Pause");
        }
    }
    /**
     * Pauses the game and updates the pause button text to "Resume".
     *
     * @param pauseButton The button used to toggle pause/resume.
     */
    public void pauseGame(Button pauseButton) {
        setState(new PausedState());
        if(pauseButton != null) {
            pauseButton.setText("Resume");
        }
    }
    /**
     * Ends the game and transitions to the end game state.
     *
     * @param isWin {@code true} if the player has won the game, {@code false} otherwise.
     */
    public void endGame(boolean isWin) {
        setState(new EndState(isWin));
    }
    /**
     * Restarts the game by resetting the player, enemies, and level data to their initial states.
     */
    public void restartGame() {

        musicPlayerController.stopMusic();
        levelController.resetLevel();
        game.player.resetPosition();
        for(Enemy enemy: game.enemiesList){
            enemy.resetPosition();
        }
        game.isPaused = false;
        resumeGame(null);
    }
    /**
     * Gets the {@link LevelController} managing the game's levels.
     *
     * @return The level controller.
     */
    public LevelController getLevelController() {
        return levelController;
    }
    /**
     * Gets the {@link PlayerController} managing the player's actions.
     *
     * @return The player controller.
     */
    public PlayerController getPlayerController() {
        return playController;
    }
    /**
     * Gets the {@link EnemyController} managing the enemies' behaviors.
     *
     * @return The enemy controller.
     */
    public EnemyController getEnemyController(){
        return enemyController;
    }
    /**
     * Gets the {@link MusicPlayerController} managing background music playback.
     *
     * @return The music player controller.
     */
    public MusicPlayerController getMusicPlayerController() {
        return musicPlayerController;
    }
    /**
     * Sets the background color of the game.
     *
     * @param color The new background color.
     */
    public void setBackgroundColor(Color color) {
        game.setBackgroundColor(color);
    }
    /**
     * Initializes the game's content, including levels, player, enemies, and UI elements.
     */
    public void initContent() {
        // Initialize the background music
        musicPlayerController.initialize();
        String[] currentLevelData = game.levels[game.currentLevel];

        game.appRoot.getChildren().clear();
        game.uiRoot.getChildren().clear();
        game.gameRoot.getChildren().clear();
        game.platforms.clear();
        game.coins.clear();
        SoundController.setupBackground(game);
        setupUIElements();
        // initialize score label and register observer
        ScoreLabelObserver scoreLabelObserver = new ScoreLabelObserver(game.scoreLabel);
        game.addObserver(scoreLabelObserver);

        levelController.loadLevel(currentLevelData);
        playController.setupPlayer();
        game.appRoot.getChildren().addAll(game.bg, game.gameRoot, game.uiRoot);
    }
    /**
     * Sets up the UI elements such as score, time, pause button, and messages.
     */
    private void setupUIElements() {
        Label scoreLabel = gameView.scoreLabel(game.score);
        game.scoreLabel = scoreLabel;
        game.uiRoot.getChildren().add(scoreLabel);

        Label timeLabel = gameView.timeLabel();
        game.timeLabel = timeLabel;
        game.uiRoot.getChildren().add(timeLabel);

        setupPauseButton();
        musicPlayerController.setupMusicToggleButton();
        // Adding message label
        Label messageLabel = gameView.messageLabel();
        game.messageLabel = messageLabel;
        game.uiRoot.getChildren().add(messageLabel);
    }
    /**
     * Sets up the pause button and its action listener.
     * Toggles between pause and resume states when clicked.
     */
    private void setupPauseButton() {
        Button pauseButton = gameView.pauseButton();
        pauseButton.setOnAction(event -> {
            if (game.isPaused) {
                resumeGame(pauseButton);
            } else {
                pauseGame(pauseButton);
            }
        });

        game.uiRoot.getChildren().add(pauseButton);
    }
    /**
     * Updates the elapsed time in the game and displays it on the time label.
     * The timer does not update when the game is paused.
     */
    public void updateTime() {
        if (game.isPaused) {
            return; // If paused, do not update the time
        }
        long currentTime = System.currentTimeMillis();
        // Convert to seconds
        long elapsedTime = (currentTime - game.startTime) / 1000;
        game.timeLabel.setText("Time: " + elapsedTime + "s");
    }

    /**
     * Initializes the game scene and sets up key event listeners.
     * Displays the game in the primary stage and starts the game loop.
     *
     * @param primaryStage The primary {@link Stage} for displaying the game.
     */
    public void initGameScene(Stage primaryStage) {
        setState(new RunningState());
        Scene scene = new Scene(game.appRoot);
        scene.setOnKeyPressed(event -> game.keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> game.keys.put(event.getCode(), false));
        primaryStage.setTitle("Sample game");
        primaryStage.setScene(scene);
        // Add window close listener to make sure the music stops
        primaryStage.setOnCloseRequest(event -> {
            musicPlayerController.stopMusic();
        });
        primaryStage.show();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
    }
}
