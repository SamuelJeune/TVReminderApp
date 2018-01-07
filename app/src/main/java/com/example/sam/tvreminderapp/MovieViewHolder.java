package com.example.sam.tvreminderapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.sam.tvreminderapp.Object.Movie;
import com.example.sam.tvreminderapp.R;

/**
 * Created by Angelo on 07/01/2018.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder {

    private TextView textViewView;
    private TextView yearTextView;

    public MovieViewHolder(View itemView) {
        super(itemView);
        textViewView = itemView.findViewById(R.id.textview);
        yearTextView = itemView.findViewById(R.id.yeartextview);
    }

    public void bind(Movie movie) {
        textViewView.setText(movie.getTitle());
        //yearTextView.setText(movie.getYear());
    }
}
