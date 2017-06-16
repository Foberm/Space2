package com.example.fabian.space;

import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;

/**
 * Created by Fabian on 06.06.2017.
 */

public class Laser {
    RectF bounding;
    int grow = 19;
    int fire = 60;
    int x, y;
    Point to;
    double alpha;
    Boss boss;
    Laser(int x, Boss boss){
        this.x = x;
        this.boss = boss;
        y = 250;
        bounding = new RectF(x-5, 250-5, x+5, 250+5);
    }
    void update(){
        if(grow>0){
            grow--;
            bounding.offset(-5, -5);
            bounding.right+=10;
            bounding.bottom+=10;
            if(grow ==0){
                Point cur = new Point(x, y);
                to = new Point(Player.bounding.centerX(), Player.bounding.bottom);
                alpha = -1*90+Math.toDegrees(Math.atan((to.y-cur.y)/(to.x-cur.x)));
                if(alpha <-90)alpha+=180;
                Log.d("a", alpha+" "+to.x+" - "+cur.x+" : "+to.y+" - "+cur.y);
            }
        }else if(fire >0){
            fire--;
            //bounding.bottom+=(int)(40.0*Math.sin(alpha));
            //bounding.right+=(int)(40.0*Math.cos(alpha));
            bounding.bottom += 40;
            if(fire ==0){
                boss.endLaser();
            }
        }
    }
}
