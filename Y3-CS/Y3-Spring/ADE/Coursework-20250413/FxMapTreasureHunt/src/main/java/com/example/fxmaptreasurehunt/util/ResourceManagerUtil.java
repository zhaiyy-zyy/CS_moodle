package com.example.fxmaptreasurehunt.util;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * the util of resource manager
 */
public class ResourceManagerUtil {
    private static final String path = ResourceManagerUtil.class.getResource("").toString();
    private static final Image playerImage = loadImage(path + "../images/player.PNG");
    private static final Image magicImage = loadImage(path + "../images/energy.PNG");
    private static final Image obstacleImage = loadImage(path + "../images/obstacle.PNG");
    private static final Image treasureImage = loadImage(path + "../images/Treasure.PNG");
    private static final Image grassImage = loadImage(path + "../images/grassland.PNG");
    private static final Image openImage = loadImage(path + "../images/open.png");
    private static final Image visitImage = loadImage(path + "../images/visit.jpg");

    /**
     * load the player image
     *
     * @return player image
     */
    public static Image loadPlayerImage() {
        return playerImage;
    }

    /**
     * load the energy image
     *
     * @return energy image
     */
    public static Image loadEnergyImage() {
        return magicImage;
    }

    /**
     * load the Visit image
     *
     * @return Visit image
     */
    public static Image loadVisitImage() {
        return visitImage;
    }

    /**
     * load the init image
     *
     * @return init image
     */
    public static Image loadInitImage() {
        return grassImage;
    }

    /**
     * load the init image
     *
     * @return init image
     */
    public static Image loadOpenImage() {
        return openImage;
    }

    /**
     * load the Obstacle image
     *
     * @return Obstacle image
     */
    public static Image loadObstacleImage() {
        return obstacleImage;
    }

    /**
     * load the Treasure image
     *
     * @return Treasure image
     */
    public static Image loadTreasureImage() {
        return treasureImage;
    }

    /**
     * load image
     *
     * @return path of image
     */
    private static Image loadImage(String path) {
        return new Image(path, -1, -1, true, true, false);
    }

    public static String getBackgroundMp3Path() {
        return path + "../sound/backgroundmusic.mp3";
    }

    public static String getTreasureMp3Path() {
        return path + "../sound/treasuremusic.mp3";
    }

    /**
     * Gets the background image based on whether the player has won or lost.
     *
     * @param win true if the player won, false if the player lost
     * @return a BackgroundImage object representing the background
     */
    public static BackgroundImage getBackground(boolean win) {
        Image backgroundImage = new Image(path + "../images/" + (win ? "win" : "fail") + ".jpg");
        return new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, false)
        );
    }

    /**
     * Retrieves the top 5 player scores from the scores file.
     * The scores are sorted in descending order by score, and in case of ties, by treasure count.
     *
     * @return a list of the top 5 player scores, where each score is represented by an array
     *         containing the score and treasure count
     */
    public static List<int[]> getTop5Score() {
        try {
            Scanner scanner = new Scanner(new File("./files/scores.txt"));
            List<int[]> scores = new ArrayList<>();
            while (scanner.hasNext()) {
                scores.add(new int[]{scanner.nextInt(), scanner.nextInt()});
            }
            Collections.sort(scores, (a, b) -> a[0] != b[0] ? b[0] - a[0] : b[1] - a[1]);
            scanner.close();
            return scores.subList(0, Math.min(5, scores.size()));
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Adds a player's score and treasure count to the scores file.
     * The score is written to a file located at "./files/scores.txt".
     *
     * @param score the score to be added
     * @param treasureNumber the number of treasures collected by the player
     */
    public static void addScore(int score, int treasureNumber) {
        try {
            File file = new File("./files/scores.txt");
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter("./files/scores.txt", true);

            fileWriter.write(score + " " + treasureNumber + "\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
