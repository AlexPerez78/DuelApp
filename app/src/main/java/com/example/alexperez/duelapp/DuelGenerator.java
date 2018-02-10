package com.example.alexperez.duelapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class DuelGenerator extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duel_generator);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.tournament_Drawerlayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToggle.syncState();

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
                        Intent tournamentActivity = new Intent(getApplicationContext(),DuelGenerator.class);
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
}
