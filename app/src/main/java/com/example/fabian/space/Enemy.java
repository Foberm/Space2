package com.example.fabian.space;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import java.util.List;
import java.util.Random;

/**
 * Created by Fabian on 05.06.2017.
 */

public class Enemy {
    Rect bounding = new Rect(0, 50, 150, 125);
    int moveTo, live =  3;

    Enemy(List<Enemy> enemies){
        int r =getRandom();
        bounding.left = r;
        bounding.right = r+150;
        while(this.intersect(enemies)){
            r = getRandom();
            bounding.left = r;
            bounding.right = r+150;
        }

        moveTo = getRandom()%2 == 0?80:1000;
    }

    Enemy(List<Enemy> enemies, int top){
        int r =getRandom();
        bounding.offset(0, top-50);
        bounding.left = r;
        bounding.right = r+150;
        while(this.intersect(enemies)){
            r = getRandom();
            bounding.left = r;
            bounding.right = r+150;
        }

        moveTo = getRandom()%2 == 0?80:1000;
    }
//git?
    private boolean intersect(List<Enemy> enemies){
        for(Enemy e : enemies){
            if(bounding.intersect(e.bounding)){
                return true;
            }
        }
        return  false;
    }

    private int getRandom(){
        Random r = new Random();
        return r.nextInt(875)+50;
    }


}
