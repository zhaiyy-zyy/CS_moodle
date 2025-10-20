package com.example.fxmaptreasurehunt.model;

import com.example.fxmaptreasurehunt.util.ResourceManagerUtil;

/**
 * Represents an obstacle tile on the game map.
 * This class extends the {@link Item} class and is used to define a tile that represents
 * an obstacle in the game, which the player cannot move through.
 */
public class Obstacle extends Item {

    /**
     * Constructs a new Obstacle tile at the specified coordinates.
     * The Obstacle tile is initialized with its default image (representing the obstacle) and its initial state.
     *
     * @param x the x-coordinate of the Obstacle tile
     * @param y the y-coordinate of the Obstacle tile
     */
    public Obstacle(int x, int y) {
        super(x, y, ResourceManagerUtil.loadInitImage(), ResourceManagerUtil.loadObstacleImage());
    }
}
