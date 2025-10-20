package com.example.fxmaptreasurehunt.model;

import com.example.fxmaptreasurehunt.util.ResourceManagerUtil;
import javafx.scene.layout.GridPane;

public class Player extends Item{
    public Player(int x,int y){
        super(x,y,ResourceManagerUtil.loadPlayerImage(),ResourceManagerUtil.loadPlayerImage());
    }

    public void contact(Item[][] items,int x,int y, GridPane gridPane,boolean movePlay){
        if(x<0||x>= items.length||y<0||y>=items[0].length) return;
        if(items[x][y].isShow()) return;
        Item item=items[x][y];
        int xx=x*HEIGHT,yy=y*WIDTH;
        item.getImageView().setImage(item.getImage());
        item.setShow();
        if(movePlay) {
            gridPane.getChildren().remove(getImageView());
            setX(xx);
            setY(yy);
            gridPane.add(getImageView(), xx, yy);
        }
    }
}
