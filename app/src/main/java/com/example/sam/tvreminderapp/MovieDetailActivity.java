package com.example.sam.tvreminderapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();

        String movieId = intent.getStringExtra("MOVIE_ID");

        OMDBApiConnection.getMovieById(movieId, this, new OMDBApiConnection.VolleyCallbackObject() {
            @Override
            public JSONObject onSuccess(JSONObject result) {
                TextView titleTextView = findViewById(R.id.titleView);
                ImageView posterView = findViewById(R.id.posterView);
                TextView yearTextView = findViewById(R.id.yeartextview);
                TextView directorTextView = findViewById(R.id.directortextview);
                TextView genreTextView = findViewById(R.id.genretextView);
                TextView countryTextView = findViewById(R.id.countrytextView);
                TextView imdbTextView = findViewById(R.id.imdbtextView);
                TextView actorTextView = findViewById(R.id.actortextView);
                TextView descriptionTextView = findViewById(R.id.descriptiontextView);
                try {
                    titleTextView.setText(result.getString("Title"));
                    Picasso.with(getApplicationContext()).load(result.getString("Poster")).into(posterView);
                    yearTextView.setText(result.getString("Year"));
                    directorTextView.setText(result.getString("Director"));
                    genreTextView.setText(result.getString("Genre"));
                    countryTextView.setText(result.getString("Country"));
                    imdbTextView.setText("IMDB score : "+result.getString("imdbRating"));
                    actorTextView.setText(result.getString("Actors"));
                    descriptionTextView.setText(result.getString("Plot"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return result;
            }
        });
    }
}