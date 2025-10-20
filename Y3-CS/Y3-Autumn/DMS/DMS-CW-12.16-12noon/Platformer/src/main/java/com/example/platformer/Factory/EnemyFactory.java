package com.example.platformer.Factory;

import com.example.platformer.Abstract.AbstractGameObjectFactory;
import com.example.platformer.Strategy.EnemyMovement;
import com.example.platformer.model.Enemy;
import javafx.scene.Node;

/**
 * Factory class for creating {@link Enemy} objects.
 * Implements the {@link AbstractGameObjectFactory} to provide a specific implementation for creating enemy game objects with predefined movement strategies.
 */
public class EnemyFactory extends AbstractGameObjectFactory {
    /**
     * Creates a new {@link Enemy} game object.
     * Configures the enemy with the given {@link Node} for its visual representation and an {@link EnemyMovement} strategy for its movement behavior.
     *
     * @param node The {@link Node} that represents the visual component of the enemy.
     * @return A new {@link Enemy} instance with the specified visual representation and movement behavior.
     */
    @Override
    public Enemy createGameObject(Node node) {
        return new Enemy(node,new EnemyMovement());
    }
}