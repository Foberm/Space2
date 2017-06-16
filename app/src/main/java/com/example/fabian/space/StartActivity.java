package com.example.fabian.space;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {
    public static final int MY_PERMISSONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.click);
        Button btn = (Button)findViewById(R.id.btn_shop);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                Intent myIntent = new Intent(StartActivity.this, ShopActivity.class);
                startActivity(myIntent);
            }
        });
        Button start = (Button)findViewById(R.id.btn_start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                Intent myIntent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(myIntent);
            }
        });

        Log.d("a", "da");
        if (ContextCompat.checkSelfPermission(StartActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("a", "if1");
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(StartActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Log.d("a", "of2");
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.
                Log.d("a", "wlaw");
                ActivityCompat.requestPermissions(StartActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSONS_REQUEST_READ_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

}
