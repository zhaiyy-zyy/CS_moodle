package com.example.fxmaptreasurehunt.util;

import com.example.fxmaptreasurehunt.model.Item;
import com.example.fxmaptreasurehunt.model.Obstacle;
import com.example.fxmaptreasurehunt.model.Treasure;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

public class PathGenerator {
    private static final int[][] D={{0,1},{0,-1},{1,0},{-1,0}};

    /**
     * generate short path by bfs
     * @param items map
     * @param x startX
     * @param y startY
     * @return short path
     */
    public static List<int[]> getShortPathByBFS(Item[][] items,int x,int y){
        boolean[][] isVisit=new boolean[items.length][items[0].length];
        Map<String,int[]> pathReference=new HashMap<>();
        Queue<int[]> queue=new LinkedBlockingDeque<>();
        queue.offer(new int[]{x,y});
        isVisit[x][y]=true;
        while(!queue.isEmpty()){
            int[] poll=queue.poll();
            for(int[] dd:D){
                int xx=poll[0]+dd[0],yy=poll[1]+dd[1];
                if(xx<0||xx>= items.length||yy<0||yy>=items.length) continue;
                if(items[xx][yy] instanceof Obstacle) continue;
                if(items[xx][yy] instanceof Treasure){
                    List<int[]> path=new LinkedList<>();
                    path.add(0,new int[]{xx,yy});
                    int[] pre=poll;
                    while(pre[0]!=x||pre[1]!=y){
                        path.add(0,pre);
                        pre=pathReference.get(Arrays.toString(pre));
                    }
                    path.add(0,new int[]{x,y});
                    return path;
                }
                if(isVisit[xx][yy]) continue;
                isVisit[xx][yy]=true;
                pathReference.put(Arrays.toString(new int[]{xx,yy}),poll);
                queue.offer(new int[]{xx,yy});

            }
        }
        return null;
    }

    /**
     * generate short path by bfs
     * @param items map
     * @param x startx
     * @param y startY
     * @return short path
     */
    public static List<int[]> getShortPathByAStar(Item[][] items, int x, int y) {
        List<Treasure> treasureList=new ArrayList<>();
        for(int i=0;i< items.length;i++){
            for(int j=0;j< items.length;j++){
                if(items[i][j] instanceof Treasure)
                    treasureList.add((Treasure) items[i][j]);
            }
        }
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        Map<String, int[]> cameFrom = new HashMap<>();
        Map<String, Integer> gScore = new HashMap<>();
        for(Treasure treasure:treasureList) {
            int hScore = manhattan(x, y, treasure);
            openSet.add(new Node(x, y, 0, hScore));
        }
        gScore.put(Arrays.toString(new int[]{x, y}), 0);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            if (items[current.x][current.y] instanceof Treasure) {
                return reconstructPath(cameFrom, current.x, current.y, x, y);
            }
            for (int[] d : D) {
                int xx = current.x + d[0];
                int yy = current.y + d[1];
                if (xx < 0 || xx >= items.length || yy < 0 || yy >= items.length) continue;
                if (items[xx][yy] instanceof Obstacle) continue;
                int tentativeG = current.g + 1;
                String neighborKey = Arrays.toString(new int[]{xx, yy});
                if (tentativeG < gScore.getOrDefault(neighborKey, Integer.MAX_VALUE)) {
                    cameFrom.put(neighborKey, new int[]{current.x, current.y});
                    gScore.put(neighborKey, tentativeG);
                    for(Treasure treasure:treasureList) {
                        int hScore = manhattan(xx, yy, treasure);
                        openSet.add(new Node(xx, yy, tentativeG, hScore));
                    }
                }
            }
        }
        return null;
    }

    /**
     * calculate the manhattan for A*
     * @param x x
     * @param y y
     * @param treasure one of treasure
     * @return manhattan value
     */
    private static int manhattan(int x, int y, Treasure treasure) {
        return Math.abs(x - treasure.getX()/Item.WIDTH) + Math.abs(y - treasure.getY()/Item.WIDTH);
    }

    /**
     * generate the path
     * @param cameFrom map reference
     * @param endX endX
     * @param endY endY
     * @param startX startX
     * @param startY startY
     * @return path
     */
    private static List<int[]> reconstructPath(Map<String, int[]> cameFrom,
                                               int endX, int endY,
                                               int startX, int startY) {
        List<int[]> path = new LinkedList<>();
        int[] current = {endX, endY};
        path.add(current);

        while (current[0] != startX || current[1] != startY) {
            current = cameFrom.get(Arrays.toString(new int[]{current[0], current[1]}));
            path.add(0, current);
        }
        return path;
    }
}
