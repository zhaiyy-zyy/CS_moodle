package com.example.platformer.controller;

import com.example.platformer.Abstract.AbstractGameObject;
import com.example.platformer.Factory.EnemyFactory;
import com.example.platformer.model.Enemy;
import com.example.platformer.model.Game;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Controller class for managing enemies in the platformer game.
 * This class handles the creation, updating, and interaction of enemies, including their movements, collision detection, and behavior in the game environment.
 */
public class EnemyController {
    /**
     * Reference to the game model that holds the current game state and entities.
     */
    private Game game;
    /**
     * Factory used for creating enemy objects.
     */
    private EnemyFactory enemyFactory;
    /**
     * Constructs an {@code EnemyController} with a reference to the game model.
     * Initializes the enemy factory for creating enemy objects.
     *
     * @param game The {@link Game} object representing the current game state.
     */
    public EnemyController(Game game) {
        this.game = game;
        enemyFactory =  new EnemyFactory();
    }

    /**
     * Creates a new enemy entity and adds it to the game root pane.
     * The enemy is visually represented as an {@link ImageView} and is wrapped in an {@link Enemy} object.
     *
     * @param x        The initial X position of the enemy.
     * @param y        The initial Y position of the enemy.
     * @param w        The width of the enemy.
     * @param h        The height of the enemy.
     * @param gameRoot The root pane to which the enemy will be added.
     * @return An {@link Enemy} object representing the created enemy.
     */
    public Enemy createEntityEnemy(int x, int y, int w, int h, Pane gameRoot) {
        Image enemyPic = new Image("file:src/main/resources/static/enemy.png");
        ImageView enemyView = new ImageView(enemyPic);
        enemyView.setFitWidth(w);
        enemyView.setFitHeight(h);
        enemyView.setTranslateX(x);
        enemyView.setTranslateY(y);
        gameRoot.getChildren().add(enemyView);
        Enemy enemy = enemyFactory.createGameObject(enemyView);
        return enemy; // The initial velocity is (1, 0)
    }
    /**
     * Updates the position and behavior of all enemies in the game.
     * Handles horizontal movement, platform interactions, gravity application, and collision detection with platforms to reverse velocity when needed.
     */
    public void updateEnemies() {
        for (AbstractGameObject enemy : game.enemiesList) {
            Point2D velocity = enemy.getVelocity();

            // Move enemies horizontally
            enemy.moveX(velocity.getX(),null);

            // Detect if the enemy is standing on the platform
            boolean onPlatform = false;
            for (Node platform : game.platforms) {
                if (enemy.getNode().getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    // Detect if the enemy is on top of the platform
                    if (enemy.getNode().getTranslateY() + enemy.getNode().getBoundsInParent().getHeight() == platform.getTranslateY()) {
                        onPlatform = true;
                        break;
                    }
                }
            }

            // If the enemy is not standing on the platform, apply gravity
            if (!onPlatform) {
                enemy.setTranslateY(enemy.getNode().getTranslateY() + 5);
                continue;
            }

            // Detect if the enemy is touching the left and right sides of the platform
            boolean flipped = false;
            for (Node platform : game.platforms) {
                if (enemy.getNode().getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    // If the enemy hits the left platform
                    if (velocity.getX() < 0 &&
                            enemy.getNode().getTranslateX() <= platform.getTranslateX() + platform.getBoundsInParent().getWidth() &&
                            enemy.getNode().getTranslateY() + enemy.getNode().getBoundsInParent().getHeight() > platform.getTranslateY()) {
                        enemy.setVelocity(new Point2D(-velocity.getX(), velocity.getY()));
                        flipped = true;
                        break;
                    }

                    // If the enemy hits the right platform
                    if (velocity.getX() > 0 &&
                            enemy.getNode().getTranslateX() + enemy.getNode().getBoundsInParent().getWidth() >= platform.getTranslateX() &&
                            enemy.getNode().getTranslateY() + enemy.getNode().getBoundsInParent().getHeight() > platform.getTranslateY()) {
                        enemy.setVelocity(new Point2D(-velocity.getX(), velocity.getY())); // Reverse horizontal velocity
                        flipped = true;
                        break;
                    }
                }
            }

            // If the enemy hits the right obstacle
            if (flipped) {
                ImageView enemyView = (ImageView) enemy.getNode();
                double currentScale = enemyView.getScaleX();
                enemyView.setScaleX(-currentScale);
            }
        }
    }
    /**
     * Checks for collisions between the player and any enemies in the game.
     * A collision is detected if the player's bounds intersect with an enemy's bounds.
     *
     * @return {@code true} if a collision is detected; {@code false} otherwise.
     */
    public boolean checkEnemyCollision() {
        for (AbstractGameObject enemy : game.enemiesList) {
            Node playerNode = game.player.getNode();
            Node enemyNode = enemy.getNode();

            // Get player and enemy boundaries
            Bounds playerBounds = playerNode.getBoundsInParent();
            Bounds enemyBounds = enemyNode.getBoundsInParent();

            if(playerBounds.intersects(enemyBounds)){
                return true;
            }
        }
        return false; // No collision detected
    }
}
