package com.example.platformer.Abstract;

import javafx.scene.Node;


/**
 * Abstract factory class for creating game objects in the platformer game.
 * This factory provides a blueprint for creating different types of game objects by encapsulating the instantiation logic.
 *
 * Subclasses of this factory should implement the {@link #createGameObject(Node)} method to define the specific type of game object to create.
 */
public abstract class AbstractGameObjectFactory {
    /**
     * Creates a new instance of an {@link AbstractGameObject}.
     *
     * This method is abstract and must be implemented by subclasses to provide the creation logic for specific types of game objects.
     *
     * @param node The {@link Node} that represents the visual component of the game object. It is used to define the appearance and position of the object in the game.
     * @return An instance of {@link AbstractGameObject} corresponding to the specific type of object created by the factory.
     */
    public abstract AbstractGameObject createGameObject(Node node);
}