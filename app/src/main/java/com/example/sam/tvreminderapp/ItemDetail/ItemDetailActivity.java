package com.example.sam.tvreminderapp.ItemDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sam.tvreminderapp.DB.Table.MovieDB;
import com.example.sam.tvreminderapp.DB.Table.TvShowDB;
import com.example.sam.tvreminderapp.OMDBApiConnection;
import com.example.sam.tvreminderapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class ItemDetailActivity extends AppCompatActivity {

    private JSONObject itemInformation;
    private String itemId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent intent = getIntent();
        itemId = intent.getStringExtra("ITEM_ID");

        OMDBApiConnection.getItemById(itemId, this, new OMDBApiConnection.VolleyCallbackObject() {
            @Override
            public JSONObject onSuccess(JSONObject result) {
                itemInformation =result;
                dysplayMovieInformation(result);
                return result;
            }
        });

        Button addToSeenListButton = findViewById(R.id.addtoseenbutton);
        addToSeenListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    onAddToListButtonClicked(1, itemInformation.getString("Type"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        final Button addToWishListButton = findViewById(R.id.addtowishbutton);
        addToWishListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    onAddToListButtonClicked(0,  itemInformation.getString("Type"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                        dysplaySerieInformation(itemId, totalSeasons);
                    }
                });
            } else seeSeasonDetailButton.setVisibility(View.INVISIBLE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void dysplaySerieInformation(String movieId, int totalSeasons) {
        Intent intent = new Intent(this, SeasonDetailActivity.class);
        intent.putExtra("ITEM_ID", movieId);
        intent.putExtra("TOTAL_SEASON", totalSeasons);
        startActivity(intent);
    }


    public void onAddToListButtonClicked(int seen, String type){
        long id;
        MovieDB movieDB = new MovieDB(this.getApplicationContext());
        TvShowDB tvShowDB = new TvShowDB(this.getApplicationContext());
        System.out.println("ADDING ITEM : " + itemId);
        try {
            if(type.equals("movie")) {
                id = movieDB.add(itemId, itemInformation.getString("Title"), itemInformation.getInt("Year"), itemInformation.getString("Director"), seen);
                Toast.makeText(getApplicationContext(), "Movie added to list, ID : " + id, Toast.LENGTH_SHORT).show();
            } else if(type.equals("series")) {
                id = tvShowDB.add(itemId, itemInformation.getString("Title"), itemInformation.getString("Year"), itemInformation.getInt("totalSeasons"));
                Toast.makeText(getApplicationContext(), "Tv show added to list, ID : " + id, Toast.LENGTH_SHORT).show();
            } else System.out.println("ERROR  : " + type);
        } catch (JSONException e) {
            System.err.println("ERROR ADDING ITEM !");
            e.printStackTrace();
        }
    }
}
