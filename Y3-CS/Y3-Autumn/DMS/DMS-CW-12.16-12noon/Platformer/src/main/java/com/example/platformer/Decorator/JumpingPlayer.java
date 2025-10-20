package com.example.platformer.Decorator;

import com.example.platformer.model.Player;
import javafx.scene.Node;

import java.util.ArrayList;
/**
 * A decorator class for adding jumping functionality to a {@link Player}.
 * Extends the {@link PlayerDecorator} class to enhance the base player's behavior with jump logic.
 */
public class JumpingPlayer extends PlayerDecorator {
    /**
     * Indicates whether the player is currently able to jump.
     */
    private boolean canJump = false;
    /**
     * Constructs a {@code JumpingPlayer} with the specified decorated player.
     * Enables the jumping ability by default.
     *
     * @param decoratedPlayer The base {@link Player} object to be decorated with jumping functionality.
     */
    public JumpingPlayer(Player decoratedPlayer) {
        super(decoratedPlayer);
        this.canJump = true;
    }
    /**
     * Makes the player jump if {@code canJump} is {@code true}.
     * Reduces the player's velocity in the Y-direction to simulate an upward jump and disables jumping until the player lands.
     */
    public void jump() {
        if (canJump) {
            // jump logic
            setVelocity(getVelocity().add(0, -30));
            canJump = false;
        }
    }
    /**
     * Sets the player's ability to jump.
     *
     * @param canJump {@code true} to allow the player to jump, {@code false} otherwise.
     */
    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }
    /**
     * Gets whether the player is currently able to jump.
     *
     * @return {@code true} if the player can jump, {@code false} otherwise.
     */
    public boolean getCanJump() {
        return canJump;
    }
    /**
     * Moves the player horizontally by a specified value, checking for collisions with nodes.
     * This method delegates the behavior to the decorated player's {@code moveX} implementation.
     *
     * @param value The horizontal movement distance.
     * @param nodes A list of {@link Node} objects representing potential obstacles.
     */
    @Override
    public void moveX(double value, ArrayList<Node> nodes) {
        super.moveX(value, nodes);
    }
    /**
     * Moves the player vertically by a specified value, checking for collisions with platforms.
     * Handles landing on platforms and resets the player's jumping ability when landing.
     *
     * This method performs the following:
     * If moving downwards, checks if the player lands on a platform and resets {@code canJump}.
     * If moving upwards, stops the player's upward movement upon colliding with the underside of a platform.
     *
     * @param value     The vertical movement distance.
     * @param platforms A list of {@link Node} objects representing the platforms in the game.
     */
    public void moveY(double value, ArrayList<Node> platforms) {
        boolean movingDown = value > 0;
        Node node = getNode();
        for (int i = 0; i < Math.abs(value); i++) {
            node.setTranslateY(node.getTranslateY() + (movingDown ? 1 : -1));
            for (Node platform : platforms) {
                if (node.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingDown) {
                        if (node.getTranslateY() + 40 == platform.getTranslateY()) {
                            node.setTranslateY((node.getTranslateY() - 1));
                            setCanJump(true);
                            return;
                        }
                    } else {
                        if (node.getTranslateY() == platform.getTranslateY() + 60) {
                            node.setTranslateY((int) (node.getTranslateY() + 1));
                            return;
                        }
                    }
                }
            }
        }
    }
}
