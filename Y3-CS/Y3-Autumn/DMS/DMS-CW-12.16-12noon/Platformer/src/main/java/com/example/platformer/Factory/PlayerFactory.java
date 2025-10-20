package com.example.platformer.Factory;


import com.example.platformer.Strategy.PlayerMovement;
import com.example.platformer.model.Player;
import com.example.platformer.Abstract.AbstractGameObjectFactory;
import javafx.scene.Node;

/**
 * Factory class for creating {@link Player} objects.
 * Implements the {@link AbstractGameObjectFactory} to provide a specific implementation
 * for creating player game objects with predefined movement strategies.
 */
public class PlayerFactory extends AbstractGameObjectFactory {
    /**
     * Creates a new {@link Player} game object.
     * Configures the player with the given {@link Node} for its visual representation and a {@link PlayerMovement} strategy for its movement behavior.
     *
     * @param node The {@link Node} that represents the visual component of the player.
     * @return A new {@link Player} instance with the specified visual representation and movement behavior.
     */
    @Override
    public Player createGameObject(Node node) {
        return new Player(node, new PlayerMovement());
    }
}