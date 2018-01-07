package com.example.sam.tvreminderapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sam.tvreminderapp.DB.Table.MovieDB;
import com.example.sam.tvreminderapp.Object.Movie;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Angelo on 07/01/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder>  {

    public interface OnItemClickListener {
        void onItemClick(Movie item);
    }

    private OnItemClickListener listener;

    private ArrayList<Movie> listMovies = new ArrayList<>();

    public MovieAdapter(Context context, OnItemClickListener listener) {
        this.listener = listener;
        MovieDB movieDB = new MovieDB(context);
        movieDB.allMovies(listMovies);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_addeditem, parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(listMovies.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }
}
