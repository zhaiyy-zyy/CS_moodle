package com.example.fxmaptreasurehunt.model;

import com.example.fxmaptreasurehunt.util.ResourceManagerUtil;

public class Treasure extends Item{
    public Treasure(int x,int y){
        super(x,y,ResourceManagerUtil.loadInitImage(), ResourceManagerUtil.loadTreasureImage());
    }
}
