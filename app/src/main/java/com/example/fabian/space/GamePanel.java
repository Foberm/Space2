package com.example.fabian.space;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.util.ArrayMap;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;



public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
    public MainThread thread;
    MainActivity context;
    public GamePanel(MainActivity context){
        super(context);
        this.context = context;
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        setFocusable(true);

        init(getContext());
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        while (true){
            try{
                thread.setRunning(false);
                thread.join();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }



    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMeasuredRect = new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }
    //------------------------------------~~~~lol~~~~-------------------------------------\\
    Player player = new Player();
    public static Rect mMeasuredRect;
    Bitmap back, dino, l, r, top, enemy, shot, shote, blood;

    float frameTime = 1;

    int speed = 20 ,score = 0, lives = 5, numberOfEnemies = 2, splitChance = 10;
    int scoreForBoss = 200;

    final int frames_shot = 10;
    float frames_until_shot = frames_shot;
    final int frames_obstacle = 40;
    float frames_until_obstacle = frames_obstacle;
    final int frames_enemy = 100;
    float frames_until_enemy = frames_enemy;
    final int frames_enemy_shot = 90;

    final int frames_dropship = 1800;
    float frames_until_dropship = frames_dropship;

    Map<Enemy, Integer> frames_until_enemy_shot = new ArrayMap<>();
    int gold = 0;
    int machine_gun_frames = 0;
    final int frames_bloodflash = 15;
    int frames_bloodflash_left = 0;
    boolean defeated = false;
    Paint paint, paint2, paint3, paint4, paint5;
    List<Shot> shots = new ArrayList<>();
    List<Enemy> enemies = new ArrayList<>();
    List<EnemyShot> enemyshot = new ArrayList<>();
    List<Obstacle> obstacles = new ArrayList<>();
    List<Item> items = new ArrayList<>();
    Movie sb;
    Boss boss;

    int shotgun_shots_left = 0;
    boolean spawn = true;
    boolean left, right = false, start = true;

    MediaPlayer shotSound;
    public static Bitmap[] imageSequenz =  new Bitmap[50];
    public static Bitmap[] imageSequenzKlein =  new Bitmap[50];
    public static Bitmap[] imageSequenz2 =  new Bitmap[50];
    public static Bitmap[] imageSequenzKlein2 =  new Bitmap[50];

    Dropship dropship;
    int chanceForItem = 450;
    private void init(final Context ct) {
        back = BitmapFactory.decodeResource(ct.getResources(), R.drawable.back2);
        dino = BitmapFactory.decodeResource(ct.getResources(), R.drawable.top);
        top = BitmapFactory.decodeResource(ct.getResources(), R.drawable.top);
        l = BitmapFactory.decodeResource(ct.getResources(), R.drawable.left);
        r = BitmapFactory.decodeResource(ct.getResources(), R.drawable.right);
        enemy = BitmapFactory.decodeResource(ct.getResources(), R.drawable.ufo);
        shot = BitmapFactory.decodeResource(ct.getResources(), R.drawable.lvl1);
        shote = BitmapFactory.decodeResource(ct.getResources(), R.drawable.enemyshot);
        blood = BitmapFactory.decodeResource(ct.getResources(), R.drawable.damaged2);
        shotSound  = MediaPlayer.create(context, R.raw.laser2);


        for(int i=1;i<=50;i++){
            int id=getResources().getIdentifier("blue00"+i, "raw", context.getPackageName());
            imageSequenz[i-1] = BitmapFactory.decodeResource(ct.getResources(), id);
        }

        for(int i=0;i<50;i++){
            imageSequenzKlein[i] =  Bitmap.createScaledBitmap(
                    imageSequenz[i], 50, 50, false);
        }

        for(int i=1;i<=50;i++){
            int id=getResources().getIdentifier("cyan00"+i, "raw", context.getPackageName());
            imageSequenz2[i-1] = BitmapFactory.decodeResource(ct.getResources(), id);
        }

        for(int i=0;i<50;i++){
            imageSequenzKlein2[i] =  Bitmap.createScaledBitmap(
                    imageSequenz2[i], 50, 50, false);
        }

        paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint2 = new Paint();
        paint2.setColor(Color.BLUE);
        paint3 = new Paint();
        paint3.setColor(Color.WHITE);
        paint3.setStyle(Paint.Style.FILL);
        paint3.setTextSize(30);
        paint4 = new Paint();
        paint4.setColor(Color.RED);
        paint5 = new Paint();
        paint5.setColor(Color.WHITE);

        doFileStuff();
    }

    int highscore =0;
    void doFileStuff(){
        String ret = "";

        File path2 = Environment.getExternalStorageDirectory();
        File absolutePath2 = new File(path2.getAbsolutePath() + "/");
        File file = new File(absolutePath2, "score.txt");
        FileInputStream fis;

        try {
            fis = new FileInputStream(file);
            int content;
            while ((content = fis.read()) != -1) {
                ret+=((char) content);
            }
            String score_str, gold_str;
            if(ret.indexOf('G')!=-1){
                score_str = ret.substring(0, ret.indexOf('G'));
                gold_str = ret.substring(ret.indexOf('G')+1);
                gold = Integer.parseInt(gold_str);
            }else{
                score_str = ret.substring(0);
                if(score_str.length()==0)score_str="0";
                gold = 0;
            }

            highscore = Integer.parseInt(score_str);

        }
        catch(Exception e)
        {
            Log.e("o", e.getMessage());
        }
    }


    void spawnStuff(){
        frames_until_shot-=frameTime;
        if(machine_gun_frames>0){
            frames_until_shot-=2*frameTime;
            machine_gun_frames-=frameTime;
        }
        if(frames_until_shot<=0){
            frames_until_shot = frames_shot;
            shots.add(new Shot(shotSound));

            if(shotgun_shots_left > 0){
                shots.add(new Shot(-30));
                shots.add(new Shot(30));
                shotgun_shots_left--;
            }
        }

        if(spawn) {
            frames_until_obstacle-=frameTime;
            if (frames_until_obstacle <= 0) {
                frames_until_obstacle = frames_obstacle;
                if(new Random().nextBoolean())obstacles.add(new Obstacle(true));
                else obstacles.add(new Obstacle(false));
            }


            frames_until_enemy-=frameTime;
            if (frames_until_enemy <= 0) {
                frames_until_enemy = frames_enemy;
                if (enemies.size() < numberOfEnemies) {
                    enemies.add(new Enemy(enemies));
                    frames_until_enemy_shot.put(enemies.get(enemies.size() - 1), frames_enemy_shot);
                }
            }

            frames_until_dropship-=frameTime;
            if(frames_until_dropship >=0)dropship = new Dropship(this);

        }

        for (Map.Entry<Enemy, Integer> m : frames_until_enemy_shot.entrySet()) {
            m.setValue(m.getValue() - 1);
            if (m.getValue() <= 0) {
                enemyshot.add(new EnemyShot(m.getKey().bounding));
                m.setValue(frames_enemy_shot);
            }
        }

        if(new Random().nextInt(chanceForItem) == 0){
            items.add(new Item(this));
        }


    }

    void moveStuff(){
        if(right) {
            player.offset((int)(speed*frameTime), 0);
            if(player.bounding2.right > mMeasuredRect.width())player.offset(mMeasuredRect.width()-player.bounding2.right, 0);
        }
        if(left){
            player.offset(-(int)(speed*frameTime), 0);
            if(player.bounding2.left < 0)player.offset(-player.bounding2.left, 0);
        }


        for( Obstacle o : obstacles){
            o.move((int)(5*frameTime));
        }
        for( Item i : items){
            i.bounding.offset(0, (int)(20*frameTime));
        }

        if(dropship!=null){
            dropship.update();
            if(dropship.bounding.left>=1080)dropship=null;
        }

        for(Enemy e : enemies){
           if(e.bounding.centerX() < e.moveTo){
                e.bounding.offset((int)(10*frameTime), 0);
               if(new Rect(e.moveTo, e.bounding.centerY(), e.moveTo+1, e.bounding.centerY()+1).intersect(e.bounding)){
                   e.moveTo = 80;
               }
               for(Enemy e2 : enemies){
                   if(!e.bounding.equals(e2.bounding)){
                       if(e.bounding.right < e2.bounding.right && e.bounding.right > e2.bounding.left) {
                           e.moveTo = 80;
                           e2.moveTo = 1000;
                           e.bounding.offset(-(int)(20*frameTime), 0);
                       }
                   }
               }

           } else{
                e.bounding.offset(-(int)(10*frameTime), 0);
               if(new Rect(e.moveTo, e.bounding.centerY(), e.moveTo+1, e.bounding.centerY()+1).intersect(e.bounding)){
                   e.moveTo = 1000;
               }
               for(Enemy e2 : enemies){
                   if(!e.bounding.equals(e2.bounding)){
                       if(e.bounding.left > e2.bounding.left && e.bounding.left < e2.bounding.right) {
                           e.moveTo = 1000;
                           e.bounding.offset((int)(20*frameTime), 0);
                           e2.moveTo = 80;
                       }
                   }
               }
            }

        }
        int size = obstacles.size();
        for(int i=0;i<shots.size();i++){
            Shot s = shots.get(i);
            Rect shot = new Rect(s.bounding.left, 0, s.bounding.right, s.bounding.bottom);
            s.move(-(int)(20*frameTime));
            if(machine_gun_frames>0)s.bounding.offset(0, -(int)(20*frameTime));
            shot.top = s.bounding.top;
            for(int j = 0; j<size; j++){
                Rect o  =  new Rect(obstacles.get(j).bounding.left, obstacles.get(j).bounding.top, obstacles.get(j).bounding.right, obstacles.get(j).bounding.bottom);
                if(shot.intersect(obstacles.get(j).bounding)){
                    s.bounding.bottom = -20;
                    s.bounding.top = -30;
                    if(new Random().nextInt(splitChance+1) % splitChance == 0 && obstacles.get(j).alpha == 0){
                        if(new Random().nextBoolean())obstacles.add(new Obstacle(10, o, false));
                        else obstacles.add(new Obstacle( 10, o, true));
                        if(new Random().nextBoolean())obstacles.add(new Obstacle(-10, o, false));
                        else obstacles.add(new Obstacle(-10, o ,true));
                    }
                    obstacles.get(j).bounding.bottom = 3500;
                    obstacles.get(j).bounding.top = 3400;
                }
            }
            for(Enemy e : enemies){
                if(shot.intersect(e.bounding)){
                    s.bounding.bottom = -20;
                    s.bounding.top = -30;
                    e.live--;
                    if(e.live<=0) {
                        e.bounding.bottom = 3500;
                        e.bounding.top = 3400;
                    }
                }
            }
            if(boss!=null){
                if(shot.intersect(boss.bounding)){
                    s.bounding.bottom = -20;
                    s.bounding.top = -30;
                    boss.lives--;
                }
            }
            if(dropship!=null){
                if(shot.intersect(dropship.bounding)){
                    s.bounding.bottom = -20;
                    s.bounding.top = -30;
                    dropship.lives--;
                    if(dropship.lives<=0){
                        dropship=null;
                        addScore(20);
                    }
                }
            }
        }

        for( EnemyShot es : enemyshot){
            es.move();
            if(player.intersects(es.bounding)){
                minusLive();
                es.bounding.offset(0, 2000);
            }
        }
    }

    boolean nuke=false;

    void deleteStuff(){
        for(int i = 0; i < shots.size(); i++){
            if(shots.get(i).bounding.bottom<=0){
                shots.remove(i);
                i--;
            }
        }

        for(int i = 0; i < items.size(); i++){
            if(items.get(i).bounding.bottom<=0){
                items.remove(i);
                i--;
            }
            if(player.intersects(items.get(i).bounding)){
                items.get(i).onCollect();
                items.remove(i);
                i--;
            }
        }

        for(int i = 0; i < obstacles.size(); i++){
            if(player.intersects(obstacles.get(i).bounding)){
                minusLive();
                obstacles.remove(i);
                i-- ;
                addScore(0);
            }
            else if(obstacles.get(i).bounding.bottom>=3000){
                obstacles.remove(i);
                addScore(1);
                i--;
            }else if(obstacles.get(i).bounding.top>=player.bounding2.top){
                obstacles.remove(i);
                addScore(0);
                i--;
            }
        }

        for(int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).bounding.bottom >= 3000) {
                enemies.remove(i);
                addScore(10);
                i--;
            }
        }

        for(int i = 0; i < enemyshot.size(); i++) {
            if (enemyshot.get(i).bounding.top>=player.bounding2.top) {
                enemyshot.remove(i);
                addScore(0);
                i--;
            }
        }

    }

    public void update(){
        if(!start) {

            spawnStuff();
            moveStuff();
            deleteStuff();

            if(boss!=null && boss.lives>0)boss.update();
        }
    }

    void addScore(int plus){
        score+=plus;
        if (score>= 100)numberOfEnemies=3;
        if(score >= scoreForBoss){
            spawn = false;
            if(obstacles.size() == 0 && enemies.size() == 0 && enemyshot.size() ==0 && boss == null){
                boss = new Boss(this);
            }
        }
    }

    void minusLive() {
        lives--;
        frames_bloodflash_left = frames_bloodflash;
        if(lives <=0){

            try {

                File path = Environment.getExternalStorageDirectory();
                File absolutePath = new File(path.getAbsolutePath() + "/");
                File outputFile = new File(absolutePath, "score.txt");

                FileOutputStream fOut = new FileOutputStream(outputFile, false);
                OutputStreamWriter osw = new OutputStreamWriter(fOut);
                String str="";
                if(score > highscore)highscore = score;
                str+=highscore+"G"+gold;
                Log.d("a", str);
                osw.write(str);
                osw.flush();
                fOut.getFD().sync();
                osw.close();

                MediaScannerConnection.scanFile(context, new String[]{outputFile.getAbsolutePath()}, null, null);

            }
            catch(Exception e)
            {
                Log.e("o", e.getMessage());
            }


            player = new Player();
            obstacles = new ArrayList<>();
            enemies = new ArrayList<>();
            enemyshot = new ArrayList<>();
            shots = new ArrayList<>();
            items = new ArrayList<>();

            score = 0;
            lives = 5;
            numberOfEnemies = 2;
            start = spawn = true;
            defeated = false;
            boss = null;
            scoreForBoss = 100;
            frameTime=1;
            frames_until_enemy_shot = new ArrayMap<>();
            frames_until_enemy = frames_enemy;
            frames_until_shot = frames_shot;
            frames_until_obstacle = frames_obstacle;
            frames_bloodflash_left = 0;
            machine_gun_frames = 0;
            shotgun_shots_left = 0;

        }
    }

    void endBoss(){
        addScore(300);
        items.add(new Item(this, boss.bounding.centerX()));
        boss = null;
        spawn = true;
        defeated = true;
        frameTime +=.3;
        scoreForBoss = score+150;
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
            canvas.drawBitmap(back, null, mMeasuredRect, null);
        if(frames_bloodflash_left>0){
            frames_bloodflash_left--;
            canvas.drawBitmap(blood, null, mMeasuredRect, null);
        }
            canvas.drawBitmap(dino, null, new Rect(player.bounding2.left, player.bounding.top, player.bounding2.right, player.bounding2.bottom), null);

            if (start) {
                canvas.drawText("Press to start or continue", mMeasuredRect.centerX() - 200, mMeasuredRect.centerY(), paint3);

            }

            for (Shot s : shots) {
                canvas.drawBitmap(shot, null, s.bounding, null);
            }
            for (Item i : items) {
                i.draw(canvas);
            }
            for (Obstacle o : obstacles) {
                o.draw(canvas);
            }
            for (Enemy e : enemies) {
                canvas.drawBitmap(enemy, null, e.bounding, null);
            }
            for (EnemyShot es : enemyshot) {
                canvas.drawBitmap(shote, null, es.bounding, null);
            }

            if (boss != null) boss.draw(canvas);

            if(dropship!=null)canvas.drawRect(dropship.bounding, paint2);
            canvas.drawRect(nuke2, paint);
            if(nuke)canvas.drawBitmap(top, null, new Rect(900,500,1000,600), null);



            if (score < highscore)
                canvas.drawText("Highscore: " + highscore + ", Score: " + score + ", Lives: " + lives + ", Gold: " + gold, 20, 20, paint3);
            else
                canvas.drawText("Highscore: " + score + ", Score: " + score + ", Lives: " + lives + ", Gold: " + gold, 20, 20, paint3);

    }

    Rect nuke2 = new Rect(890,490,1010,610);
    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        if (start) {
            if(event.getAction() == android.view.MotionEvent.ACTION_UP) {
                start = false;
                addScore(0);
            }
        } else {

            if (nuke2.contains((int) event.getX(), (int) event.getY())) {

                if(nuke){
                    obstacles = new ArrayList<>();
                    enemies = new ArrayList<>();
                    enemyshot = new ArrayList<>();
                    items = new ArrayList<>();
                    if(boss!=null)boss.lives-=100;
                    nuke = false;
                }
            } else {
                if (event.getAction() == android.view.MotionEvent.ACTION_UP && event.getPointerCount() <= 1) {
                    right = left = false;
                    dino = top;
                }
                if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                    if (event.getX(event.getPointerCount() - 1) < mMeasuredRect.width() / 2) {
                        left = true;
                        right = false;
                        dino = l;
                    } else {
                        right = true;
                        left = false;
                        dino = r;
                    }
                }

            }
        }
        return true;
    }


}