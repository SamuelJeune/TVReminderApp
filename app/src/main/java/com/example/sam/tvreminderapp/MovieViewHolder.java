package com.example.sam.tvreminderapp;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sam.tvreminderapp.Object.Movie;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Angelo on 07/01/2018.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder {

    private TextView textViewView;
    private TextView yearTextView;
    private TextView descriptionView;
    private View view;

    public MovieViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        textViewView = itemView.findViewById(R.id.title_item);
        yearTextView = itemView.findViewById(R.id.year_item);
        descriptionView = itemView.findViewById(R.id.description_item);
    }

    public void bind(Movie movie) {
        textViewView.setText(movie.getTitle());
        yearTextView.setText(String.valueOf(movie.getYear()));

        OMDBApiConnection.getMovieById(movie.getIdOMDB(), view.getContext(), new OMDBApiConnection.VolleyCallbackObject() {
            @Override
            public JSONObject onSuccess(JSONObject result) {
                try {
                    Picasso.with(view.getContext()).load(result.getString("Poster")).into((ImageView) view.findViewById(R.id.poster));
                    descriptionView.setText(result.getString("Plot"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return result;
            }
        });
    }
}
