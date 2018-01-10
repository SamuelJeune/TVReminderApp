package com.example.sam.tvreminderapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sam.tvreminderapp.DB.Table.MovieDB;
import com.example.sam.tvreminderapp.Object.Item;
import com.example.sam.tvreminderapp.Object.Movie;

import java.util.ArrayList;

/**
 * Created by Angelo on 07/01/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<ItemViewHolder>  {

    private MovieDB movieDB;

    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    private MovieAdapter.OnItemClickListener listener;

    private ArrayList<Movie> listMovies = new ArrayList<>();

    public MovieAdapter(Context context, MovieAdapter.OnItemClickListener listener) {
        this.listener = listener;
        this.movieDB = new MovieDB(context);
        refresh();
    }

    public void refresh() {
        listMovies.clear();
        movieDB.allMovies(listMovies);
        sortList();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_addeditem, parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bind(listMovies.get(position), listener, null);
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    public void sortList() {
        for (int i = 0; i < listMovies.size()-1; i++) {
            for(int j = i + 1; j < listMovies.size(); j++) {
                if(listMovies.get(i).isSeen() && !listMovies.get(j).isSeen()) {
                    Movie m = listMovies.get(j);
                    listMovies.remove(j);
                    listMovies.set(i, m);
                }
            }
        }
    }
}
