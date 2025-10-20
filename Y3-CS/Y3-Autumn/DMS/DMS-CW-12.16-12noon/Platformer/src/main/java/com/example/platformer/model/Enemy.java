package com.example.platformer.model;

import com.example.platformer.Strategy.MoveStrategy;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import com.example.platformer.Abstract.AbstractGameObject;

import java.util.ArrayList;

/**
 * Represents an enemy in the platformer game.
 * Extends the {@link AbstractGameObject} class and uses a {@link MoveStrategy} to define its movement behavior.
 */
public class Enemy extends AbstractGameObject {
    /**
     * The movement strategy defining how the enemy moves.
     */
    private MoveStrategy moveStrategy;
    /**
     * Constructs an {@code Enemy} object with the specified visual representation and movement strategy.
     * Initializes the enemy's velocity to a default value and sets its movement speed.
     *
     * @param node         The {@link Node} representing the visual component of the enemy.
     * @param moveStrategy The {@link MoveStrategy} defining the enemy's movement behavior.
     */
    public Enemy(Node node, MoveStrategy moveStrategy) {
        super(node);
        this.velocity = new Point2D(1, 0); // Default velocity for the enemy
        this.moveStrategy = moveStrategy;
        this.speed = 5;
    }
    /**
     * Sets the movement strategy for the enemy.
     * Allows dynamic changes to the enemy's movement behavior during the game.
     *
     * @param moveStrategy The new {@link MoveStrategy} to be used by the enemy.
     */
    public void setMoveStrategy(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }
    /**
     * Moves the enemy horizontally by delegating the behavior to its {@link MoveStrategy}.
     * If no strategy is set, this method performs no action.
     *
     * @param value The horizontal movement distance.
     * @param nodes A list of {@link Node} objects representing potential obstacles or platforms.
     */
    @Override
    public void moveX(double value, ArrayList<Node> nodes){
        if(moveStrategy != null){
            moveStrategy.moveX(this, value, nodes);
        }
    }

    /**
     * Checks if the enemy collides with the given player.
     * A collision is determined by checking if the enemy's bounds intersect with the player's bounds.
     *
     * @param player The {@link Player} object to check for collision.
     * @return {@code true} if the enemy collides with the player, {@code false} otherwise.
     */
    public boolean checkCollision(Player player) {
        return getNode().getBoundsInParent().intersects(player.getNode().getBoundsInParent());
    }
}