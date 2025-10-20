package com.example.fxmaptreasurehunt.controller;

import com.example.fxmaptreasurehunt.model.*;
import com.example.fxmaptreasurehunt.util.MapGenerator;
import com.example.fxmaptreasurehunt.util.PathGenerator;
import com.example.fxmaptreasurehunt.util.ResourceManagerUtil;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.transform.Rotate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Controller for managing the game logic, including player actions, map generation, and game status updates.
 */
public class GameController implements Initializable {
    // FXML elements linked to UI components

    @FXML
    private GridPane gridPane;
    @FXML
    private Label scoreLabel;
    @FXML
    private Button hintButton;
    @FXML
    private Button restartButton;
    @FXML
    private Label promptLabel;
    @FXML
    private Button magicButton;
    @FXML
    private Label treasureLabel;

    // Game state variables
    private int score; // Current score of the player
    private boolean isAdmin; // Admin mode flag
    private List<Path> adminArrow; // List of arrows for admin
    private Scene scene; // Current scene
    private Scene backScene; // Previous scene
    private int treasureNumber; // Number of treasures found
    private int contactObstacleNumber; // Number of obstacles encountered

    // Media players for background and treasure sounds
    private MediaPlayer backgroundMediaPlayer;
    private MediaPlayer treasureMediaPlayer;

    // Grid dimensions
    private final int WIDTH = 30;
    private final int HEIGHT = 30;
    public static final int ROWS = 20;
    public static final int COLUMNS = 20;

    private Path arrow; // Arrow for hint
    private boolean useMagic; // Flag to use magic bottle
    private Timeline timeline; // Timeline for arrow flashing
    private Timeline timelineHit; // Timeline for prompt blinking
    private Item[][] items; // Game map with items (obstacles, treasures, etc.)
    private Player player; // Player instance
    private Stage thisStage; // Current stage
    private Stage parentStage;  // Parent stage

    /**
     * Initializes the game by setting up the grid, player, and button actions.
     * This method is automatically called after the FXML file is loaded.
     *
     * @param location the location of the FXML file
     * @param resources the resources for the FXML file
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeGrid();
        setupPlayer();

        // Set up hint button action
        hintButton.setOnAction((e) -> {
            if (gameOver()) return;
            hint();// Call hint method to display the hint and update score
            final boolean[] isVisible = {true};
            // Get the shortest path using A* algorithm
            List<int[]> path = PathGenerator.getShortPathByAStar(items, player.getX() / HEIGHT, player.getY() / WIDTH);
            int[] dd = new int[]{path.get(1)[0] - path.get(0)[0], path.get(1)[1] - path.get(0)[1]};
            if (arrow != null) {
                gridPane.getChildren().remove(arrow);
                timeline.stop();
                arrow = null;
            }
            //System.out.println(Arrays.toString(dd));
            arrow = createArrow(dd);
            gridPane.add(arrow, player.getX(), player.getY());
            timeline = new Timeline(
                    new KeyFrame(Duration.seconds(0.5), (ee) -> {
                        isVisible[0] = !isVisible[0];
                        arrow.setFill(isVisible[0] ? Color.YELLOW : Color.TRANSPARENT);
                    })
            );
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        });

        // Set up restart button action
        restartButton.setOnAction((e) -> {
            initializeGrid();
            setupPlayer();
            if (timeline != null) timeline.stop();
            if (timelineHit != null) {
                promptLabel.setTextFill(Color.TRANSPARENT);
                timelineHit.stop();
                timelineHit = null;
            }
            magicButton.setVisible(false); // Hide magic button
        });

        // Set up magic button action
        magicButton.setVisible(false); // Initially hide the magic button
        magicButton.setOnAction((e) -> {
            if (score <= 10) {
                // If score is low, use magic bottle to restore points
                score += 20;
                scoreLabel.setText(" " + score);
            }
            useMagic = true;
            magicButton.setVisible(false); // Hide the magic button after use
        });

        // Set up magic button
        ImageView imageView = new ImageView(ResourceManagerUtil.loadEnergyImage());
        imageView.setFitHeight(15);
        imageView.setFitWidth(15);
        magicButton.setGraphic(imageView);
    }

    /**
     * Creates an arrow path for the hint, indicating the direction to the next tile.
     *
     * @param d the direction to the next tile
     * @return a Path object representing the arrow
     */
    private Path createArrow(int[] d) {
        Path path = new Path();
        path.getElements().addAll(
                new MoveTo(0, 3),   // Starting point, position the arrow start
                new LineTo(15, 3),  // Horizontal line segment, adjust length
                new LineTo(15, 5),  // Downward diagonal segment, adjust length
                new LineTo(25, 0),  // Arrow tip, adjust the width
                new LineTo(15, -5), // Upward diagonal segment, adjust length
                new LineTo(15, -3), // Horizontal line segment, adjust width
                new LineTo(0, -3),  // Line segment going left, adjust width
                new ClosePath()     // Close the path to complete the arrow
        );
        int deg = 0;
        if (d[0] == 0 && d[1] > 0) {
            deg = 90;
        } else if (d[0] == 0 && d[1] < 0) {
            deg = -90;
        } else if (d[0] < 0 && d[1] == 0) {
            deg = 180;
        }
        Rotate rotate = new Rotate(deg, 15, 0);
        path.getTransforms().add(rotate); // Rotate the arrow based on direction
        path.setFill(Color.YELLOW);
        path.setStroke(Color.TRANSPARENT);
        return path;
    }

    /**
     * Checks if the game is over. The game ends if the score is less than or equal to 0 or if the player has collected all treasures.
     *
     * @return true if the game is over, otherwise false
     */
    private boolean gameOver() {
        return score <= 0 || treasureNumber >= 3;
    }

    /**
     * Initializes the game grid by generating the map and placing obstacles, treasures, and paths.
     */
    private void initializeGrid() {
        score = 100; // Set initial score
        adminArrow = new ArrayList<>();
        treasureNumber = 0;
        useMagic = false;
        contactObstacleNumber = 0;
        items = new Item[ROWS][COLUMNS];
        int[][] map = MapGenerator.generateMap();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (map[i][j] == MapGenerator.PATH_VALUE || map[i][j] == MapGenerator.PLAY_VALUE) {
                    items[i][j] = new Grassland(i * HEIGHT, j * WIDTH); // Place grassland
                } else if (map[i][j] == MapGenerator.OBSTACLE_VALUE) {
                    items[i][j] = new Obstacle(i * HEIGHT, j * WIDTH); // Place obstacles
                } else if (map[i][j] == MapGenerator.TREASURE_VALUE) {
                    items[i][j] = new Treasure(i * HEIGHT, j * WIDTH); // Place treasures
                }
                // test
                //if(i==0&&j==3) items[0][3]=new Obstacle(0,3*WIDTH);
                gridPane.add(items[i][j].getImageView(), items[i][j].getX(), items[i][j].getY());
            }
        }

        scoreLabel.setText(" " + score); // Update score display
        treasureLabel.setText(" " + treasureNumber); // Update treasure count display
    }

    /**
     * Sets up the player at the starting position and updates the status of the game.
     */
    private void setupPlayer() {
        player = new Player(0, 0); // Create player at position (0, 0)
        gridPane.add(player.getImageView(), player.getX(), player.getY());
        updateStatus();
        player.contact(items, player.getX() / HEIGHT, player.getY() / WIDTH, gridPane, true); // Handle player's initial interaction with items
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        this.scene.setOnKeyPressed(this::handleKeyPress);
    }

    /**
     * Handles key presses for player movement and action.
     *
     * @param event the key event triggered by the user
     */
    public void handleKeyPress(KeyEvent event) {
        if (gameOver()) return;
        switch (event.getCode()) {
            case UP:
                if (player.getY() > 0) {
                    movePlayer(0, -HEIGHT);
                }else{
                    showBoundaryPopup(); // If the player has reached the upper boundary, pop up a prompt box
                }
                break;
            case DOWN:
                if (player.getY() < (COLUMNS - 1) * HEIGHT) {
                    movePlayer(0, HEIGHT);
                }else{
                    showBoundaryPopup(); // If the player has reached the lower boundary, pop up a prompt box
                }
                break;
            case LEFT:
                if (player.getX() > 0) {
                    movePlayer(-WIDTH, 0);
                }else{
                    showBoundaryPopup(); // If the player has reached the left boundary, pop up a prompt box
                }
                break;
            case RIGHT:
                if (player.getX() < (ROWS - 1) * WIDTH) {
                    movePlayer(WIDTH, 0);
                }else{
                    showBoundaryPopup(); // If the player has reached the right boundary, pop up a prompt box
                }
                break;
            case SPACE:
                if (adminArrow.size() != 0) {
                    for (Path p : adminArrow) {
                        gridPane.getChildren().remove(p);
                    }
                    adminArrow = new ArrayList<>();
                    for (int i = 0; i < items.length; i++) {
                        for (int j = 0; j < items.length; j++) {
                            if (items[i][j] instanceof Treasure) {
                                gridPane.getChildren().remove(items[i][j].getImageView());
                                items[i][j].getImageView().setImage(ResourceManagerUtil.loadInitImage());

                                gridPane.add(items[i][j].getImageView(), items[i][j].getX(), items[i][j].getY());
                            }
                        }
                    }
                    return;
                }
                List<int[]> path = PathGenerator.getShortPathByAStar(items, player.getX() / HEIGHT, player.getY() / WIDTH);
                int[] aa = path.get(0);
                int k = 1;
                int xx = player.getX();
                int yy = player.getY();
                while (k < path.size()) {
                    int[] dd = new int[]{path.get(k)[0] - aa[0], path.get(k)[1] - aa[1]};
                    Path p = createArrow(dd);
                    adminArrow.add(p);
                    aa = path.get(k);
                    xx += dd[0] * WIDTH;
                    yy += dd[1] * HEIGHT;
                    gridPane.add(p, xx, yy);
                    k++;
                }
                for (int i = 0; i < items.length; i++) {
                    for (int j = 0; j < items.length; j++) {
                        if (items[i][j] instanceof Treasure) {
//                            items[i][j].setImageView(new ImageView(ResourceManagerUtil.loadTreasureImage()));
                            gridPane.getChildren().remove(items[i][j].getImageView());
                            items[i][j].getImageView().setImage(ResourceManagerUtil.loadTreasureImage());

                            gridPane.add(items[i][j].getImageView(), items[i][j].getX(), items[i][j].getY());
                        }
                    }
                }
                return;
            //System.out.println(Arrays.toString(dd));

        }
        updateStatus();
        player.contact(items, player.getX() / HEIGHT, player.getY() / WIDTH, gridPane, true);
    }

    /**
     * Display a border prompt box to inform the player that the border has been reached.
     */
    private void showBoundaryPopup() {
        // Create a new Stage as a pop-up window
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);  // Set as a modal window to prevent interaction with other windows
        popupStage.setTitle("Boundary Tips");  // Set the title of the popup window

        // Create a VBox layout and set its content
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER);

        // Create a prompt information Label
        Label messageLabel = new Label("You have reached the edge of the map.\n You cannot move any further!");
        Button closeButton = new Button("ok");

        //Button click event, close the pop-up window when clicked
        closeButton.setOnAction(event -> popupStage.close());

        // Add Label and Button to the layout
        layout.getChildren().addAll(messageLabel, closeButton);

        Scene scene = new Scene(layout, 300, 150);  // Set the size of the popup window
        popupStage.setScene(scene);

        // Show pop up
        popupStage.showAndWait();
    }

    /**
     * Updates the status of the game, including checking for game over and updating visibility of the magic button.
     */
    private void updateStatus() {
        if (gameOver()) {
            if (treasureNumber >= 3) {
                showTop5AndAskDialog(); // Show top 5 ranking dialog
            } else {
                showMessage("You fail. Would you play again?");
            }
            return;
        }
        if (score <= 60) promptHit(); // If score is low, show prompt to warn the player

        if (score <= 10 && !useMagic) magicButton.setVisible(true); // Show magic button if score is low
    }

    /**
     * Decreases the player's score when they ask for a hint and updates the game status.
     */
    private void hint() {
        score -= 3;
        scoreLabel.setText(" " + score);
        updateStatus();
    }

    /**
     * Makes the prompt text blink when the player's score is low.
     */
    public void promptHit() {
        if (timelineHit != null) return;
        boolean[] isVisible = new boolean[]{true};
        timelineHit = new Timeline(
                new KeyFrame(Duration.seconds(0.5), (ee) -> {
                    isVisible[0] = !isVisible[0];
                    promptLabel.setTextFill(isVisible[0] ? Color.BLUE : Color.TRANSPARENT);
                })
        );
        timelineHit.setCycleCount(Timeline.INDEFINITE);
        timelineHit.play();
    }

    /**
     * Moves the player by the specified x and y deltas and handles interactions with obstacles or treasures.
     *
     * @param dx the change in the player's X position
     * @param dy the change in the player's Y position
     */
    private void movePlayer(int dx, int dy) {
        int x = (player.getX() + dx) / HEIGHT, y = (player.getY() + dy) / WIDTH;
        if (items[x][y] instanceof Obstacle) {
            score -= 10;
            player.contact(items, x, y, gridPane, false);
            contactObstacleNumber++;
            scoreLabel.setText(" " + score);
            return;
        }
        if (items[x][y] instanceof Treasure) {
            treasureNumber++;
            score += 20;
            treasureLabel.setText(" " + treasureNumber);
            items[x][y].getImageView().setImage(ResourceManagerUtil.loadOpenImage()); // Change image to open treasure
            items[x][y] = new OpenTreasure(items[x][y].getX(), items[x][y].getY());
        }
        if (arrow != null) {
            gridPane.getChildren().remove(arrow); // Remove arrow after moving
            timeline.stop();
            arrow = null;
        }
        score--;
        scoreLabel.setText(" " + score);
        gridPane.getChildren().remove(player.getImageView());
        player.setX(player.getX() + dx);
        player.setY(player.getY() + dy);
        gridPane.add(player.getImageView(), player.getX(), player.getY());
//        player.contact(items,x,y,gridPane,true);
    }

    /**
     * Displays the top 5 scores and offers options to restart, go back to the welcome screen, or quit.
     */
    public void showTop5AndAskDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Top 5");
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox dialogVBox = new VBox(10);
        dialogVBox.setPadding(new Insets(5));

        Label playerScoreLabel = new Label("Your score: " + score + ", treasure: " + treasureNumber);
        dialogVBox.getChildren().add(playerScoreLabel);

        Label topScoresLabel = new Label("     score   treasure");
        dialogVBox.getChildren().add(topScoresLabel);
        List<int[]> topScores = ResourceManagerUtil.getTop5Score();
        for (int i = 0; i < topScores.size(); i++) {
            dialogVBox.getChildren().add(new Label((i + 1) + ".   " + String.format("%5d", topScores.get(i)[0]) + "   " + String.format("%8d", topScores.get(i)[1])));
        }
        ResourceManagerUtil.addScore(score, treasureNumber);
        Button continueButton = new Button("Restart");
        continueButton.setOnAction(event -> {
            initializeGrid();
            setupPlayer();
            if (timeline != null) timeline.stop();
            if (timelineHit != null) {
                promptLabel.setTextFill(Color.TRANSPARENT);
                timelineHit.stop();
                timelineHit = null;
            }
            magicButton.setVisible(false);
            dialog.close();
        });
        Button backButton = new Button("Home");
        backButton.setOnAction(event -> {
            parentStage.show();
            thisStage.hide();
            dialog.close();
        });
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> {
            System.exit(0);
        });
        VBox buttonBox = new VBox(10);
        buttonBox.getChildren().addAll(continueButton, backButton, exitButton);
        buttonBox.setAlignment(Pos.CENTER);
        dialogVBox.getChildren().add(buttonBox);
        dialogVBox.setAlignment(Pos.CENTER);
        dialogVBox.setBackground(new Background(ResourceManagerUtil.getBackground(true)));
        Scene dialogScene = new Scene(dialogVBox, 350, 300);
        dialog.setScene(dialogScene);

        dialog.showAndWait();
    }

    /**
     * Displays a message dialog with a custom message.
     *
     * @param msg the message to display
     */
    public void showMessage(String msg) {
        Stage dialog = new Stage();
        dialog.setTitle("Message");
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox dialogVBox = new VBox(10);
        dialogVBox.setPadding(new Insets(10));

        Label playerScoreLabel = new Label(msg);
        dialogVBox.getChildren().add(playerScoreLabel);
        dialogVBox.setAlignment(Pos.CENTER);
        Button continueButton = new Button("Restart");
        continueButton.setOnAction(event -> {
            initializeGrid();
            setupPlayer();
            if (timeline != null) timeline.stop();
            if (timelineHit != null) {
                promptLabel.setTextFill(Color.TRANSPARENT);
                timelineHit.stop();
                timelineHit = null;
            }
            magicButton.setVisible(false);
            dialog.close();
        });
        Button backButton = new Button("Home");
        backButton.setOnAction(event -> {
            parentStage.show();
            thisStage.hide();
            dialog.close();
        });
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> {
            System.exit(0);
        });
        VBox buttonBox = new VBox(10);
        buttonBox.getChildren().addAll(continueButton, backButton, exitButton);
        buttonBox.setAlignment(Pos.CENTER);
        dialogVBox.getChildren().add(buttonBox);
        dialogVBox.setBackground(new Background(ResourceManagerUtil.getBackground(false)));
        Scene dialogScene = new Scene(dialogVBox, 300, 150);
        dialog.setScene(dialogScene);

        dialog.showAndWait();
    }

    public void setBackScene(Scene scene, Stage parentStage, Stage thisStage) {
        backScene = scene;
        this.parentStage = parentStage;
        this.thisStage = thisStage;
    }
}
