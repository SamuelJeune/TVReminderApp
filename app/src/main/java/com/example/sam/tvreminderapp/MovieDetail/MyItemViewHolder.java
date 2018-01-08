package com.example.sam.tvreminderapp.MovieDetail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.sam.tvreminderapp.R;

/**
 * Created by Sam on 1/7/2018.
 */
class MyItemViewHolder extends RecyclerView.ViewHolder {
    final TextView tvItem;

    public MyItemViewHolder(View itemView) {
        super(itemView);
        tvItem = (TextView) itemView.findViewById(R.id.tvItem);
    }
}
