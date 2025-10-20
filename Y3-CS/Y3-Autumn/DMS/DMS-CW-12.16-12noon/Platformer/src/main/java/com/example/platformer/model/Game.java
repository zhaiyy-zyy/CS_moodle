package com.example.platformer.model;

import com.example.platformer.Decorator.JumpingPlayer;
import com.example.platformer.Observer.Subject;
import com.example.platformer.State.PausedState;
import com.example.platformer.controller.GameController;
import com.example.platformer.controller.PlayerController;
import com.example.platformer.controller.SoundController;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents the main game model for the platformer game.
 * Handles the game state, player and enemy interactions, levels, score, and leaderboard management.
 * Extends the {@link Subject} class to allow observer-based updates for score changes.
 */
public class Game extends Subject {
    /**
     * Stores the current state of pressed keys for player movement.
     * Maps {@link KeyCode} to a {@code Boolean} indicating whether the key is pressed.
     */
    public HashMap<KeyCode, Boolean> keys = new HashMap<>();
    /**
     * A list of platforms in the current level.
     */
    public ArrayList<Node> platforms = new ArrayList<>();
    /**
     * A list of coins in the current level.
     */
    public ArrayList<Node> coins = new ArrayList<>();
    /**
     * The root pane containing all game elements.
     */
    public Pane appRoot = new Pane();
    /**
     * The pane containing game objects like the player, platforms, coins, and enemies.
     */
    public Pane gameRoot = new Pane();
    /**
     * The pane containing UI elements like score and time labels.
     */
    public Pane uiRoot = new Pane();
    /**
     * The player object, represented as a {@link JumpingPlayer}.
     */
    public JumpingPlayer player;
    /**
     * A list of enemies in the current level.
     */
    public ArrayList<Enemy> enemiesList = new ArrayList<>();
    /**
     * The width of the current level, calculated based on level data.
     */
    public int levelWidth;
    /**
     * The rectangle representing the game background.
     */
    public Rectangle bg;
    /**
     * The player's current score.
     */
    public int score = 0;
    /**
     * The label displaying the player's score.
     */
    public Label scoreLabel;
    /**
     * The timestamp when the game started.
     */
    public long startTime;
    /**
     * The label displaying the elapsed game time.
     */
    public Label timeLabel; // A label that displays the time
    /**
     * Indicates whether the game is currently paused.
     */
    public boolean isPaused = false;
    /**
     * The finish line object for the level.
     */
    public Node finishLine; // finish line
    /**
     * The label used to display game messages.
     */
    public Label messageLabel;
    /**
     * The timestamp when the game was paused.
     */
    public long pauseTime; // The timestamp of the pause
    /**
     * The array of levels in the game, each represented as a 2D string array.
     */
    public String[][] levels = {LevelData.Level1, LevelData.Level2, LevelData.Level3, LevelData.Level4};
    /**
     * The index of the current level.
     */
    public int currentLevel = 0; // Current level index
    /**
     * The {@link MediaPlayer} used to play background music.
     */
    public MediaPlayer backgroundMusicPlayer;

    /**
     * A map of leaderboards, where each level has its corresponding {@link Leaderboard}.
     * The key represents the level index.
     */
    public HashMap<Integer, Leaderboard> leaderboards = new HashMap<>();
    /**
     * The {@link GameController} managing the game logic and state transitions.
     */
    public GameController gameController;
    /**
     * The {@link PlayerController} managing player interactions and behavior.
     */
    public PlayerController playerController = new PlayerController(this);

    /**
     * Constructs a {@code Game} object with the specified {@link GameController}.
     * Initializes the leaderboards for each level.
     *
     * @param gameController The {@link GameController} managing the game state.
     */
    public Game(GameController gameController) {
        this.gameController = gameController;
        // Initialize the leaderboard with one file per level
        for (int i = 0; i < levels.length; i++) {
            leaderboards.put(i, new Leaderboard("leaderboard_level_" + (i + 1) + ".txt"));
        }
    }
    /**
     * Checks if the player has collided with any coins in the current level.
     * If a collision is detected, the coin is collected, the score is updated, and a sound is played.
     */
    public void checkCoinCollision() {
        ArrayList<Node> collectedCoins = new ArrayList<>();
        for (Node coin : coins) {
            if (player.getNode().getBoundsInParent().intersects(coin.getBoundsInParent())) {
                collectedCoins.add(coin);
                score += 10; // Add 10 points to each coin
                notifyObservers(score);
                SoundController.playMusic("src/main/resources/static/coin.wav");
            }
        }
        // Removes collected gold from the scene
        for (Node coin : collectedCoins) {
            gameRoot.getChildren().remove(coin);
            coins.remove(coin);
        }
    }

    /**
     * Checks if the player has died.
     * A player is considered dead if they fall below the game screen or collide with an enemy.
     * If the player has sufficient score, they are offered a respawn choice. Otherwise, the game ends.
     */
    public void checkPlayerDeath() {
        if (player.getTranslateY() > 720 || gameController.getEnemyController().checkEnemyCollision()) {
            player.setDead(true);
            // respawnPlayer
        if (score >= 10) {
                //A selection box pops up for the player to decide whether or not to revive
            gameController.setState(new PausedState());
                playerController.offerRespawnChoice();
            } else {
                gameController.endGame(false);
            }
        }
    }
    /**
     * Sets the background color of the game.
     *
     * @param color The new background color.
     */
    public void setBackgroundColor(Color color) {
        if (bg != null) {
            bg.setFill(color);
        }
    }
    /**
     * Checks if the specified key is currently pressed.
     *
     * @param key The {@link KeyCode} to check.
     * @return {@code true} if the key is pressed, {@code false} otherwise.
     */
    public boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }
    /**
     * Checks if the player has collided with the finish line.
     * If a collision is detected, the game ends with a victory.
     */
    public void checkFinishLineCollision() {
        if (player.getNode().getBoundsInParent().intersects(finishLine.getBoundsInParent())) {
            gameController.endGame(true);
        }
    }
}
