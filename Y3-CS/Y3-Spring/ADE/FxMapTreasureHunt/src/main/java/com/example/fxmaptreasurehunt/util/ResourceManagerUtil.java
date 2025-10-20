package com.example.fxmaptreasurehunt.util;

import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;

/**
 * the util of resource manager
 */
public class ResourceManagerUtil {
    private static final String path=ResourceManagerUtil.class.getResource("").toString();
    private static final Image playerImage=loadImage(path+"../images/player.PNG");
    private static final Image obstacleImage=loadImage(path+"../images/obstacle.PNG");
    private static final Image treasureImage=loadImage(path+"../images/Treasure.PNG");
    private static final Image grassImage=loadImage(path+"../images/grassland.PNG");
    private static final Image initImage=loadImage(path+"../images/initImage.jpg");

    /**
     * load the player image
     * @return player image
     */
    public static Image loadPlayerImage(){
        return playerImage;
    }
    /**
     * load the Grassland image
     * @return Grassland image
     */
    public static Image loadGrasslandImage(){
        return grassImage;
    }
    /**
     * load the init image
     * @return init image
     */
    public static Image loadInitImage(){
        return initImage;
    }
    /**
     * load the Obstacle image
     * @return Obstacle image
     */
    public static Image loadObstacleImage(){
        return obstacleImage;
    }
    /**
     * load the Treasure image
     * @return Treasure image
     */
    public static Image loadTreasureImage(){
        return treasureImage;
    }
    /**
     * load image
     * @return path of image
     */
    private static Image loadImage(String path) {
        return new Image(path, -1, -1, true, true, false);
    }
    public static String getBackgroundMp3Path(){
        return path+"../sound/backgroundmusic.mp3";
    }
    public static String getTreasureMp3Path(){
        return path+"../sound/treasuremusic.mp3";
    }
    public static List<Integer> getTop5Score(){
        try {
            Scanner scanner=new Scanner(new File(path.replace("file:/","")+"../files/scores.txt"));
            List<Integer> scores=new ArrayList<>();
            while(scanner.hasNext()){
                scores.add(scanner.nextInt());
            }
            Collections.sort(scores,(a,b)->b-a);
            scanner.close();
            return scores.subList(0,Math.min(5,scores.size()));
        } catch (FileNotFoundException e) {
            return Arrays.asList(1,3);
        }
    }
    public static void addScore(int score){
        try {
            FileWriter fileWriter=new FileWriter( path.replace("file:/","")+"../files/scores.txt",true);
            fileWriter.write(score+"\n");
            fileWriter.flush();
            fileWriter.close();
        }catch (Exception e){
            //throw new RuntimeException(e);
        }
    }
}
