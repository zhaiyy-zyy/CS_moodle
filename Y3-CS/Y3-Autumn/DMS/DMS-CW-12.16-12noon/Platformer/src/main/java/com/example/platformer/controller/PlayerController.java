package com.example.platformer.controller;

import com.example.platformer.Decorator.JumpingPlayer;
import com.example.platformer.Factory.PlayerFactory;
import com.example.platformer.model.Game;
import com.example.platformer.model.Player;
import com.example.platformer.view.RespawnView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
/**
 * Controller class for managing the player's behavior and interactions in the platformer game.
 * Handles player creation, movement, collision detection, and respawn functionality.
 */
public class PlayerController {
    /**
     * Reference to the game model that stores the current game state and entities.
     */
    private Game game;
    /**
     * Factory for creating player objects.
     */
    private PlayerFactory playerFactory;
    /**
     * Constructs a {@code PlayerController} with a reference to the game model.
     * Initializes the player factory for creating player entities.
     *
     * @param game The {@link Game} object representing the current game state.
     */
    public PlayerController(Game game) {
        this.game = game;
        this.playerFactory = new PlayerFactory();
    }
    /**
     * Creates a new player entity and adds it to the specified root pane.
     *
     * @param x        The X position of the player.
     * @param y        The Y position of the player.
     * @param w        The width of the player.
     * @param h        The height of the player.
     * @param color    The color of the player (currently unused, reserved for future customization).
     * @param gameRoot The root pane to which the player will be added.
     * @return A {@link Player} object representing the created player entity.
     */
    private Player createEntityPlayer(int x, int y, int w, int h, Color color, Pane gameRoot) {
        Image playerPic = new Image("file:src/main/resources/static/player.png");
        ImageView playerView = new ImageView(playerPic);
        playerView.setTranslateX(x);
        playerView.setTranslateY(y);
        playerView.setFitWidth(w);
        playerView.setFitHeight(h);
        gameRoot.getChildren().add(playerView);
        Player player = playerFactory.createGameObject(playerView);
        return player;
    }
    /**
     * Sets up the player entity with movement behavior and screen scrolling logic.
     * Adds a listener to handle screen scrolling as the player moves horizontally.
     */
    public void setupPlayer() {
        Player basePlayer = createEntityPlayer(0, 600, 40, 40, null, game.gameRoot);
        game.player = new JumpingPlayer(basePlayer);
        game.player.getNode().translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > 640 && offset < game.levelWidth - 640) {
                game.gameRoot.setLayoutX(-(offset - 640));
            }
        });
    }
    /**
     * Updates the player's state, handling movement, collision detection, and interactions.
     * Handles the following actions:
     * Player movement based on key presses (W, A, D, and SHIFT).
     * Applies gravity to the player.
     * Handles collisions with platforms, coins, and the finish line.
     * Checks if the player has died.
     */
    public void update() {
        if (game.isPressed(KeyCode.W) && game.player.getTranslateY() >= 5) {
            game.player.jump();
        }
        if (game.isPressed(KeyCode.A) && game.player.getTranslateX() >= 5) {
            int speed = game.isPressed(KeyCode.SHIFT) ? -10 : -5; // Shift double ring astern
            game.player.moveX(speed, game.platforms);
        }
        if (game.isPressed(KeyCode.D) && game.player.getTranslateX() + 40 <= game.levelWidth - 5) {
            int speed = game.isPressed(KeyCode.SHIFT) ? 10 : 5; // Shift double ring astern
            game.player.moveX(speed, game.platforms);
        }
        if (game.player.getVelocity().getY() < 10) {
            game.player.setVelocity(game.player.getVelocity().add(0,1));
        }
        game.player.moveY((int)game.player.getVelocity().getY(),game.platforms);

        game.checkCoinCollision();// Check gold collision

        game.checkPlayerDeath(); // Check if the player is dead
        game.checkFinishLineCollision(); // Check finish line collision
    }
    /**
     * Offers the player a choice to respawn after dying.
     * Pauses the game and background music, then displays a respawn menu.
     */
    public void offerRespawnChoice() {
        // Pause the game and record the pause time
        game.isPaused = true;
        game.pauseTime = System.currentTimeMillis();

        // Pause background music
        game.gameController.getMusicPlayerController().pauseMusic();
        new RespawnView().respawnPane(this.game);
    }

}
