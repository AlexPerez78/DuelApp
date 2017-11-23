package com.example.alexperez.duelapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.Random;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupBottomNavigationView();

        NavigationView mNavigationView = (NavigationView) findViewById(R.id.nav_menu);
            mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch(item.getItemId()){
                        case(R.id.nav_home):
                            Intent homeActivity = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(homeActivity);
                            break;

                        case(R.id.nav_account):
                            Intent accountActivity = new Intent(getApplicationContext(),MyAccount.class);
                            startActivity(accountActivity);
                            break;

                        case(R.id.nav_banlist):
                            Intent banlistActivity = new Intent(getApplicationContext(),BanList.class);
                            startActivity(banlistActivity);
                            break;

                        case(R.id.nav_tournament):
                            Intent tournamentActivity = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(tournamentActivity);
                            break;

                        case(R.id.nav_settings):
                            Intent settingsActivity = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(settingsActivity);
                            break;
                    }
                    return true;
                }
            });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    * BottomNavigationView setup
    */
    private void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx)findViewById(R.id.bottomNavigationViewBar);
        bottomNavigationViewEx.enableAnimation(true);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
        enableNavigation(MainActivity.this, bottomNavigationViewEx);
    }

    public static void enableNavigation(final Context context, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.dice:
                        int diceRolled = (int) (Math.random() * 6) + 1;
                        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.diceroll);
                        mediaPlayer.start();
                        Toast.makeText(context, "You Rolled a " + diceRolled , Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.coin:
                        Random randomNum = new Random();
                        int coinFlip = randomNum.nextInt(2);
                        String output;
                        if(coinFlip==0){
                            output = "Heads";
                        }
                        else{
                            output = "Tails";
                        }
                        MediaPlayer mediaPlayerCoin = MediaPlayer.create(context, R.raw.coinflipfx);
                        mediaPlayerCoin.start();
                        Toast.makeText(context, "You Flipped " + output , Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.nightmode:
                        Intent banlistActivity = new Intent(context,BanList.class);
                        context.startActivity(banlistActivity);
                        break;

                    case R.id.reset:
                        // Use the Builder class for convenient dialog construction
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Would you like to start a new game?")
                                .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Toast.makeText(context, "Game Has Been Resetted " , Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // User cancelled the dialog
                                        dialog.cancel();
                                    }
                                });
                        // Create the AlertDialog object and return it
                        AlertDialog alert = builder.create();
                        alert.setTitle("New Game");
                        alert.setIcon(R.drawable.ic_loop_black_24dp);
                        alert.show();
                        break;

                }
                return false;
            }
        });
    }
}
