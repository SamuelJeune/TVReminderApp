package com.example.sam.tvreminderapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.widget.Toast;


import com.example.sam.tvreminderapp.MovieDetail.MovieDetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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

        final RecyclerView recyclerView = findViewById(R.id.myRecyclerView);
        OMDBApiConnection.getJsonArray(searchQuery, context, new OMDBApiConnection.VolleyCallbackArray() {
            @Override
            public JSONArray onSuccess(JSONArray result) {

                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(new searchResultAdapter(result, new searchResultAdapter.OnItemClickListener() {
                    @Override public void onItemClick(JSONObject item) {
                        try {
                            Toast.makeText(getApplicationContext(), "Item Clicked "+item.getString("Title"), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class);
                            intent.putExtra("MOVIE_ID", item.getString("imdbID"));
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }}));
                return result;
            }
        });

    }
}
