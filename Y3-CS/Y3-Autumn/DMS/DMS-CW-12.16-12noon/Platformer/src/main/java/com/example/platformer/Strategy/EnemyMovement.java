package com.example.platformer.Strategy;


import com.example.platformer.Abstract.AbstractGameObject;
import javafx.scene.Node;

import java.util.ArrayList;

/**
 * Represents the movement strategy for an enemy in the platformer game.
 * Implements the {@link MoveStrategy} interface to define horizontal movement behavior for enemies.
 */
public class EnemyMovement implements MoveStrategy {

    /**
     * Moves the enemy horizontally by the specified value.
     * Updates the enemy's horizontal position without collision detection or additional constraints.
     *
     * @param abstractGameObject The {@link AbstractGameObject} representing the enemy to be moved.
     * @param value              The horizontal movement distance.
     * @param platforms          A list of {@link Node} objects representing the platforms in the game.
     * This implementation does not use the platforms parameter.
     */
    @Override
    public void moveX(AbstractGameObject abstractGameObject, double value, ArrayList<Node> platforms) {
        abstractGameObject.setTranslateX(abstractGameObject.getTranslateX() + value);
    }
}
