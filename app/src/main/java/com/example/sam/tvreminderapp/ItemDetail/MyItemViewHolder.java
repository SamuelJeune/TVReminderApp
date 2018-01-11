package com.example.sam.tvreminderapp.ItemDetail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.sam.tvreminderapp.R;

/**
 * Created by Sam on 1/7/2018.
 */
class MyItemViewHolder extends RecyclerView.ViewHolder {
    final TextView tvItem;
    final CheckBox seen;

    public MyItemViewHolder(View itemView) {
        super(itemView);
        tvItem = (TextView) itemView.findViewById(R.id.tvItem);
        seen = (CheckBox) itemView.findViewById(R.id.checkbox_episode);
    }
}
