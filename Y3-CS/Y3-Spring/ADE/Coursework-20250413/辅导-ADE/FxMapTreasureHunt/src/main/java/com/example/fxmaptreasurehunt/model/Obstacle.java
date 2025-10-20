package com.example.fxmaptreasurehunt.model;

import com.example.fxmaptreasurehunt.util.ResourceManagerUtil;

public class Obstacle extends Item{
    public Obstacle(int x,int y){
        super(x,y,ResourceManagerUtil.loadInitImage(), ResourceManagerUtil.loadObstacleImage());
    }
}
