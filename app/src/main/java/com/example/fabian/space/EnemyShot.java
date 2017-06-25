package com.example.fabian.space;

import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Fabian on 05.06.2017.
 */

public class EnemyShot {
    Rect bounding;
    Point cur, to;
    double alpha;
    boolean reflected=false;
    EnemyShot(Rect r){
        bounding  = new Rect(r.centerX()-15, r.bottom+10, r.centerX()+15, r.bottom+40);
        cur = new Point(bounding.centerX(), bounding.centerY());
        to = new Point(Player.bounding.centerX(), Player.bounding.bottom);
        alpha = Math.tan(1.0*(to.x-cur.x)/(to.y-cur.y));
    }
    void move(){
        if(reflected)bounding.offset(-(int)(15.0*Math.sin(alpha)), -(int)(15.0*Math.cos(alpha)));
        else bounding.offset((int)(15.0*Math.sin(alpha)), (int)(15.0*Math.cos(alpha)));
    }//TODO - * FRAMETIME
}
