package com.example.sam.tvreminderapp.MovieDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sam.tvreminderapp.DB.Table.MovieDB;
import com.example.sam.tvreminderapp.OMDBApiConnection;
import com.example.sam.tvreminderapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class MovieDetailActivity extends AppCompatActivity {

    JSONObject movieInformation;
    private String movieId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent intent = getIntent();
        movieId = intent.getStringExtra("MOVIE_ID");

        OMDBApiConnection.getMovieById(movieId, this, new OMDBApiConnection.VolleyCallbackObject() {
            @Override
            public JSONObject onSuccess(JSONObject result) {
                movieInformation=result;
                dysplayMovieInformation(result);
                return result;
            }
        });

        Button addToSeenListButton = findViewById(R.id.addtoseenbutton);
        addToSeenListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddToListButtonClicked(1);
            }
        });
        final Button addToWishListButton = findViewById(R.id.addtowishbutton);
        addToWishListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddToListButtonClicked(0);
            }
        });


    }

    private void dysplayMovieInformation(final JSONObject result){
        TextView titleTextView = findViewById(R.id.titleView);
        ImageView posterView = findViewById(R.id.posterView);
        TextView yearTextView = findViewById(R.id.yeartextview);
        TextView directorTextView = findViewById(R.id.directortextview);
        TextView genreTextView = findViewById(R.id.genretextView);
        TextView countryTextView = findViewById(R.id.countrytextView);
        TextView imdbTextView = findViewById(R.id.imdbtextView);
        TextView actorTextView = findViewById(R.id.actortextView);
        TextView descriptionTextView = findViewById(R.id.descriptiontextView);
        Button seeSeasonDetailButton = findViewById(R.id.seeseasonbutton);

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
            if(Objects.equals(result.getString("Type"), "series")){
                final int totalSeasons = Integer.parseInt(result.getString("totalSeasons"));
                seeSeasonDetailButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dysplaySerieInformation(movieId, totalSeasons);
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void dysplaySerieInformation(String movieId, int totalSeasons) {
        Intent intent = new Intent(this, SeasonDetailActivity.class);
        intent.putExtra("MOVIE_ID", movieId);
        intent.putExtra("TOTAL_SEASON", totalSeasons);
        startActivity(intent);
    }


    public void onAddToListButtonClicked(int seen){
        MovieDB movieDB = new MovieDB(this.getApplicationContext());

        try {
            long id = movieDB.add(movieId, movieInformation.getString("Title"), movieInformation.getInt("Year"), movieInformation.getString("Director"), seen);
            Toast.makeText(getApplicationContext(), "Item added to seen list, ID : " + id, Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            System.err.println("ERROR ADDING MOVIE !");
            e.printStackTrace();
        }
    }
}