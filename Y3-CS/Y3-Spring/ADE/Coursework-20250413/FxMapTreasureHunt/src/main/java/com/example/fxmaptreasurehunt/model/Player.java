package com.example.fxmaptreasurehunt.model;

import com.example.fxmaptreasurehunt.util.ResourceManagerUtil;
import javafx.scene.layout.GridPane;

/**
 * Represents the player character in the game.
 * This class extends the {@link Item} class and defines the player's position, image, and interaction with the game world.
 * The player can move across the grid and interact with items such as treasures and obstacles.
 */
public class Player extends Item {

    /**
     * Constructs a new Player at the specified coordinates.
     * The Player tile is initialized with the player's image, which represents the player's character on the grid.
     *
     * @param x the x-coordinate of the player on the grid
     * @param y the y-coordinate of the player on the grid
     */
    public Player(int x, int y) {
        super(x, y, ResourceManagerUtil.loadPlayerImage(), ResourceManagerUtil.loadPlayerImage());
    }

    /**
     * Handles the interaction between the player and an item on the map.
     * The player can move to a new tile and update its state (e.g., collecting treasure or hitting an obstacle).
     *
     * @param items the map grid of items (including obstacles, treasures, etc.)
     * @param x the target x-coordinate to move to
     * @param y the target y-coordinate to move to
     * @param gridPane the grid pane where the items are displayed
     * @param movePlay if true, the player will move to the new coordinates; otherwise, only the item state will be updated
     */
    public void contact(Item[][] items, int x, int y, GridPane gridPane, boolean movePlay) {
        // Check if the target coordinates are valid
        if (x < 0 || x >= items.length || y < 0 || y >= items[0].length) return;
        // If the treasure is already shown, do not interact with it again
        if (items[x][y].isShow()) return;
        Item item = items[x][y];
        int xx = x * HEIGHT, yy = y * WIDTH;

        // Set the item's image and mark it as shown
        item.getImageView().setImage(item.getImage());
        item.setShow();

        // Move the player to the new position if specified
        if (movePlay) {
            gridPane.getChildren().remove(getImageView());
            setX(xx);
            setY(yy);
            gridPane.add(getImageView(), xx, yy);
        }
    }
}
