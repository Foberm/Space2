package com.example.fabian.space;

import android.graphics.Rect;

import java.util.Random;

/**
 * Created by Fabian on 19.06.2017.
 */

public class Dropship {
    int lives = 2;
    Rect bounding = new Rect(-200,450,-100, 500);
    GamePanel gp;
    Dropship(GamePanel gp){
        this.gp = gp;
    }
    int frames_until_meteor = 20;
    void update(){
        bounding.offset(5, 0);
        frames_until_meteor--;
        if(frames_until_meteor==0){
            frames_until_meteor = 20;
            if(new Random().nextBoolean())gp.obstacles.add(new Obstacle(bounding.left, bounding.bottom+10, true));
            else gp.obstacles.add(new Obstacle(bounding.left, bounding.bottom+10, false));
        }
    }

}
