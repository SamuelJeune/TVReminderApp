package com.example.sam.tvreminderapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sam on 07/12/2017.
 */

public class searchResultViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewView;
        private TextView yearTextView;

        public searchResultViewHolder(View itemView) {
            super(itemView);

            textViewView = itemView.findViewById(R.id.textview);
            yearTextView = itemView.findViewById(R.id.yeartextview);
        }

        public void bind(final JSONObject myObject, final searchResultAdapter.OnItemClickListener listener){
            try {
                textViewView.setText(myObject.getString("Title"));
                yearTextView.setText(myObject.getString("Year"));
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        listener.onItemClick(myObject);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
 }

