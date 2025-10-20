package com.example.fxmaptreasurehunt.model;

import com.example.fxmaptreasurehunt.util.ResourceManagerUtil;


/**
 * Represents an opened treasure tile on the game map.
 * This class extends the {@link Item} class and is used to define a tile that represents
 * a treasure that has already been collected or opened by the player.
 */
public class OpenTreasure extends Item {

    /**
     * Constructs a new OpenTreasure tile at the specified coordinates.
     * The OpenTreasure tile is initialized with its open image, indicating that it has been collected.
     *
     * @param x the x-coordinate of the OpenTreasure tile
     * @param y the y-coordinate of the OpenTreasure tile
     */
    public OpenTreasure(int x, int y) {
        super(x, y, ResourceManagerUtil.loadOpenImage(), ResourceManagerUtil.loadOpenImage());
    }
}
