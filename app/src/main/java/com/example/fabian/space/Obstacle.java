package com.example.fabian.space;

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
    Movie m ;
    Obstacle(Movie m){
       bounding.offset(getRandom(), 0);
        this.m = m;
    }
    Obstacle(List<Obstacle> obstacles, Movie m){
        this.m =m;
        bounding.offset(getRandom(), 0);
        while(this.intersect(obstacles))bounding.offset(getRandom(), 0);
    }
    Obstacle(int alpha, Rect r, Movie m){
        this.m =m;
        this.alpha = alpha;
        bounding = new Rect(r);
        bounding.right-=40;
        bounding.top+=40;
        if(alpha>0)bounding.offset(45, 0);
        else bounding.offset(-5, 0);
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
        long _current_time = android.os.SystemClock.uptimeMillis();
        if (0 == _start_time) {
            _start_time = _current_time;
        }
        if (null != m) {

            //final int _relatif_time = (int) ((_current_time - _start_time) % m.duration());
            //m.setTime(_relatif_time);
            m.setTime(start%m.duration());
            start+=80;
        }
    }

    private boolean intersect(List<Obstacle> obstacles){
        for(Obstacle o : obstacles){
            if(bounding.intersect(o.bounding)){
                return true;
            }
        }
        return  false;
    }

    private long _start_time;
    void draw(Canvas canvas){
            m.draw(canvas, bounding.left, bounding.top);

    }


}
