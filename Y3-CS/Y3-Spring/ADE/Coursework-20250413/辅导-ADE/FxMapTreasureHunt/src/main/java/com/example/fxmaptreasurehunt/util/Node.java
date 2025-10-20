package com.example.fxmaptreasurehunt.util;

public class Node {
    int x;
    int y;
    int g;
    int f;  // f = g + h

    Node(int x, int y, int g, int h) {
        this.x = x;
        this.y = y;
        this.g = g;
        this.f = g + h;
    }
}