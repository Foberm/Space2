package com.example.fabian.space;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
    GamePanel gp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
          gp = new GamePanel(this);
         setContentView(gp);

    }

    @Override
    public void onBackPressed() {
        // your code.
        Log.d("a", "menu");
        gp.start = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        gp.start = true;
    }

}
