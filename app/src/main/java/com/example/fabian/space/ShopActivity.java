package com.example.fabian.space;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.lang.reflect.Method;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.logging.Logger;

import static android.R.attr.data;
import static android.R.attr.drawable;

public class ShopActivity extends AppCompatActivity {

    TableLayout table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        table = (TableLayout) findViewById(R.id.table);

        newEntry(R.drawable.heart, "+1 Lives at the beginning of the Game \n currently: 5");
        newEntry(R.drawable.gold, "+1 Gold for each Coin collected \n currently: 1");
        newEntry(R.drawable.shot, "Shots deal one additional damage");
        newEntry(R.drawable.speed_small, "+2 seconds duration for rapid fire \n currently: 4s");
        newEntry(R.drawable.schrot_small, "+1 additional bullet per shot \n currently: 2");
        newEntry(R.drawable.schrot_small, "+2 seconds duration \n currently: 4s");
    }




    void newEntry(int drawable, String content){
        TableRow row = new TableRow(this);
        LinearLayout line = new LinearLayout(this);
        ImageButton img = new ImageButton(this);
        img.setImageDrawable(getResources().getDrawable(drawable, getTheme()));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(250, 250);
        img.setLayoutParams(params);
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        img.setBackgroundColor(Color.TRANSPARENT);

        TextView text = new TextView(this);
        text.setText(content);
        text.setTextColor(Color.WHITE);
        text.setHeight(250);
        text.setPadding(25, 25, 25, 25);

        line.addView(img);
        line.addView(text);
        row.addView(line);
        table.addView(row);
    }
}