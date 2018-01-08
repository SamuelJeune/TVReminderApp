package com.example.sam.tvreminderapp.ItemDetail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.sam.tvreminderapp.OMDBApiConnection;
import com.example.sam.tvreminderapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class SeasonDetailActivity extends AppCompatActivity {

    String movieId;
    int totalSeasons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season_detail);

        Intent intent = getIntent();
        movieId = intent.getStringExtra("ITEM_ID");
        totalSeasons = intent.getIntExtra("TOTAL_SEASON",0);

        // Create an instance of SectionedRecyclerViewAdapter
        final SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.seasonRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        final List<JSONArray> seasonList = new ArrayList<>();
        for(int i=0; i<totalSeasons; i++){
            sectionAdapter.addSection(new MySection(movieId, i+1, getApplicationContext(),sectionAdapter));
            recyclerView.setAdapter(sectionAdapter);
        }
    }
}
