package com.example.alexperez.duelapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    static ProgressBar progressBar_Player1,progressBar_Player2;
    static TextView healthValue_Player1, healthValue_Player2;

    EditText user_input;
    int flag;
    String command;

    private static int ACTIVITY_NUM = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MediaPlayer life_Point_loss = MediaPlayer.create(getApplicationContext(), R.raw.lifepoint_drop);

        /*-------------------------------------Player Lifepoint Input View-------------------------------------------*/

        final AlertDialog.Builder life_point_calc = new AlertDialog.Builder(this);

        life_point_calc.setTitle("Enter LifePoint Loss/Add");

        user_input = new EditText(getApplicationContext());
        user_input.setInputType(InputType.TYPE_CLASS_NUMBER);
        life_point_calc.setView(user_input);

        life_point_calc.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(flag == 1 ){
                    if(command.equalsIgnoreCase("add")){
                        String txt = user_input.getText().toString();
                        int current_Lifepoints = progressBar_Player1.getProgress() + Integer.parseInt(txt);
                        Toast.makeText(getApplicationContext(),Integer.toString(current_Lifepoints),Toast.LENGTH_SHORT).show();

                        if(current_Lifepoints > progressBar_Player1.getMax()){
                            progressBar_Player1.setMax(current_Lifepoints);
                        }

                        progressBar_Player1.setProgress(current_Lifepoints);
                        healthValue_Player1.setText(Integer.toString(current_Lifepoints));
                        user_input.setText("");
                    }
                    else{
                        life_Point_loss.start();
                        String txt = user_input.getText().toString();
                        int current_Lifepoints = progressBar_Player1.getProgress() - Integer.parseInt(txt);
                        Toast.makeText(getApplicationContext(),Integer.toString(current_Lifepoints),Toast.LENGTH_SHORT).show();

                        progressBar_Player1.setProgress(current_Lifepoints);
                        healthValue_Player1.setText(Integer.toString(current_Lifepoints));
                        user_input.setText("");
                    }

                }
                else{
                    if(command.equalsIgnoreCase("add")){
                        String txt = user_input.getText().toString();
                        int current_Lifepoints = progressBar_Player2.getProgress() + Integer.parseInt(txt);
                        Toast.makeText(getApplicationContext(),Integer.toString(current_Lifepoints),Toast.LENGTH_SHORT).show();

                        if(current_Lifepoints > progressBar_Player2.getMax()){
                            progressBar_Player2.setMax(current_Lifepoints);
                        }

                        progressBar_Player2.setProgress(current_Lifepoints);
                        healthValue_Player2.setText(Integer.toString(current_Lifepoints));
                        user_input.setText("");
                    }
                    else{
                        life_Point_loss.start();
                        String txt = user_input.getText().toString();
                        int current_Lifepoints = progressBar_Player2.getProgress() - Integer.parseInt(txt);
                        Toast.makeText(getApplicationContext(),Integer.toString(current_Lifepoints),Toast.LENGTH_SHORT).show();

                        progressBar_Player2.setProgress(current_Lifepoints);
                        healthValue_Player2.setText(Integer.toString(current_Lifepoints));
                        user_input.setText("");
                    }

                }
            }
        });
        life_point_calc.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        final AlertDialog ad = life_point_calc.create();

        /*Handling Key Action event to Dismiss a Dialog*/
        ad.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        user_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int action_id, KeyEvent keyEvent) {
                if(action_id == EditorInfo.IME_ACTION_DONE){
                    Toast.makeText(getApplication(),"Press Submit",Toast.LENGTH_SHORT).show();
                    return true;
                }else{
                    return false;
                }
            }
        });
        /*-----------------------------------------------------------------------------------------------*/

        /*-------------------------------------Player 1 Controls-------------------------------------------*/
        progressBar_Player1 = (ProgressBar) findViewById(R.id.progressBar_PLayer1);
        progressBar_Player1.setMax(8000);
        progressBar_Player1.setProgress(8000);

        healthValue_Player1 = (TextView) findViewById(R.id.healthpoint_Value_Player1);
        healthValue_Player1.setText(Integer.toString(progressBar_Player1.getProgress()));

        final Button player1_Add = (Button)findViewById(R.id.add_LP_Player1);
        player1_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 1;
                command = "add";
                ad.show();

            }
        });

        Button player1_Sub = (Button)findViewById(R.id.sub_LP_Player1);
        player1_Sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 1;
                command = "subtract";
                ad.show();
            }
        });
    /*-------------------------------------Player 2 Controls-------------------------------------------*/
        progressBar_Player2 = (ProgressBar) findViewById(R.id.progressBar_PLayer2);
        progressBar_Player2.setMax(8000);
        progressBar_Player2.setProgress(8000);

        healthValue_Player2 = (TextView) findViewById(R.id.healthpoint_Value_Player2);
        healthValue_Player2.setText(Integer.toString(progressBar_Player2.getProgress()));

        Button player2_Add = (Button)findViewById(R.id.add_LP_Player2);
        player2_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 2;
                command = "add";
                ad.show();
            }
        });

        Button player2_Sub = (Button)findViewById(R.id.sub_LP_Player2);
        player2_Sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 2;
                command = "subtract";
                ad.show();
            }
        });



        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToggle.syncState();

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
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
        enableNavigation(MainActivity.this, bottomNavigationViewEx);
//        Menu menu = bottomNavigationViewEx.getMenu();
//        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
//        menuItem.setChecked(true);
    }

    public static void enableNavigation(final Context context, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.dice:
                        ACTIVITY_NUM = 0;
                        Intent diceActivity = new Intent(context, DiceRoll.class);
                        context.startActivity(diceActivity);
                        break;

                    case R.id.coin:
                        ACTIVITY_NUM = 1;
                        Intent flipCoinActivity = new Intent(context, FlipCoin.class);
                        context.startActivity(flipCoinActivity);
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
                                        progressBar_Player1.setProgress(8000); //Reset The Game
                                        healthValue_Player1.setText(Integer.toString(progressBar_Player1.getProgress()));

                                        progressBar_Player2.setProgress(8000); //Reset The Game
                                        healthValue_Player2.setText(Integer.toString(progressBar_Player2.getProgress()));

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
