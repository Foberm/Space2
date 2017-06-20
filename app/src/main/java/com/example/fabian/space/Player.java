package com.example.fabian.space;

import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by Fabian on 14.04.2017.
 */

public class Player {

    public static Rect bounding = new Rect(550,1600,650,1700);
    public static Rect bounding2 = new Rect(500,1700,700,1800);

    int lives = 3;
    int gold = 0;
    int shotDmg = 1;
    int rapid_fire_duration = 2;
    int rapid_fire_speed = 1;
    int schrot_duration = 4;
    int schrot_bullets = 2;

    Player(){

    }

    boolean intersects(Rect r){
        return r.intersect(bounding) || r.intersect(bounding2);
    }

    void offset(int x, int y){
        bounding.offset(x, y);
        bounding2.offset(x, y);
    }
}
