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

    public interface OnItemClickListener {
        void onItemClick(JSONObject item);
    }

    private final OnItemClickListener listener;

    public searchResultAdapter(JSONArray jsonArray, OnItemClickListener listener) {
        this.listener = listener;
        list = new ArrayList<>();
        for(int i=0; i<jsonArray.length(); i++) {
            try {
                list.add(jsonArray.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public searchResultViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listview_item,viewGroup,false);
        return new searchResultViewHolder(view);
    }


    @Override
    public void onBindViewHolder(searchResultViewHolder myViewHolder, int position) {
        JSONObject myObject = list.get(position);
        myViewHolder.bind(myObject, listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

