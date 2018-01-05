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

        //itemView est la vue correspondante à 1 cellule
        public searchResultViewHolder(View itemView) {
            super(itemView);

            //c'est ici que l'on fait nos findView

            textViewView = (TextView) itemView.findViewById(R.id.textview);
        }

        //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
        public void bind(JSONObject myObject){
            try {
                textViewView.setText(myObject.getString("Title"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

