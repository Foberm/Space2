package com.example.fabian.space;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;

/**
 * Created by Fabian on 06.06.2017.
 */

public class Laser {
    Boss boss;
    Rect bounding;
    Paint line;

    Bitmap laser;
    int frames_until_fire = 15;
    Laser(Boss boss){
        laser = BitmapFactory.decodeResource(boss.gp.context.getResources(), R.drawable.laser);
        this.boss = boss;
        bounding = new Rect(boss.bounding.centerX()-5, boss.bounding.bottom, boss.bounding.centerX()+5, boss.bounding.bottom+5);
        line =new Paint();
        line.setStrokeWidth(20);
        cur = new Point(bounding.centerX(), bounding.centerY());
    }

int offset = 0;
    int frames_until_end = 10;
    int frames_until_lock = 39;
    Point to, cur, left, right;
    double alpha;
    int immune = 0;
    void update(){
        frames_until_lock--;
        if(frames_until_lock<=0){
            frames_until_fire--;
            if(frames_until_fire<=0) {
                frames_until_end--;
                if (frames_until_end == 0) boss.endLaser();

                if (boss.gp.player.intersects(new Rect(to.x-10, to.y, to.x+10, to.y+1)) && immune <=1) {
                    boss.gp.minusLive();
                    immune++;
                }
            }
        }else{
            offset-=7;
            line.setPathEffect(new DashPathEffect(new float[]{10, 20}, offset));
            to = new Point(Player.bounding.centerX(), Player.bounding.bottom);
            alpha = Math.tan(1.0*(to.x-cur.x)/(to.y-cur.y));
            right = new Point(bounding.centerX()+10+(int)(250*Math.sin(alpha)), bounding.centerY()+(int)(250*Math.cos(alpha)));
            left = new Point(bounding.centerX()-10+(int)(250*Math.sin(alpha)), bounding.centerY()+(int)(250*Math.cos(alpha)));

        }
    }

    void draw(Canvas canvas){
        if(frames_until_fire<=0){
            canvas.save();
            canvas.rotate(-(float)Math.toDegrees(alpha), bounding.centerX(), bounding.centerY());
            canvas.drawBitmap(laser, null , new Rect(cur.x-10, bounding.top-50, cur.x+10, 2000), null);
            canvas.restore();
            canvas.drawRect(new Rect(to.x-10, to.y, to.x+10, to.y-20), line);
        }else{
            canvas.drawLine(bounding.centerX(), bounding.centerY(), bounding.centerX()+(int)(250*Math.sin(alpha)), bounding.centerY()+(int)(250*Math.cos(alpha)), line);
       }
    }
}
