package com.example.platformer.model;

import com.example.platformer.Strategy.MoveStrategy;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import com.example.platformer.Abstract.AbstractGameObject;

/**
 * Represents the main player in the platformer game.
 * Extends the {@link AbstractGameObject} class and incorporates movement strategies for player behavior.
 */
public class Player extends AbstractGameObject {
    /**
     * Indicates whether the player can jump.
     */
    private boolean canJump;
    /**
     * Indicates whether the player is currently dead.
     */
    private boolean dead;
    /**
     * The movement strategy defining how the player moves.
     */
    private MoveStrategy moveStrategy;
    /**
     * Constructs a {@code Player} object with the specified visual representation and movement strategy.
     * Initializes the player's state, including its ability to jump, movement strategy, and speed.
     *
     * @param node         The {@link Node} representing the visual component of the player.
     * @param moveStrategy The {@link MoveStrategy} defining the player's movement behavior.
     */
    public Player(Node node,MoveStrategy moveStrategy) {
        super(node);
        this.dead = false;
        this.canJump = true ;
        this.moveStrategy = moveStrategy;
        this.speed = 5;
    }
    /**
     * Sets the movement strategy for the player.
     * Allows dynamic changes to the player's movement behavior during the game.
     *
     * @param moveStrategy The new {@link MoveStrategy} to be used by the player.
     */
    public void setMoveStrategy(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }
    /**
     * Gets the current movement strategy used by the player.
     *
     * @return The {@link MoveStrategy} defining the player's movement behavior.
     */
    public MoveStrategy getMoveStrategy() {
        return moveStrategy;
    }
    /**
     * Moves the player horizontally by delegating the behavior to its {@link MoveStrategy}.
     * If no strategy is set, this method performs no action.
     *
     * @param value The horizontal movement distance.
     * @param nodes A list of {@link Node} objects representing potential obstacles or platforms.
     */
    @Override
    public void moveX(double value, ArrayList<Node> nodes) {
        if(moveStrategy != null) {
            moveStrategy.moveX(this, value, nodes);
        }
    }

    /**
     * Checks if the player is currently dead.
     *
     * @return {@code true} if the player is dead, {@code false} otherwise.
     */
    public boolean getDead() {
        return dead;
    }
    /**
     * Sets the player's dead status.
     *
     * @param dead {@code true} to mark the player as dead, {@code false} otherwise.
     */
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    /**
     * Checks if the player collides with the finish line.
     * A collision is determined by checking if the player's bounds intersect with the finish line's bounds.
     *
     * @param finishLine The {@link Rectangle} representing the finish line.
     * @return {@code true} if the player collides with the finish line, {@code false} otherwise.
     */
    public boolean checkFinishLineCollision(Rectangle finishLine) {
        return getNode().getBoundsInParent().intersects(finishLine.getBoundsInParent());
    }
}