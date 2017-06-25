package com.example.fabian.space;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by Fabian on 07.06.2017.
 */

public class Item {
    Bitmap item;
    GamePanel gp;
    Rect bounding = new Rect(0, 250, 50, 300);
    int r;
    Item(GamePanel gp){
        this.gp = gp;
        bounding.offset(new Random().nextInt(875)+50, 0);
        r = new Random().nextInt(8);
        switch(r){
            case 0:
                item =  BitmapFactory.decodeResource(gp.context.getResources(), R.drawable.heart_small);
                break;
            case 1:
                item =  BitmapFactory.decodeResource(gp.context.getResources(), R.drawable.speed_small);
                break;
            case 2:
                item =  BitmapFactory.decodeResource(gp.context.getResources(), R.drawable.schrot_small);
                break;
            case 3:
                item = BitmapFactory.decodeResource(gp.context.getResources(), R.drawable.nuke);
                break;
            case 4:
                item = BitmapFactory.decodeResource(gp.context.getResources(), R.drawable.flash);
                break;
            case 5:
                item = BitmapFactory.decodeResource(gp.context.getResources(), R.drawable.reflect);
                break;
            default:
                item =  BitmapFactory.decodeResource(gp.context.getResources(), R.drawable.c_coin);
        }
    }

    Item(GamePanel gp, int x){
        this.gp = gp;
        item =  BitmapFactory.decodeResource(gp.context.getResources(), R.drawable.morecoins2);
        r=-1;
        bounding.offset(x, 0);
    }

    void onCollect(){
        switch(r){
            case -1:
                gp.gold+=10;
                break;
            case 0:
                gp.lives++;
                break;
            case 1:
                gp.machine_gun_frames = 150;
                break;
            case 2:
                gp.shotgun_shots_left = 25;
                break;
            case 3:
                gp.nuke = true;
                break;
            case 4:
                gp.flas_shot_left = gp.flash_shots;
            case 5:
                gp.reflect = true;
            default:
                gp.gold++;
                break;
        }
    }

    void draw(Canvas canvas){
        canvas.drawBitmap(item, null, bounding, null);
    }
}