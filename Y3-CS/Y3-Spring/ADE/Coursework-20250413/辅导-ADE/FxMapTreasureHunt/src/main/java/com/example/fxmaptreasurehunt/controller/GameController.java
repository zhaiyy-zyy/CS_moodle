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

public class GameController implements Initializable {
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
    private Button pauseButton;
    @FXML
    private Button magicButton;
    @FXML
    private Label treasureLabel;
    private int score;
    private boolean isAdmin;
    private List<Path> adminArrow;
    private Scene scene;
    private Scene backScene;
    private int treasureNumber;
    private int contactObstacleNumber;

    private MediaPlayer backgroundMediaPlayer;
    private MediaPlayer treasureMediaPlayer;

    private final int WIDTH=30;
    private final int HEIGHT=30;
    public static final int ROWS=20;
    public static final int COLUMNS=20;
    private Path arrow;
    private boolean useMagic;
    private Timeline timeline;
    private Timeline timelineHit;
    private Item[][] items;
    private Player player;
    private Stage thisStage;
    private Stage parentStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeGrid();
        setupPlayer();
        hintButton.setOnAction((e)->{
            if(gameOver()) return;
            hint();
            final boolean[] isVisible = {true};
            //List<int[]> path= PathGenerator.getShortPathByBFS(items, player.getX()/HEIGHT, player.getY()/WIDTH);
            List<int[]> path= PathGenerator.getShortPathByAStar(items, player.getX()/HEIGHT, player.getY()/WIDTH);
            int[] dd=new int[]{path.get(1)[0]-path.get(0)[0],path.get(1)[1]-path.get(0)[1]};
            if(arrow!=null) {
                gridPane.getChildren().remove(arrow);
                timeline.stop();
                arrow=null;
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
        restartButton.setOnAction((e)->{
            initializeGrid();
            setupPlayer();
            if(timeline!=null) timeline.stop();
            if(timelineHit!=null){
                promptLabel.setTextFill(Color.TRANSPARENT);
                timelineHit.stop();
                timelineHit=null;
            }
            magicButton.setVisible(false);
        });
        pauseButton.setOnAction((e)->{
            if(backgroundMediaPlayer==null) return;
            if(pauseButton.getText().equals("pause")){
                backgroundMediaPlayer.pause();
                pauseButton.setText("resume");
            } else {
                backgroundMediaPlayer.play();
                pauseButton.setText("pause");
            }
        });
        magicButton.setVisible(false);
        magicButton.setOnAction((e)->{
            if(score<=10) {
                score+=20;
                scoreLabel.setText(" "+score);
            }
            useMagic=true;
            magicButton.setVisible(false);
        });
        ImageView imageView=new ImageView(ResourceManagerUtil.loadEnergyImage());
        imageView.setFitHeight(15);
        imageView.setFitWidth(15);
        magicButton.setGraphic(imageView);
    }
    private Path createArrow(int []d) {
        Path path = new Path();
        path.getElements().addAll(
                new MoveTo(0, 5),
                new LineTo(20, 5),
                new LineTo(20, 10),
                new LineTo(29, 0),
                new LineTo(20, -10),
                new LineTo(20, -5),
                new LineTo(0, -5),
                new ClosePath()
        );
        int deg=0;
        if(d[0]==0&&d[1]>0){
            deg=90;
        } else if (d[0]==0&&d[1]<0) {
            deg=-90;
        }else if (d[0]<0&&d[1]==0) {
            deg=180;
        }
        Rotate rotate=new Rotate(deg,15,0);
        path.getTransforms().add(rotate);
        path.setFill(Color.YELLOW);
        path.setStroke(Color.TRANSPARENT);
        return path;
    }
    private boolean gameOver(){
        return score<=0||treasureNumber>=3;
    }
    private void initializeGrid(){
        score=100;
        adminArrow=new ArrayList<>();
        treasureNumber=0;
        useMagic=false;
        contactObstacleNumber=0;
        if(backgroundMediaPlayer==null)
            backgroundMediaPlayer=new MediaPlayer(new Media(ResourceManagerUtil.getBackgroundMp3Path()));
        // loop play
        backgroundMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        backgroundMediaPlayer.play();
        items=new Item[ROWS][COLUMNS];
        int[][] map= MapGenerator.generateMap();
        for(int i=0;i<ROWS;i++){
            for(int j=0;j<COLUMNS;j++){
                if(map[i][j]==MapGenerator.PATH_VALUE||map[i][j]==MapGenerator.PLAY_VALUE){
                    items[i][j]=new Grassland(i*HEIGHT,j*WIDTH);
                } else if (map[i][j]==MapGenerator.OBSTACLE_VALUE) {
                    items[i][j]=new Obstacle(i*HEIGHT,j*WIDTH);
                } else if (map[i][j]==MapGenerator.TREASURE_VALUE) {
                    items[i][j]=new Treasure(i*HEIGHT,j*WIDTH);
                }
                // test
                //if(i==0&&j==3) items[0][3]=new Obstacle(0,3*WIDTH);
                gridPane.add(items[i][j].getImageView(), items[i][j].getX(),items[i][j].getY());
            }
        }

        scoreLabel.setText(" "+score);
        treasureLabel.setText(" "+treasureNumber);
    }

    private void setupPlayer(){
        player=new Player(0,0);
        gridPane.add(player.getImageView(),player.getX(),player.getY());
        updateStatus();
        player.contact(items,player.getX()/HEIGHT,player.getY()/WIDTH,gridPane,true);
    }
    public void setScene(Scene scene){
        this.scene=scene;
        this.scene.setOnKeyPressed(this::handleKeyPress);
    }
    public void handleKeyPress(KeyEvent event) {
        if(gameOver()) return;
        switch (event.getCode()) {
            case UP:
                if (player.getY() > 0) {
                    movePlayer(0, -HEIGHT);
                }
                break;
            case DOWN:
                if (player.getY() < (COLUMNS-1) *HEIGHT) {
                    movePlayer(0, HEIGHT);
                }
                break;
            case LEFT:
                if (player.getX() > 0) {
                    movePlayer(-WIDTH, 0);
                }
                break;
            case RIGHT:
                if (player.getX() < (ROWS-1) *WIDTH) {
                    movePlayer(WIDTH, 0);
                }
                break;
            case SPACE:
                if(adminArrow.size()!=0){
                    for(Path p:adminArrow){
                        gridPane.getChildren().remove(p);
                    }
                    adminArrow=new ArrayList<>();
                    for(int i=0;i<items.length;i++){
                        for(int j=0;j< items.length;j++){
                            if(items[i][j] instanceof Treasure){
                                gridPane.getChildren().remove(items[i][j].getImageView());
                                items[i][j].getImageView().setImage(ResourceManagerUtil.loadInitImage());

                                gridPane.add(items[i][j].getImageView(), items[i][j].getX(),items[i][j].getY());
                            }
                        }
                    }
                    return;
                }
                List<int[]> path= PathGenerator.getShortPathByAStar(items, player.getX()/HEIGHT, player.getY()/WIDTH);
                int[] aa=path.get(0);
                int k=1;
                int xx=player.getX();
                int yy=player.getY();
                while(k<path.size()){
                    int[] dd=new int[]{path.get(k)[0]-aa[0],path.get(k)[1]-aa[1]};
                    Path p=createArrow(dd);
                    adminArrow.add(p);
                    aa=path.get(k);
                    xx+=dd[0]*WIDTH;
                    yy+=dd[1]*HEIGHT;
                    gridPane.add(p,xx,yy);
                    k++;
                }
                for(int i=0;i<items.length;i++){
                    for(int j=0;j< items.length;j++){
                        if(items[i][j] instanceof Treasure){
//                            items[i][j].setImageView(new ImageView(ResourceManagerUtil.loadTreasureImage()));
                            gridPane.getChildren().remove(items[i][j].getImageView());
                            items[i][j].getImageView().setImage(ResourceManagerUtil.loadTreasureImage());

                            gridPane.add(items[i][j].getImageView(), items[i][j].getX(),items[i][j].getY());
                        }
                    }
                }
                return;
                //System.out.println(Arrays.toString(dd));

        }
        updateStatus();
        player.contact(items,player.getX()/HEIGHT,player.getY()/WIDTH,gridPane,true);
    }
    private void updateStatus(){
        if(gameOver()){
            if(treasureNumber>=3){
                showTop5AndAskDialog();
            }else{
                showMessage("You fail. Would you play again?");
            }
            return;
        }
        if(score<=80) promptHit();

        if(score<=10&&!useMagic) magicButton.setVisible(true);
    }
    private void hint(){
        score-=3;
        scoreLabel.setText(" "+score);
        updateStatus();
    }
    public void promptHit(){
        if(timelineHit!=null) return;
        boolean[] isVisible=new boolean[]{true};
        timelineHit = new Timeline(
                new KeyFrame(Duration.seconds(0.5), (ee) -> {
                    isVisible[0] = !isVisible[0];
                    promptLabel.setTextFill(isVisible[0] ? Color.BLUE : Color.TRANSPARENT);
                })
        );
        timelineHit.setCycleCount(Timeline.INDEFINITE);
        timelineHit.play();
    }
    private void movePlayer(int dx, int dy) {
        int x= (player.getX()+dx)/HEIGHT,y=(player.getY()+dy)/WIDTH;
        if(items[x][y] instanceof Obstacle){
            score-=10;
            player.contact(items,x,y,gridPane,false);
            contactObstacleNumber++;
            scoreLabel.setText(" "+score);
            return;
        }
        if(items[x][y] instanceof Treasure){
            treasureNumber++;
            score+=20;
            treasureLabel.setText(" "+treasureNumber);
            backgroundMediaPlayer.pause();
            if(treasureMediaPlayer==null){
                treasureMediaPlayer=new MediaPlayer(new Media(ResourceManagerUtil.getTreasureMp3Path()));
                treasureMediaPlayer.play();
                treasureMediaPlayer.setOnEndOfMedia(()->{
                    treasureMediaPlayer=new MediaPlayer(new Media(ResourceManagerUtil.getTreasureMp3Path()));
                    treasureMediaPlayer.pause();
                    treasureMediaPlayer.dispose();
                    treasureMediaPlayer=null;
                    backgroundMediaPlayer.play();
                });
            }
            items[x][y].getImageView().setImage(ResourceManagerUtil.loadOpenImage());
            items[x][y]=new OpenTreasure(items[x][y].getX(),items[x][y].getY());
        }
        if(arrow!=null){
            gridPane.getChildren().remove(arrow);
            timeline.stop();
            arrow=null;
        }
        score--;
        scoreLabel.setText(" "+score);
        gridPane.getChildren().remove(player.getImageView());
        player.setX(player.getX()+ dx);
        player.setY(player.getY()+ dy);
        gridPane.add(player.getImageView(), player.getX(),player.getY());
//        player.contact(items,x,y,gridPane,true);
    }
    public void showTop5AndAskDialog(){
        Stage dialog = new Stage();
        dialog.setTitle("Top 5");
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox dialogVBox = new VBox(10);
        dialogVBox.setPadding(new Insets(5));

        Label playerScoreLabel = new Label("Your score: " + score+", treasure: "+treasureNumber);
        dialogVBox.getChildren().add(playerScoreLabel);

        Label topScoresLabel = new Label("     score   treasure");
        dialogVBox.getChildren().add(topScoresLabel);
        List<int[]> topScores=ResourceManagerUtil.getTop5Score();
        for (int i = 0; i < topScores.size(); i++) {
            dialogVBox.getChildren().add(new Label((i + 1) + ".   " + String.format("%5d",topScores.get(i)[0])+"   "+String.format("%8d",topScores.get(i)[1])));
        }
        ResourceManagerUtil.addScore(score,treasureNumber);
        Button continueButton = new Button("restart");
        continueButton.setOnAction(event -> {
            initializeGrid();
            setupPlayer();
            if(timeline!=null) timeline.stop();
            if(timelineHit!=null){
                promptLabel.setTextFill(Color.TRANSPARENT);
                timelineHit.stop();
                timelineHit=null;
            }
            magicButton.setVisible(false);
            dialog.close();
        });
        Button cancelButton = new Button("cancel");
        cancelButton.setOnAction(event -> {
            System.exit(0);
        });
        Button backButton = new Button("back to welcome");
        backButton.setOnAction(event -> {
            parentStage.show();
            thisStage.hide();
            backgroundMediaPlayer.pause();
            dialog.close();
        });
        VBox buttonBox = new VBox(10);
        buttonBox.getChildren().addAll(continueButton, cancelButton,backButton);
        buttonBox.setAlignment(Pos.CENTER);
        dialogVBox.getChildren().add(buttonBox);
        dialogVBox.setAlignment(Pos.CENTER);
        dialogVBox.setBackground(new Background(ResourceManagerUtil.getBackground(true)));
        Scene dialogScene = new Scene(dialogVBox, 350, 300);
        dialog.setScene(dialogScene);

        dialog.showAndWait();
    }
    public void showMessage(String msg){
        Stage dialog = new Stage();
        dialog.setTitle("Message");
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox dialogVBox = new VBox(10);
        dialogVBox.setPadding(new Insets(10));

        Label playerScoreLabel = new Label(msg);
        dialogVBox.getChildren().add(playerScoreLabel);
        dialogVBox.setAlignment(Pos.CENTER);
        Button continueButton = new Button("restart");
        continueButton.setOnAction(event -> {
            initializeGrid();
            setupPlayer();
            if(timeline!=null) timeline.stop();
            if(timelineHit!=null){
                promptLabel.setTextFill(Color.TRANSPARENT);
                timelineHit.stop();
                timelineHit=null;
            }
            magicButton.setVisible(false);
            dialog.close();
        });

        Button cancelButton = new Button("cancel");
        cancelButton.setOnAction(event -> {
            System.exit(0);
        });
        Button backButton = new Button("back to welcome");
        backButton.setOnAction(event -> {
            parentStage.show();
            thisStage.hide();
            backgroundMediaPlayer.pause();
            dialog.close();
        });
        VBox buttonBox = new VBox(10);
        buttonBox.getChildren().addAll(continueButton, cancelButton,backButton);
        buttonBox.setAlignment(Pos.CENTER);
        dialogVBox.getChildren().add(buttonBox);
        dialogVBox.setBackground(new Background(ResourceManagerUtil.getBackground(false)));
        Scene dialogScene = new Scene(dialogVBox, 300, 150);
        dialog.setScene(dialogScene);

        dialog.showAndWait();
    }

    public void setBackScene(Scene scene,Stage parentStage,Stage thisStage) {
        backScene=scene;
        this.parentStage=parentStage;
        this.thisStage=thisStage;
    }
}
