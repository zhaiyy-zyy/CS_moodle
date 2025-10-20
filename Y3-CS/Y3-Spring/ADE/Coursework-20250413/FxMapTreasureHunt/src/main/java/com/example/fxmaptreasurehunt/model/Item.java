package com.example.fxmaptreasurehunt.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * The base class for game entities representing items on the map.
 * This class serves as the foundation for all entities in the game, such as obstacles, treasures, and grasslands.
 * It provides basic properties like position, images, and visibility for these entities.
 */
public class Item {
    /** The width of the item (used for its image view). */
    public static final int WIDTH = 30;
    /** The height of the item (used for its image view). */
    public static final int HEIGHT = 30;
    /** The image representing the item when it is not yet interacted with. */
    private Image image;
    /** The initial image of the item before it is interacted with. */
    private Image initImage;
    /** The ImageView used to display the item on the grid. */
    private ImageView imageView;
    /** The x-coordinate of the item on the grid. */
    private int x;
    /** A flag indicating whether the item is visible. */
    private boolean show;
    /** The y-coordinate of the item on the grid. */
    private int y;

    /**
     * Default constructor for Item.
     * Initializes the object without setting its position or images.
     */
    public Item() {

    }

    /**
     * Constructs an Item with a specified position and images.
     *
     * @param x the x-coordinate of the item
     * @param y the y-coordinate of the item
     * @param initImage the initial image of the item
     * @param image the image of the item to be displayed
     */
    public Item(int x, int y, Image initImage, Image image) {
        this.x = x;
        this.y = y;
        this.initImage = initImage;
        this.image = image;
        show = false;
        this.imageView = new ImageView(initImage);
        this.imageView.setFitHeight(HEIGHT);
        this.imageView.setFitWidth(WIDTH);
        this.imageView.setLayoutX(x);
        this.imageView.setLayoutY(y);
    }

    /**
     * Checks if the item is visible.
     *
     * @return true if the item is visible, false otherwise
     */
    public boolean isShow() {
        return show;
    }

    /**
     * Sets the item to be visible.
     */
    public void setShow() {
        show = true;
    }


    /**
     * Gets the current image of the item.
     *
     * @return the image of the item
     */
    public Image getImage() {
        return image;
    }

    /**
     * Sets the image of the item.
     *
     * @param image the new image for the item
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Gets the initial image of the item.
     *
     * @return the initial image of the item
     */
    public Image getInitImage() {
        return initImage;
    }

    /**
     * Sets the initial image of the item.
     *
     * @param initImage the new initial image for the item
     */
    public void setInitImage(Image initImage) {
        this.initImage = initImage;
    }

    /**
     * Gets the x-coordinate of the item.
     *
     * @return the x-coordinate of the item
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the item.
     *
     * @param x the new x-coordinate for the item
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the y-coordinate of the item.
     *
     * @return the y-coordinate of the item
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the item.
     *
     * @param y the new y-coordinate for the item
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Gets the ImageView used to display the item.
     *
     * @return the ImageView of the item
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Sets the ImageView for the item.
     *
     * @param imageView the new ImageView for the item
     */
    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
