package com.example.sam.tvreminderapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 07/12/2017.
 */

public class searchResultAdapter extends RecyclerView.Adapter<searchResultViewHolder> {

        private List<JSONObject> list;

        //ajouter un constructeur prenant en entrée une liste
        public searchResultAdapter(JSONArray jsonArray) {
            list = new ArrayList<>();
            for(int i=0; i<jsonArray.length(); i++) {
                try {
                    list.add(jsonArray.getJSONObject(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //cette fonction permet de créer les viewHolder
        //et par la même indiquer la vue à inflater (à partir des layout xml)
        @Override
        public searchResultViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listview_item,viewGroup,false);
            return new searchResultViewHolder(view);
        }

        //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
        @Override
        public void onBindViewHolder(searchResultViewHolder myViewHolder, int position) {
            JSONObject myObject = list.get(position);
            myViewHolder.bind(myObject);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }

