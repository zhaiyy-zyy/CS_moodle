package com.example.fxmaptreasurehunt.util;

import com.example.fxmaptreasurehunt.model.Item;
import com.example.fxmaptreasurehunt.model.Obstacle;
import com.example.fxmaptreasurehunt.model.Treasure;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * PathGenerator class is responsible for generating paths in the game map using pathfinding algorithms such as BFS and A*.
 */
public class PathGenerator {
    // Direction vectors for moving up, down, left, and right
    // These define the possible movements (right, left, down, up)
    private static final int[][] D = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    /**
     * Generates the shortest path from the starting position (x, y) to a treasure using the A* algorithm.
     * A* algorithm uses a heuristic (Manhattan distance) to find the shortest path more efficiently than BFS.
     *
     * @param items Map representing the game grid.
     * @param x     Starting x-coordinate of the player.
     * @param y     Starting y-coordinate of the player.
     * @return A list of coordinates representing the shortest path to a treasure.
     */
    public static List<int[]> getShortPathByAStar(Item[][] items, int x, int y) {
        // List to store all treasures on the map
        List<Treasure> treasureList = new ArrayList<>();
        for (int i = 0; i < items.length; i++) {
            for (int j = 0; j < items.length; j++) {
                if (items[i][j] instanceof Treasure)
                    treasureList.add((Treasure) items[i][j]);
            }
        }
        // Priority queue to store nodes with the lowest f-cost (g + h)
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        // Map to track the path leading to each node
        Map<String, int[]> cameFrom = new HashMap<>();
        // Map to store the g-score (cost to reach a node)
        Map<String, Integer> gScore = new HashMap<>();
        // Initialize the open set with all treasures' heuristic scores (h)
        for (Treasure treasure : treasureList) {
            int hScore = manhattan(x, y, treasure);
            openSet.add(new Node(x, y, 0, hScore));
        }
        gScore.put(Arrays.toString(new int[]{x, y}), 0);

        // Perform A* search
        while (!openSet.isEmpty()) {
            Node current = openSet.poll(); // Get the node with the lowest f-score
            // If a treasure is found, reconstruct the path and return it
            if (items[current.x][current.y] instanceof Treasure) {
                return reconstructPath(cameFrom, current.x, current.y, x, y);
            }

            // Explore all 4 possible directions (up, down, left, right)
            for (int[] d : D) {
                int xx = current.x + d[0];
                int yy = current.y + d[1];

                // Skip out-of-bounds or obstacle positions
                if (xx < 0 || xx >= items.length || yy < 0 || yy >= items.length) continue;
                if (items[xx][yy] instanceof Obstacle) continue;

                // Calculate tentative g-score for the neighbor node
                int tentativeG = current.g + 1;
                String neighborKey = Arrays.toString(new int[]{xx, yy});
                if (tentativeG < gScore.getOrDefault(neighborKey, Integer.MAX_VALUE)) {
                    cameFrom.put(neighborKey, new int[]{current.x, current.y});
                    gScore.put(neighborKey, tentativeG);

                    // Add the neighbor to the open set with updated f-score (g + h)
                    for (Treasure treasure : treasureList) {
                        int hScore = manhattan(xx, yy, treasure);
                        openSet.add(new Node(xx, yy, tentativeG, hScore));
                    }
                }
            }
        }
        return null;
    }

    /**
     * Calculates the Manhattan distance between a point (x, y) and a treasure's position.
     * Manhattan distance is used as the heuristic for A* algorithm.
     *
     * @param x        x-coordinate of the point.
     * @param y        y-coordinate of the point.
     * @param treasure The treasure to calculate the distance to.
     * @return The Manhattan distance.
     */
    private static int manhattan(int x, int y, Treasure treasure) {
        return Math.abs(x - treasure.getX() / Item.WIDTH) + Math.abs(y - treasure.getY() / Item.WIDTH);
    }

    /**
     * Reconstructs the path from the starting position to the end position using the cameFrom map.
     * This method backtracks from the end node to the start node to generate the complete path.
     *
     * @param cameFrom Map that tracks the node from which each position is reached.
     * @param endX     X-coordinate of the end node.
     * @param endY     Y-coordinate of the end node.
     * @param startX   X-coordinate of the start node.
     * @param startY   Y-coordinate of the start node.
     * @return A list of coordinates representing the path from the start to the end.
     */
    private static List<int[]> reconstructPath(Map<String, int[]> cameFrom,
                                               int endX, int endY,
                                               int startX, int startY) {
        List<int[]> path = new LinkedList<>();
        int[] current = {endX, endY};
        path.add(current);

        // Backtrack from the end node to the start node
        while (current[0] != startX || current[1] != startY) {
            current = cameFrom.get(Arrays.toString(new int[]{current[0], current[1]}));
            path.add(0, current); // Add each node to the path
        }
        return path;
    }
}
