package com.example.platformer.Strategy;

import com.example.platformer.Abstract.AbstractGameObject;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
/**
 * Represents the movement strategy for a player in the platformer game.
 * Implements the {@link MoveStrategy} interface to define horizontal movement behavior for the player, including collision detection and directional mirroring.
 */
public class PlayerMovement implements MoveStrategy {
    /**
     * Moves the player horizontally based on the specified value, with collision detection against platforms.
     * Adjusts the player's facing direction (left or right) using mirroring logic.
     *
     * @param abstractGameObject The {@link AbstractGameObject} representing the player to be moved.
     * @param value              The horizontal movement distance.
     *                           Positive values indicate movement to the right, negative values to the left.
     * @param platforms          A list of {@link Node} objects representing platforms in the game.
     * Used for collision detection to prevent the player from passing through platforms.
     */
    @Override
    public void moveX(AbstractGameObject abstractGameObject, double value, ArrayList<Node> platforms) {
        boolean movingRight = value > 0;
        Node player = abstractGameObject.getNode();
        for (int i = 0; i < Math.abs(value); i++) {
            player.setTranslateX((player.getTranslateX() + (movingRight ? 1 : -1)));

            // Add mirror logic
            ImageView playerView = (ImageView) player;
            if (movingRight) {
                playerView.setScaleX(1); // facing right side
            } else {
                playerView.setScaleX(-1); // facing left side
            }

            for (Node platform : platforms) {
                if (player.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingRight) {
                        if (player.getTranslateX() + 40 == platform.getTranslateX()) {
                            player.setTranslateX((player.getTranslateX() - 1));
                            return;
                        }
                    } else {
                        if (player.getTranslateX() == platform.getTranslateX() + 60) {
                            player.setTranslateX((player.getTranslateX() + 1));
                            return;
                        }
                    }
                }
            }
        }
    }
}
