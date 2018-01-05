package com.example.sam.tvreminderapp;

import android.content.Context;
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
        private View.OnClickListener onClickListener;

        public searchResultViewHolder(View itemView) {
            super(itemView);
            
            onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Yo nigga !", Toast.LENGTH_LONG).show();
                }
            };

            itemView.setOnClickListener(onClickListener);
            textViewView = itemView.findViewById(R.id.textview);
            yearTextView = itemView.findViewById(R.id.yeartextview);
        }

        public void bind(JSONObject myObject){
            try {
                textViewView.setText(myObject.getString("Title"));
                yearTextView.setText(myObject.getString("Year"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

