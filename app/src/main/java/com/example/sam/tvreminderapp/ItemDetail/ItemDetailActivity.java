package com.example.sam.tvreminderapp.ItemDetail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sam.tvreminderapp.DB.Table.EpisodeDB;
import com.example.sam.tvreminderapp.DB.Table.MovieDB;
import com.example.sam.tvreminderapp.DB.Table.SeasonDB;
import com.example.sam.tvreminderapp.DB.Table.TvShowDB;
import com.example.sam.tvreminderapp.OMDBApiConnection;
import com.example.sam.tvreminderapp.Object.Movie;
import com.example.sam.tvreminderapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class ItemDetailActivity extends AppCompatActivity {

    private JSONObject itemInformation;
    private String itemId;
    private MovieDB movieDB;
    private int totalSeasons;
    private Button addToSeenListButton;
    private Button addToWishListButton;

    private JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent intent = getIntent();
        itemId = intent.getStringExtra("ITEM_ID");

        this.movieDB = new MovieDB(getApplicationContext());

        final LinearLayout linearLayoutInformations = findViewById(R.id.container_informations);
        final LinearLayout linearLayoutSpinner = findViewById(R.id.container_spinner);
        linearLayoutInformations.setVisibility(View.INVISIBLE);

        OMDBApiConnection.getItemById(itemId, this, new OMDBApiConnection.VolleyCallbackObject() {
            @Override
            public JSONObject onSuccess(JSONObject result) {
                linearLayoutInformations.setVisibility(View.VISIBLE);
                linearLayoutSpinner.setVisibility(View.INVISIBLE);
                itemInformation =result;
                dysplayMovieInformation(result);
                return result;
            }
        });




        addToSeenListButton = findViewById(R.id.addtoseenbutton);
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
        addToWishListButton = findViewById(R.id.addtowishbutton);
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
                totalSeasons = Integer.parseInt(result.getString("totalSeasons"));
                seeSeasonDetailButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dysplaySerieInformation(itemId, totalSeasons);
                    }
                });
            } else {
                seeSeasonDetailButton.setVisibility(View.INVISIBLE);
                final Movie movie = movieDB.getMovieByIMDBID(itemId);
                if(movie!=null) {
                    updateButton(movie);
                }
            }
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
        System.out.println("ADDING ITEM : " + itemId);
        try {
            if(type.equals("movie")) {
                id = movieDB.add(itemId, itemInformation.getString("Title"), itemInformation.getInt("Year"), itemInformation.getString("Director"), seen);
                Toast.makeText(getApplicationContext(), "Movie added to list, ID : " + id, Toast.LENGTH_SHORT).show();
                updateButton(movieDB.getMovie(id));
            } else if(type.equals("series")) {
                addTvShow();
            } else System.out.println("ERROR  : " + type);
        } catch (JSONException e) {
            System.err.println("ERROR ADDING ITEM !");
            e.printStackTrace();
        }

    }

    public void addTvShow() throws JSONException {
        TvShowDB tvShowDB = new TvShowDB(this.getApplicationContext());
        final long idTvShow = tvShowDB.add(itemId, itemInformation.getString("Title"), itemInformation.getString("Year"), itemInformation.getInt("totalSeasons"));
        final SeasonDB seasonDB = new SeasonDB(getApplicationContext());
        final EpisodeDB episodeDB = new EpisodeDB(getApplicationContext());

        for(int i = 0; i < totalSeasons; i++) {
            OMDBApiConnection.getSeasonDetail(itemId, i+1, getApplicationContext(), new OMDBApiConnection.VolleyCallbackObject() {
                @Override
                public JSONObject onSuccess(JSONObject result) {
                    try {
                        jsonArray = result.getJSONArray("Episodes");
                        long idSeason = seasonDB.add(result.getInt("Season"), idTvShow);
                        for (int j = 0; j < jsonArray.length(); j++) {
                            try {
                                episodeDB.add(jsonArray.getJSONObject(j).getString("Title"), result.getInt("Season"), 0, idSeason);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return result;
                }
            });
        }

        Toast.makeText(getApplicationContext(), "Tv show added to list, ID : " + idTvShow, Toast.LENGTH_SHORT).show();
    }

    public void deleteFromList(Movie movie){
        MovieDB movieDB = new MovieDB(this.getApplicationContext());
        movieDB.delete(movie.getIdOMDB());
        updateButton(null);
    }

    public void updateButton(final Movie movie){
        if(movie==null){
            addToSeenListButton.setText("Add To Seen List");
            addToSeenListButton.setBackgroundColor(Color.GREEN);
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
            addToWishListButton.setText("Add To Wish List");
            addToWishListButton.setBackgroundColor(Color.BLUE);
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
        }else{
            if (movie.isSeen()) {
                addToSeenListButton.setText("Delete From Seen List");
                addToSeenListButton.setBackgroundColor(Color.RED);
                addToSeenListButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteFromList(movie);
                    }
                });
            } else {
                addToWishListButton.setText("Delete From Wish List");
                addToWishListButton.setBackgroundColor(Color.RED);
                addToWishListButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteFromList(movie);
                    }
                });
            }
        }

    }
}
