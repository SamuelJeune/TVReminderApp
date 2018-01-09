package com.example.sam.tvreminderapp.SearchActivityPackage;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sam.tvreminderapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sam on 07/12/2017.
 */

public class searchResultViewHolder extends RecyclerView.ViewHolder{

        private TextView titleTextView;
        private TextView yearTextView;
        private TextView typeTextView;
        private View view;

        public searchResultViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            titleTextView = itemView.findViewById(R.id.searchtitle_item);
            yearTextView = itemView.findViewById(R.id.searchyear_item);
        }

        public void bind(final JSONObject myObject, final searchResultAdapter.OnItemClickListener listener){
            try {
                titleTextView.setText(myObject.getString("Title"));
                yearTextView.setText(myObject.getString("Year"));
                //typeTextView.setText(myObject.getString("Type"));
                Picasso.with(view.getContext()).load(myObject.getString("Poster")).into((ImageView) view.findViewById(R.id.searchposter));

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

