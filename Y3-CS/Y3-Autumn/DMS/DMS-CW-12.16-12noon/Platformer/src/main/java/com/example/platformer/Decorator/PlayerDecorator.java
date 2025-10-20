package com.example.platformer.Decorator;

import com.example.platformer.model.Player;
import javafx.scene.Node;

import java.util.ArrayList;
/**
 * Abstract decorator class for extending the functionality of a {@link Player}.
 * Implements the decorator design pattern to dynamically add or override behaviors of a base player.
 * This class serves as a wrapper around a {@link Player} instance.
 */
public abstract class PlayerDecorator extends Player {
    /**
     * The base {@link Player} instance that is being decorated.
     */
    protected Player decoratedPlayer;
    /**
     * Constructs a {@code PlayerDecorator} with the specified decorated player.
     * Delegates the base {@link Player}'s properties such as its {@link Node} and movement strategy to this decorator.
     *
     * @param decoratedPlayer The {@link Player} object to be decorated.
     */
    public PlayerDecorator(Player decoratedPlayer) {
        super(decoratedPlayer.getNode(), decoratedPlayer.getMoveStrategy());
        this.decoratedPlayer = decoratedPlayer;
    }
    /**
     * Moves the player horizontally by delegating the behavior to the decorated player's {@code moveX} method.
     * This method checks for potential collisions with the provided nodes.
     *
     * @param value The horizontal movement distance.
     * @param nodes A list of {@link Node} objects representing potential obstacles or platforms.
     */
    @Override
    public void moveX(double value, ArrayList<Node> nodes) {
        decoratedPlayer.moveX(value, nodes);
    }
    /**
     * Gets the {@link Node} object representing the player's visual component.
     * Delegates the call to the decorated player's {@code getNode} method.
     *
     * @return The {@link Node} object of the decorated player.
     */
    @Override
    public Node getNode() {
        return decoratedPlayer.getNode();
    }

}
