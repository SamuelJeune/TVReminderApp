package com.example.sam.tvreminderapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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
            textViewView = (TextView) itemView.findViewById(R.id.textview);
            yearTextView = itemView.findViewById(R.id.yeartextview);
        }

        //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
        public void bind(JSONObject myObject){
            try {
                textViewView.setText(myObject.getString("Title"));
                yearTextView.setText(myObject.getString("Year"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

