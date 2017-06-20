package com.example.fabian.space;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.graphics.Rect;
import android.util.Log;

import java.io.InputStream;
import java.util.List;
import java.util.Random;

public class Obstacle {

    public Rect bounding = new Rect(0,150,100,250);
    int alpha = 0;
    float b =0;
    boolean color;
    Obstacle(boolean c)
    {
        color = c;
       bounding.offset(getRandom(), 0);
    }
    Obstacle(List<Obstacle> obstacles, boolean c){
        color = c;
        bounding.offset(getRandom(), 0);
        while(this.intersect(obstacles))bounding.offset(getRandom(), 0);
    }
    Obstacle(int alpha, Rect r, boolean c){
        color = c;
        this.alpha = alpha;
        bounding = new Rect(r);
        bounding.right-=40;
        bounding.top+=40;
        if(alpha>0)bounding.offset(45, 0);
        else bounding.offset(-5, 0);
    }
    Obstacle(int x, int y, boolean c){
        color = c;
        bounding.offset(x, y-150);
    }
    private int getRandom(){
        Random r = new Random();
        return r.nextInt(900)+50;
    }

int start = 0;
    void move(int offset){
        if(alpha !=0){
            bounding.offset(-(int)(offset*Math.sin(alpha)), (int)(-offset*Math.cos(alpha)));
        }else bounding.offset(0, offset);

           b+=0.75;


    }

    private boolean intersect(List<Obstacle> obstacles){
        for(Obstacle o : obstacles){
            if(bounding.intersect(o.bounding)){
                return true;
            }
        }
        return  false;
    }

    void draw(Canvas canvas){
        canvas.drawBitmap(GamePanel.imageSequenz[(int)(b%50)], null, bounding, null);
    }


}
