package com.example.alexperez.duelapp;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BanList extends AppCompatActivity {

    private static final String URL_DATA = "https://api.myjson.com/bins/1f9c5j";

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

        loadRecyclerViewData();
    }

    private void loadRecyclerViewData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("cards");

                            for(int i = 0; i < array.length(); i++){
                                JSONObject o = array.getJSONObject(i);
                                ListItem item = new ListItem(
                                        o.getString("Card Type"),
                                        o.getString("Card Name"),
                                        o.getString("Advance Format"),
                                        o.getString("User Format")
                                );
                                listItems.add(item);
                            }

                            adapter = new BanlistAdapter(listItems,getApplicationContext());
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}