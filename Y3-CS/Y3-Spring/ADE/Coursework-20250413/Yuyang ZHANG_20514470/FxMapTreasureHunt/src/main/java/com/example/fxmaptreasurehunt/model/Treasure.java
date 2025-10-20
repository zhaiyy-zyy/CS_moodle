package com.example.fxmaptreasurehunt.model;

import com.example.fxmaptreasurehunt.util.ResourceManagerUtil;

/**
 * Represents a treasure tile on the game map.
 * This class extends the {@link Item} class and is used to define a tile that represents
 * a treasure that the player can collect.
 */
public class Treasure extends Item {

    /**
     * Constructs a new Treasure tile at the specified coordinates.
     * The Treasure tile is initialized with its initial image (representing the uncollected treasure)
     * and its image when collected.
     *
     * @param x the x-coordinate of the Treasure tile
     * @param y the y-coordinate of the Treasure tile
     */
    public Treasure(int x, int y) {
        super(x, y, ResourceManagerUtil.loadInitImage(), ResourceManagerUtil.loadTreasureImage());
    }
}
