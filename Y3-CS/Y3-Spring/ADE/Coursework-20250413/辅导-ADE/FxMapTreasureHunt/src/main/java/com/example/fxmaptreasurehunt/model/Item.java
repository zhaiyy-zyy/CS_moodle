package com.example.fxmaptreasurehunt.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * the base class for other entiey
 */
public class Item {
    public static final int WIDTH=30;
    public static final int HEIGHT=30;
    private Image image;
    private Image initImage;
    private ImageView imageView;
    private int x;
    private boolean show;
    private int y;
    public Item(){

    }
    public Item(int x,int y,Image initImage,Image image){
        this.x=x;
        this.y=y;
        this.initImage=initImage;
        this.image=image;
        show=false;
        this.imageView=new ImageView(initImage);
        this.imageView.setFitHeight(HEIGHT);
        this.imageView.setFitWidth(WIDTH);
        this.imageView.setLayoutX(x);
        this.imageView.setLayoutY(y);
    }
    public boolean isShow(){
        return show;
    }
    public void setShow(){
        show=true;
    }
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getInitImage() {
        return initImage;
    }

    public void setInitImage(Image initImage) {
        this.initImage = initImage;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
