package com.example.fxmaptreasurehunt.model;

import com.example.fxmaptreasurehunt.util.ResourceManagerUtil;

/**
 * Represents a Grassland tile on the game map.
 * This class extends the {@link Item} class and is used to define a tile that represents
 * an area of grassland in the game.
 *
 * Grassland tiles are initially empty and do not contain any obstacles or treasures.
 */
public class Grassland extends Item {

    /**
     * Constructs a new Grassland tile at the specified coordinates.
     * The Grassland tile is initialized with its default image and its visited state.
     *
     * @param x the x-coordinate of the Grassland tile
     * @param y the y-coordinate of the Grassland tile
     */
    public Grassland(int x, int y) {
        super(x, y, ResourceManagerUtil.loadInitImage(), ResourceManagerUtil.loadVisitImage());
    }
}
