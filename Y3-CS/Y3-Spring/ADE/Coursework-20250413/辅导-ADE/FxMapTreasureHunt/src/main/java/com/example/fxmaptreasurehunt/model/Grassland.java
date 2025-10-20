package com.example.fxmaptreasurehunt.model;

import com.example.fxmaptreasurehunt.util.ResourceManagerUtil;

public class Grassland extends Item{
    public Grassland(int x,int y){
        super(x,y,ResourceManagerUtil.loadInitImage(), ResourceManagerUtil.loadVisitImage());
    }
}
