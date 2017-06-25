package com.example.fabian.space;

import android.graphics.Rect;
import android.media.MediaPlayer;

/**
 * Created by Fabian on 04.06.2017.
 */

public class Shot {
    int alpha = 0;
    public Rect bounding;
    boolean laser = false;
    int draw = 6;
    Shot(){
        bounding = new Rect(Player.bounding.centerX()-15, Player.bounding.top-60, Player.bounding.centerX()+15, Player.bounding.top-10);
    }
    Shot(int offset){
        bounding = new Rect(Player.bounding.centerX()-15+offset, Player.bounding.top-60, Player.bounding.centerX()+15+offset, Player.bounding.top-10);
        alpha = offset>0?-10:10;
    }
    Shot(boolean laser){
        this.laser = laser;
        bounding = new Rect(Player.bounding.centerX()-30, 0, Player.bounding.centerX()+30, Player.bounding.top-10);
    }

    void move(int offset){
        bounding.offset((int)(offset*Math.tan(Math.toRadians(alpha))), offset);
    }
}
