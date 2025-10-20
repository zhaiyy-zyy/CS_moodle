package com.example.fxmaptreasurehunt.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapGenerator {
    public static final int MAP_SIZE = 20;
    public static final int MIN_OBSTACLES = 10;
    public static final int NUM_TREASURES = 3;
    public static final int TREASURE_VALUE = 3;
    public static final int OBSTACLE_VALUE = 1;
    public static final int PATH_VALUE = 0;
    public static final int PLAY_VALUE = 4;
    private static final int[][] D = {{1,0},{0,1},{-1,0},{0,-1}};

    public static int[][] generateMap(){
        int[][] map = new int[MAP_SIZE][MAP_SIZE];
        Random random = new Random();
        // Initialize map with obstacles
        int obstacleCount = 0;
        while (obstacleCount < MIN_OBSTACLES) {
            int x = random.nextInt(MAP_SIZE);
            int y = random.nextInt(MAP_SIZE);
            if (isValidObstaclePosition(map,x,y)) {
                map[x][y] = OBSTACLE_VALUE;
                obstacleCount++;
            }
        }
        int treasureCount = 0;
        while(treasureCount<NUM_TREASURES){
            int x = random.nextInt(MAP_SIZE);
            int y = random.nextInt(MAP_SIZE);
            if (map[x][y]==PATH_VALUE) {
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
     * @param map the map
     * @param x x
     * @param y y
     * @return true or false
     */
    private static boolean isValidObstaclePosition(int[][] map,int x,int y){
        if(x==0&&y==0) return false;
        if(map[x][y]==OBSTACLE_VALUE) return false;
        int[][] mapCopy=new int[map.length][map[0].length];
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[i].length;j++){
                mapCopy[i][j]=map[i][j];
            }
        }
        mapCopy[x][y]=OBSTACLE_VALUE;
        int obstacleNumber=0;
        for(int i=0;i<MAP_SIZE;i++){
            for(int j=0;j<MAP_SIZE;j++){
                if(mapCopy[i][j]==OBSTACLE_VALUE) obstacleNumber++;
            }
        }
        boolean[][] isVisit=new boolean[MAP_SIZE][MAP_SIZE];
        isVisit[0][0]=true;
        List<int[]> queue=new ArrayList<>();
        queue.add(new int[]{0,0});
        int k=0;
        while(k<queue.size()){
            int[] position=queue.get(k);
            k++;
            for(int[] dd:D){
                int xx=position[0]+dd[0];
                int yy=position[1]+dd[1];
                if(xx<0||xx>=MAP_SIZE||yy<0||yy>=MAP_SIZE) continue;
                if(isVisit[xx][yy]||mapCopy[xx][yy]==OBSTACLE_VALUE) continue;
                queue.add(new int[]{xx,yy});
                isVisit[xx][yy]=true;
            }
        }
        return queue.size()+obstacleNumber==MAP_SIZE*MAP_SIZE;
    }
}
