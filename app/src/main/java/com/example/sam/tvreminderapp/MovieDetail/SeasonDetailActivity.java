package com.example.sam.tvreminderapp.MovieDetail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.sam.tvreminderapp.OMDBApiConnection;
import com.example.sam.tvreminderapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class SeasonDetailActivity extends AppCompatActivity {

    String movieId;
    int totalSeasons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season_detail);

        Intent intent = getIntent();
        movieId = intent.getStringExtra("MOVIE_ID");
        totalSeasons = intent.getIntExtra("TOTAL_SEASON",0);

        // Create an instance of SectionedRecyclerViewAdapter
        final SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.seasonRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        for(int i=0; i<totalSeasons; i++){
            OMDBApiConnection.getSeasonDetail(movieId, i+1, this, new OMDBApiConnection.VolleyCallbackObject() {
                @Override
                public JSONObject onSuccess(JSONObject result) {
                    try {
                        sectionAdapter.addSection(new MySection(result.getJSONArray("Episodes"), result.getString("Season")));
                        recyclerView.setAdapter(sectionAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return result;
                }
            });
        }
    }
}
