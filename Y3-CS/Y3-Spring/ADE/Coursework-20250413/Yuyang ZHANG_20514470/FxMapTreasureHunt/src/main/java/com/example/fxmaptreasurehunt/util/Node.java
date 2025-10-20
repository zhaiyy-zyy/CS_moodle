package com.example.fxmaptreasurehunt.util;

/**
 * Represents a node used in pathfinding algorithms such as A*.
 * Each node contains information about its position on the map, its cost to reach,
 * and its total estimated cost (f = g + h).
 */
public class Node {
    /** The x-coordinate of the node on the map. */
    int x;
    /** The y-coordinate of the node on the map. */
    int y;
    /** The cost to reach this node from the start node (g). */
    int g;
    /** The total cost of the node, calculated as f = g + h. */
    int f;  // f = g + h

    /**
     * Constructs a new Node with the specified coordinates and costs.
     *
     * @param x the x-coordinate of the node
     * @param y the y-coordinate of the node
     * @param g the cost to reach this node from the start node
     * @param h the heuristic estimated cost from this node to the goal
     */
    Node(int x, int y, int g, int h) {
        this.x = x;
        this.y = y;
        this.g = g;
        this.f = g + h;
    }
}