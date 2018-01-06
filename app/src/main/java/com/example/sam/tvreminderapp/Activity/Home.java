package com.example.sam.tvreminderapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.sam.tvreminderapp.R;
import com.example.sam.tvreminderapp.Search;

/**
 * Created by Angelo on 06/01/2018.
 */

public class Home extends AppCompatActivity {

    private FloatingActionButton searchButton;
    private Home instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        searchButton = findViewById(R.id.search_fab);
        instance = this;

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(instance, Search.class);
                startActivity(intent);
            }
        });
    }


}
