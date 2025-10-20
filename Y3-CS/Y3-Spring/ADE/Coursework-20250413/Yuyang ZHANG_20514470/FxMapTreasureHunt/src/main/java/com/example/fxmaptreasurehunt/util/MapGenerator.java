package com.example.fxmaptreasurehunt.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The MapGenerator class is responsible for generating the game map,
 * including placing obstacles and treasures at random positions.
 * The map is represented as a 2D array where different values represent
 * different types of tiles (path, obstacle, treasure, and player).
 */
public class MapGenerator {
    /** The size of the map (MAP_SIZE x MAP_SIZE). */
    public static final int MAP_SIZE = 20; // Define the map size as a 20x20 grid

    /** The minimum number of obstacles to be placed on the map. */
    public static final int MIN_OBSTACLES = 15; // Set 15 obstacles on the map

    /** The number of treasures to be placed on the map. */
    public static final int NUM_TREASURES = 3; // Set up 3 treasures to be placed on the map
    /** The value representing a treasure on the map. */
    public static final int TREASURE_VALUE = 2; // 2 represents treasure
    /** The value representing an obstacle on the map. */
    public static final int OBSTACLE_VALUE = 1; // 1 represents obstacle
    /** The value representing an empty path on the map. */
    public static final int PATH_VALUE = 0; // 0 represents path
    /** The value representing the player's initial position on the map. */
    public static final int PLAY_VALUE = 3;
    /** Direction vectors for moving up, down, left, and right. */
    private static final int[][] D = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    /**
     * Generates a random game map with obstacles and treasures placed at valid positions.
     * The map is represented by a 2D integer array, where each element indicates a tile type.
     *
     * @return a 2D array representing the generated game map
     */
    public static int[][] generateMap() {
        int[][] map = new int[MAP_SIZE][MAP_SIZE]; // Create a 2D array to represent the map
        Random random = new Random();
        // Initialize map with obstacles
        int obstacleCount = 0; // Count the number of obstacles placed
        while (obstacleCount < MIN_OBSTACLES) {
            int x = random.nextInt(MAP_SIZE);
            int y = random.nextInt(MAP_SIZE);
            // Check if the obstacle can be placed at the current position
            if (isValidObstaclePosition(map, x, y) && !(x == 0 && y == 0)) {
                map[x][y] = OBSTACLE_VALUE;
                obstacleCount++;
            }
        }
        // Place treasures, ensuring they do not overlap with the player's starting position
        int treasureCount = 0; // Count the number of treasures placed
        while (treasureCount < NUM_TREASURES) {
            int x = random.nextInt(MAP_SIZE);
            int y = random.nextInt(MAP_SIZE);
            // Place treasure only on empty path tiles
            if (map[x][y] == PATH_VALUE && !(x == 0 && y == 0)) {
                map[x][y] = TREASURE_VALUE;
                treasureCount++;
            }
        }
        return map;
    }

    /**
     * valid the Obstacle Position by BFS
     * Traverse all points using BFS, and if the sum of points is+obstacleNumber==MAP_SIZE * MAP_SIZE,
     * then the position of the obstacle is considered valid
     *
     * @param map the map
     * @param x   x
     * @param y   y
     * @return true or false
     */
    private static boolean isValidObstaclePosition(int[][] map, int x, int y) {
        if (x == 0 && y == 0) return false; // It is not allowed to place obstacles at position (0, 0) because the player starts at this position
        if (map[x][y] == OBSTACLE_VALUE) return false; // If there is already an obstacle at that location, no more obstacles can be placed
        int[][] mapCopy = new int[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                mapCopy[i][j] = map[i][j];
            }
        }
        mapCopy[x][y] = OBSTACLE_VALUE;
        int obstacleNumber = 0;
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                if (mapCopy[i][j] == OBSTACLE_VALUE) obstacleNumber++;
            }
        }

        // Perform BFS to check if there is still a valid path
        boolean[][] isVisit = new boolean[MAP_SIZE][MAP_SIZE]; // Used to record visited locations
        isVisit[0][0] = true; // Mark the starting position (0, 0) as visited
        List<int[]> queue = new ArrayList<>(); // BFS queue
        queue.add(new int[]{0, 0}); // Add the starting position to the queue
        int k = 0;
        while (k < queue.size()) {
            int[] position = queue.get(k); // Get the position in the queue
            k++;
            for (int[] dd : D) { //Traverse in four directions (up, down, left, and right)
                int xx = position[0] + dd[0];
                int yy = position[1] + dd[1];
                if (xx < 0 || xx >= MAP_SIZE || yy < 0 || yy >= MAP_SIZE) continue; // If out of bounds, skip
                if (isVisit[xx][yy] || mapCopy[xx][yy] == OBSTACLE_VALUE) continue; // If it has been visited or is an obstacle, skip it
                queue.add(new int[]{xx, yy}); // Add the new position to the queue
                isVisit[xx][yy] = true;
            }
        }
        // If the number of visited points plus the number of obstacles equals the total number of tiles, the obstacle can be placed
        return queue.size() + obstacleNumber == MAP_SIZE * MAP_SIZE;
    }
}
