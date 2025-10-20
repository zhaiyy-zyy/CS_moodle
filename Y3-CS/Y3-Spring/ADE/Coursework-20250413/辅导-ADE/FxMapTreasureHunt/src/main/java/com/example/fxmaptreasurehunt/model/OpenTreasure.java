package com.example.fxmaptreasurehunt.model;

import com.example.fxmaptreasurehunt.util.ResourceManagerUtil;

public class OpenTreasure extends Item{
    public OpenTreasure(int x,int y){
        super(x,y, ResourceManagerUtil.loadOpenImage(), ResourceManagerUtil.loadOpenImage());
    }
}
