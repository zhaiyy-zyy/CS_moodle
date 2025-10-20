package com.example.platformer.controller;
import com.example.platformer.State.RunningState;
import com.example.platformer.model.Enemy;
import com.example.platformer.model.Game;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Objects;

/**
 * Controller class for managing the levels in the platformer game.
 * This class is responsible for setting, resetting, and loading levels.
 */
public class LevelController {
    /**
     * The game model that stores the current game state and entities.
     */
    private Game game;
    /**
     * Constructs a {@code LevelController} with a reference to the game model.
     *
     * @param game The {@link Game} object representing the current game state.
     */
    public LevelController(Game game) {
        this.game = game;
    }
    /**
     * Sets the current level of the game.
     *
     * @param level The level index to set as the current level.
     */
    public void setLevel(int level) {
        game.currentLevel = level;
    }
    /**
     * Resets the current level to its initial state.
     * This includes resetting the score, time, player position, and other entities.
     */
    public void resetLevel() {
        game.score = 0; // reset score
        game.notifyObservers(game.score);
        game.startTime = System.currentTimeMillis(); // reset time

        game.player.resetPosition();

        game.gameRoot.setLayoutX(0);
        game.keys.clear();
        game.gameController.initContent();

    }
    /**
     * Enter the game to the next level. If there are no more levels, the game ends with a victory.
     */
    public void nextLevel() {
        if (game.currentLevel < game.levels.length - 1) {
            game.currentLevel++; // next level
            resetLevel(); // reset level
            game.isPaused = false; // resume game
            game.gameController.setState(new RunningState());
        } else {
            game.gameController.endGame(true);
        }
    }

    /**
     * Creates an in-game entity (e.g., platform, finish line, or coin) and adds it to the specified root pane.
     *
     * @param x        The X position of the entity.
     * @param y        The Y position of the entity.
     * @param w        The width of the entity.
     * @param h        The height of the entity.
     * @param gameRoot The root pane to which the entity will be added.
     * @param type     The type of entity to create ("platform", "finishline", "coin").
     * @return A {@link Node} representing the created entity.
     */
    public Node createEntity(int x, int y, int w, int h, Pane gameRoot, String type) {
        String url = null;
        if(Objects.equals(type, "platform")){
            url = "file:src/main/resources/static/platform.png";
        } else if (Objects.equals(type, "finishline")) {
            url = "file:src/main/resources/static/finishline.png";
        } else if (Objects.equals(type, "coin")) {
            url = "file:src/main/resources/static/present.png";
        }
        assert url != null;
        Image image = new Image(url);
        ImageView entity = new ImageView(image);
        entity.setFitWidth(w);
        entity.setFitHeight(h);
        entity.setTranslateX(x);
        entity.setTranslateY(y);
        gameRoot.getChildren().add(entity);
        return entity;
    }
    /**
     * Loads a level based on the provided level data. This includes creating platforms, coins, enemies, and the finish line based on the level.
     *
     * @param currentLevelData An array of strings representing the level's layout.
     * Each character in the string represents a type of entity:
     * '1' - Platform
     * '2' - Coin
     * '3' - Enemy
     */
    public void loadLevel(String[] currentLevelData) {
        game.finishLine = createEntity(game.levelWidth - 60, 600, 60, 60, game.gameRoot, "finishline");
        for (int i = 0; i < currentLevelData.length; i++) {
            String line = currentLevelData[i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '1':
                        Node platform = createEntity(j * 60, i * 60, 60, 60, game.gameRoot, "platform");
                        game.platforms.add(platform);
                        break;
                    case '2':
                        Node coin = createEntity(j * 60 + 10, i * 60 + 10, 40, 40, game.gameRoot, "coin");
                        game.coins.add(coin);
                        break;
                    case '3': // New enemy
                        Enemy enemy = (new EnemyController(game)).createEntityEnemy(j * 60, i * 60, 45, 45,game.gameRoot);
                        game.enemiesList.add(enemy);
                        break;
                    default:
                        break;
                }
            }
        }
    }

}