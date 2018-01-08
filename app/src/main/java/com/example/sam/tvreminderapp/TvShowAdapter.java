package com.example.sam.tvreminderapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sam.tvreminderapp.DB.Table.TvShowDB;
import com.example.sam.tvreminderapp.Object.Item;
import com.example.sam.tvreminderapp.Object.Movie;
import com.example.sam.tvreminderapp.Object.TvShow;

import java.util.ArrayList;

/**
 * Created by Angelo on 08/01/2018.
 */

public class TvShowAdapter extends RecyclerView.Adapter<ItemViewHolder>  {

    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    private TvShowAdapter.OnItemClickListener listener;

    private ArrayList<TvShow> listTvShow = new ArrayList<>();

    public TvShowAdapter(Context context, TvShowAdapter.OnItemClickListener listener) {
        this.listener = listener;
        TvShowDB tvShowDB = new TvShowDB(context);
        tvShowDB.allTvShows(listTvShow);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_addeditem, parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bind(listTvShow.get(position), null, listener);
    }

    @Override
    public int getItemCount() {
        return listTvShow.size();
    }
}
