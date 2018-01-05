package com.example.sam.tvreminderapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;


import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity implements SearchView.OnQueryTextListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SearchView searchView = findViewById(R.id.searchView); // inititate a search view
        searchView.setOnQueryTextListener(this);


    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("submit", query);
        try {
            query= URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        search(query, this);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        Log.d("change", text);


        return false;
    }

    public void search(String searchQuery, final Context context) {
        final JSONArray jsonArray;
        String url = "http://www.omdbapi.com/?i=tt3896198&apikey=31595ce6";
        final RecyclerView recyclerView = findViewById(R.id.myRecyclerView);
        OMDBApiConnection.getJsonArray(searchQuery, context, url, new OMDBApiConnection.VolleyCallback() {
            @Override
            public JSONArray onSuccess(JSONArray result) {

                //définit l'agencement des cellules, ici de façon verticale, comme une ListView
                recyclerView.setLayoutManager(new LinearLayoutManager(context));

                //pour adapter en grille comme une RecyclerView, avec 2 cellules par ligne
                //recyclerView.setLayoutManager(new GridLayoutManager(this,2));

                //puis créer un MyAdapter, lui fournir notre liste de villes.
                //cet adapter servira à remplir notre recyclerview
                recyclerView.setAdapter(new searchResultAdapter(result));
                return result;
            }
        });

    }
}
