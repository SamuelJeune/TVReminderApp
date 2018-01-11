package com.example.sam.tvreminderapp.ItemDetail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;

import com.example.sam.tvreminderapp.DB.Table.SeasonDB;
import com.example.sam.tvreminderapp.DB.Table.TvShowDB;
import com.example.sam.tvreminderapp.Object.TvShow;
import com.example.sam.tvreminderapp.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class SeasonDetailActivity extends AppCompatActivity {

    private String itemID;
    private int totalSeasons;
    private CheckBox checkBoxSeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season_detail);

        Intent intent = getIntent();
        itemID = intent.getStringExtra("ITEM_ID");
        totalSeasons = intent.getIntExtra("TOTAL_SEASON",0);
        checkBoxSeen = findViewById(R.id.checkbox_season);

        // Create an instance of SectionedRecyclerViewAdapter
        final SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.seasonRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        for (int i = 0; i < totalSeasons; i++) {
            sectionAdapter.addSection(new MySection(itemID, i + 1, getApplicationContext(), sectionAdapter));
            recyclerView.setAdapter(sectionAdapter);
        }
    }
}
