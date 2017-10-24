package com.example.alexperez.duelapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class BanList extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<ListItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ban_list);

        /*Navigation Drawer (Hamburger Menu)*/
        mDrawerLayout = (DrawerLayout) findViewById(R.id.banlist_Drawerlayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        /*Recycler View*/
        recyclerView = (RecyclerView) findViewById(R.id.banlistrecycle);
        recyclerView.setHasFixedSize(true); //Every item in the recyclerview has a fixed size
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();

        for(int i = 0; i < 10; i++){
            ListItem card = new ListItem(
                    "Nekroz Of Brionac " + (i+1),
                    "Limited"
            );

            listItems.add(card);
        }

        adapter = new BanlistAdapter(listItems, this);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
