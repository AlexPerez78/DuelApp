package com.example.alexperez.duelapp;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class DiceRoll extends Activity {
    private Random random = new Random();
    private HashMap<Integer, Integer> diceResource = new HashMap<>();
    TextView btn_close;
    int dice_Spinning = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_roll);

        ContextCompat.getDrawable(this,R.drawable.table);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.5));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;



        getWindow().setAttributes(params);

        btn_close = (TextView)findViewById(R.id.btnclose);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Add The Dice References to the Hashmap
        diceResource.put(0, R.drawable.starter);
        diceResource.put(1, R.drawable.one);
        diceResource.put(2, R.drawable.two);
        diceResource.put(3, R.drawable.three);
        diceResource.put(4, R.drawable.four);
        diceResource.put(5, R.drawable.five);
        diceResource.put(6, R.drawable.six);

        final ImageView starter = (ImageView) findViewById(R.id.starter);
        starter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*int diceRolled = (int) (Math.random() * 6) + 1;
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.diceroll);
                mediaPlayer.start();
                starter.setImageResource(diceResource.get(diceRolled));*/
                DiceRoll();
            }
        });
    }

    public void DiceRoll(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                int diceRolled = (int) (Math.random() * 6) + 1;
                ImageView starter = (ImageView)findViewById(R.id.starter);
                //MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.diceroll);

                starter.setEnabled(false);
                //mediaPlayer.start();
                starter.setImageResource(diceResource.get(diceRolled));
                dice_Spinning++;
                //mediaPlayer.release();

                if(dice_Spinning < 10){
                    DiceRoll(); //Recursion to Mimic a Dice Rolling

                }else{
                    starter.setEnabled(true);
                    dice_Spinning = 0; //Reset Number of Rolls
                    StyleableToast.makeText(getApplicationContext(),"You Rolled A " + diceRolled,R.style.Rolling_Flip).show();
                }
            }
            //}
        }, 100);
    }
}
