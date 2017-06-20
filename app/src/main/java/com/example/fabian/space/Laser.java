package com.example.fabian.space;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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
    int width = 100;
    Bitmap laser;
    int frames_until_fire = 23;
    Laser(Boss boss){
        laser = BitmapFactory.decodeResource(boss.gp.context.getResources(), R.drawable.laser2);
        this.boss = boss;
        bounding = new Rect(boss.bounding.centerX()-5, boss.bounding.bottom, boss.bounding.centerX()+5, boss.bounding.bottom+5);
        line =new Paint();
        line.setStrokeWidth(width);
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
            line.setColor(Color.RED);
            frames_until_fire--;
            if(frames_until_fire<=0) {
                frames_until_end--;
                if (frames_until_end == 0) boss.endLaser();

                if (boss.gp.player.intersects(new Rect(to.x-width/2, to.y, to.x+width/2, to.y+1)) && immune <=5) {
                    if(immune == 5)boss.gp.minusLive();
                    immune++;
                }
            }
        }else{
            offset-=7;
            line.setPathEffect(new DashPathEffect(new float[]{10, 20}, offset));
            to = new Point(Player.bounding.centerX(), Player.bounding.bottom);
            alpha = Math.tan(1.0*(to.x-cur.x)/(to.y-cur.y));
            right = new Point(bounding.centerX()+width/2+(int)(250*Math.sin(alpha)), bounding.centerY()+(int)(250*Math.cos(alpha)));
            left = new Point(bounding.centerX()-width/2+(int)(250*Math.sin(alpha)), bounding.centerY()+(int)(250*Math.cos(alpha)));

        }
    }

    void draw(Canvas canvas){
        if(frames_until_fire<=0){
            canvas.save();
            canvas.rotate(-(float)Math.toDegrees(alpha), bounding.centerX(), bounding.centerY());
            canvas.drawBitmap(laser, null , new Rect(cur.x-width/2, bounding.top-50, cur.x+width/2, 2000), null);
            canvas.restore();
        }else{
            canvas.drawLine(bounding.centerX(), bounding.centerY(), bounding.centerX()+(int)(250*Math.sin(alpha)), bounding.centerY()+(int)(250*Math.cos(alpha)), line);
       }
    }
}