package com.example.alexperez.duelapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BanList extends AppCompatActivity {
    /*Production: https://cdn.rawgit.com/AlexPerez78/d7307cf43493f06f17420b35e8d8ad73/raw/ba033506db1e6aca1e6d851df9c7be8c6d0547ed/banlist_JSON.json
    * Development: https://rawgit.com/AlexPerez78/d7307cf43493f06f17420b35e8d8ad73/raw/ba033506db1e6aca1e6d851df9c7be8c6d0547ed/banlist_JSON.json
    * OG: https://api.jsonbin.io/b/5a2233aa3cc482364837a0ca/4
    * */
    private static final String URL_DATA = "https://rawgit.com/AlexPerez78/d7307cf43493f06f17420b35e8d8ad73/raw/ba033506db1e6aca1e6d851df9c7be8c6d0547ed/banlist_JSON.json";

    private ActionBarDrawerToggle mToggle;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<ListItem> listItems;

    MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ban_list);

        /*Navigation Drawer (Hamburger Menu)*/
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.banlist_Drawerlayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        getSupportActionBar().setTitle("BanList");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Removes the Fade that Drawer navigation has
        mDrawerLayout.setScrimColor(ContextCompat.getColor(BanList.this, android.R.color.transparent));
        //Programmatically set the hamburger menu to white instead of black
        mToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        mToggle.syncState();

        /*Recycler View*/
        recyclerView = (RecyclerView) findViewById(R.id.banlistrecycle);
        recyclerView.setHasFixedSize(true); //Every item in the recyclerview has a fixed size
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();

        loadRecyclerViewData();

/*-------------------------------------------------------------------------------------------------------------------------------------------------------- */
        /*Set for the Search View */
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Leave empty, we don't want to alter the list when pressed

            }

            @Override
            public void onSearchViewClosed() {
                //If search view is closed, list view will restore itself to default (O.G. Ban list Items)
                adapter = new BanlistAdapter(listItems,getApplicationContext());
                recyclerView.setAdapter(adapter);

            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Forceful Way of handling Keyboard Token, as of yet Androids Library does not have a method
                View view = getCurrentFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText != null && !newText.isEmpty()){
                  List<ListItem> queryListFound = new ArrayList<>();
                  for(ListItem items:listItems){
                      //Search Engine Head Text must be converted to Lowercase,
                      //else it will search only for specific Lower or uppercase

                      /*codeword to see what recently has been altered in the Banlist*/
                      if(newText.equalsIgnoreCase("update") && !items.getRemark().isEmpty()){
                          queryListFound.add(items);
                      }
                      /*Default Base Case*/
                      else if(items.getHead().toLowerCase().contains(newText)){
                          queryListFound.add(items);
                      }

                      //Add current items into the adapter
                      adapter = new BanlistAdapter(queryListFound,getApplicationContext());
                      recyclerView.setAdapter(adapter); //Propagate List with Adapter Items
                  }
                }
                /*Base Case If User does not touch Search Bar*/
                else{
                    adapter = new BanlistAdapter(listItems,getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }
                return false;
            }
        });
/*-------------------------------------------------------------------------------------------------------------------------------------------------------- */

        /* Set For the Hamburger Menu*/
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
    /*-------------------------------------------------------------------------------------------------------------------------------------------------------- */
    private void loadRecyclerViewData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Card Data...");
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
                                        o.getString("Remarks")
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
                        //If an error would happen, a toast will state the error (Saving App From Crashing)
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                        /*StyleableToast.makeText(getApplicationContext(),error.getMessage(),R.style.Lost_Toast).show();*/
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cardsearchbar,menu);
        MenuItem item = menu.findItem(R.id.card_search);
        searchView.setMenuItem(item);
        return true;
    }
}