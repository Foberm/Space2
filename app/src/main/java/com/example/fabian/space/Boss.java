package com.example.fabian.space;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.Random;



 class Boss {
    Rect bounding = new Rect(100, 50, 600, 262);
    GamePanel gp;
    Paint paint =new Paint(), paint2 = new Paint(), paint3=new Paint(), paint4=new Paint();
    Bitmap boss;
    Boss(GamePanel gp){
        boss = BitmapFactory.decodeResource(gp.context.getResources(), R.drawable.boss);
        this.gp = gp;
        paint.setColor(Color.DKGRAY);
        paint2.setColor(Color.BLUE);
        paint3.setColor(Color.RED);
        paint4.setColor(Color.LTGRAY);
        moveTo = new Random().nextInt(550)+250;
        Log.d("a", "a");
    }

    int moveTo;
    int machin_gun_shots = 0;
    int numberOfMeteors = 0;
    int frames_until_meteor = 9;
    final int frames_shot =  85;
    int frames_until_shot = frames_shot;
    final int frames_special = 350;//450
    int frames_until_special = frames_special;
    int lives = 150;
    void update() {
        if(laser==null)if(bounding.centerX()<moveTo)bounding.offset((int)(5), 0);
        else bounding.offset(-(int)(5), 0);

        if(new Rect(moveTo, 100, moveTo+1, 101).intersect(new Rect(bounding.centerX(), 100, bounding.centerX()+10, 101
        ))){
            moveTo = new Random().nextInt(550)+250;
        }



        frames_until_shot-=1;
        if(machin_gun_shots >0)frames_until_shot-=5;
        if (frames_until_shot <= 0) {
            machin_gun_shots--;
            frames_until_shot = frames_shot;
            gp.enemyshot.add(new EnemyShot(bounding));
        }

        frames_until_special-=gp.frameTime;
        if(frames_until_special <= 0 && frames_until_special >-10 && laser==null){
            int r = new Random().nextInt(4);
            switch (r){
                case 0:
                    numberOfMeteors = 15;
                    frames_until_special = frames_special;
                    break;
                case 1:
                    machin_gun_shots = 5;
                    frames_until_special = frames_special;
                    break;
                case 2:
                    if(gp.enemies.size()< gp.numberOfEnemies){
                        gp.enemies.add(new Enemy(gp.enemies, bounding.bottom+20));
                        gp.frames_until_enemy_shot.put(gp.enemies.get(gp.enemies.size() - 1), gp.frames_enemy_shot);
                    }
                    if(gp.enemies.size()< gp.numberOfEnemies){
                        gp.enemies.add(new Enemy(gp.enemies, bounding.bottom+20));
                        gp.frames_until_enemy_shot.put(gp.enemies.get(gp.enemies.size() - 1), gp.frames_enemy_shot);
                    }
                    frames_until_special = frames_special;
                    break;
                case 3:
                    laser = new Laser(this);
                    frames_until_special-=10;
                    break;
            }
        }

        if(laser!=null) laser.update();

        if(numberOfMeteors > 0){
            frames_until_meteor-=gp.frameTime;
            if(frames_until_meteor<=0){
                numberOfMeteors--;
                frames_until_meteor = 10;
                if(new Random().nextBoolean())gp.obstacles.add(new Obstacle(gp.obstacles, true));
                else gp.obstacles.add(new Obstacle(gp.obstacles, false));
            }
        }
    }


    Laser laser;
    long _start_time=0;
    void draw(Canvas canvas){
        if(lives >0) {
            canvas.drawRect(new Rect(200, 10, 350, 40), paint4);
            canvas.drawRect(new Rect(200, 10, 200 + lives, 40), paint3);
            canvas.drawBitmap(boss, null, bounding, null);
            if (laser != null) laser.draw(canvas);
        }else{
            long _current_time = android.os.SystemClock.uptimeMillis();
            if (0 == _start_time) {
                _start_time = _current_time;
            }
            if (null != gp.sb) {
                Log.d("a", "da");
                final int _relatif_time = (int) ((_current_time - _start_time));
                if(_relatif_time > gp.sb.duration())gp.endBoss();
                else gp.sb.setTime(_relatif_time);
                gp.sb.draw(canvas, bounding.left, bounding.top );
            }
        }
    }

    void endLaser(){
        frames_until_special = frames_special;
        laser = null;
    }

}
