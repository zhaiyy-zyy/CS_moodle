package com.example.platformer.Strategy;

import com.example.platformer.Abstract.AbstractGameObject;
import javafx.scene.Node;

import java.util.ArrayList;

/**
 * Defines a strategy for moving game objects in the platformer game.
 * Provides a method for handling horizontal movement, allowing different movement behaviors to be implemented.
 */
public interface MoveStrategy {

    /**
     * Moves a game object horizontally based on the provided value.
     * The implementation can include logic for collision detection, constraints, or other movement behaviors.
     *
     * @param abstractGameObject The {@link AbstractGameObject} to be moved.
     * @param value              The horizontal movement distance.
     * @param platforms          A list of {@link Node} objects representing platforms or obstacles in the game.
     * Implementations may use this parameter for collision detection or movement constraints.
     */
    void moveX(AbstractGameObject abstractGameObject, double value, ArrayList<Node> platforms);
}
