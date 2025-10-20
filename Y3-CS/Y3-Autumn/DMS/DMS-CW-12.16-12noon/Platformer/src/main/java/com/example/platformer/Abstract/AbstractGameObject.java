package com.example.platformer.Abstract;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import java.util.ArrayList;

/**
 * Abstract class representing a general game object in the platformer game.
 * Both Player and Enemy objects will inherit from this class.
 * Provides basic properties and behaviors such as position, velocity, and movement.
 */
public abstract class AbstractGameObject {
    /**
     * The visual representation of the game object in the scene.
     */
    protected Node node;
    /**
     * The velocity of the game object, represented as a 2D vector.
     */
    protected Point2D velocity;
    /**
     * The movement speed of the game object.
     */
    protected int speed;
    /**
     * The initial X position of the game object when created.
     */
    double initPosX;
    /**
     * The initial Y position of the game object when created.
     */
    double initPosY;

    /**
     * Constructor for the AbstractGameObject class.
     * Initializes the game object with a visual node and sets the initial position and velocity.
     *
     * @param node The visual node representing the game object.
     */
    public AbstractGameObject(Node node) {
        this.node = node;
        this.velocity = new Point2D(0, 0);
        initPosX = node.getTranslateX();
        initPosY = node.getTranslateY();
    }

    /**
     * Gets the speed of the game object.
     *
     * @return The speed of the game object.
     */
    public int getSpeed() {return speed;}
    /**
     * Sets the speed of the game object.
     *
     * @param speed The new speed to be set.
     */
    public void setSpeed(int speed) {}
    /**
     * Gets the visual node of the game object.
     *
     * @return The node representing the game object.
     */
    public Node getNode() {
        return node;
    }
    /**
     * Sets the visual node of the game object.
     *
     * @param node The node to represent the game object.
     */
    public void setNode(Node node) {
        this.node = node;
    }

    /**
     * Gets the velocity of the game object.
     *
     * @return The velocity as a 2D vector.
     */
    public Point2D getVelocity() {
        return velocity;
    }
    /**
     * Sets the velocity of the game object.
     *
     * @param velocity The velocity as a 2D vector.
     */
    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    /**
     * Gets the current X position of the game object.
     *
     * @return The X position of the game object.
     */
    public double getTranslateX() {
        return node.getTranslateX();
    }
    /**
     * Sets the current X position of the game object.
     *
     * @param x The new X position of the game object.
     */
    public void setTranslateX(double x) {
        node.setTranslateX(x);
    }

    /**
     * Gets the current Y position of the game object.
     *
     * @return The Y position of the game object.
     */
    public double getTranslateY() {
        return node.getTranslateY();
    }
    /**
     * Sets the current Y position of the game object.
     *
     * @param y The new Y position of the game object.
     */
    public void setTranslateY(double y) {
        node.setTranslateY(y);
    }

    /**
     * Abstract method for horizontal movement, to be implemented by subclasses.
     * This method allows the object to move in the X direction and interact with other nodes.
     *
     * @param value The amount to move in the X direction.
     * @param nodes A list of nodes to check for collisions or interactions.
     */
    public abstract void moveX(double value, ArrayList<Node> nodes);
    /**
     * Reset player position
     */
    public void resetPosition() {
        setTranslateX(initPosX);
        setTranslateY(initPosY);
    }
    /**
     * Sets the position of the game object to specific X and Y coordinates.
     *
     * @param posX The new X position to be set.
     * @param posY The new Y position to be set.
     */
    public void setPosXY(double posX, double posY){
        setTranslateX(posX);
        setTranslateY(posY);
    }
}