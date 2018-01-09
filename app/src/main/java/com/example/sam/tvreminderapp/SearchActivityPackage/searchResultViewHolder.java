package com.example.sam.tvreminderapp.SearchActivityPackage;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.sam.tvreminderapp.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sam on 07/12/2017.
 */

public class searchResultViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewView;
        private TextView yearTextView;
        private TextView typeTextView;

        public searchResultViewHolder(View itemView) {
            super(itemView);
            textViewView = itemView.findViewById(R.id.textview);
            yearTextView = itemView.findViewById(R.id.yeartextview);
            typeTextView = itemView.findViewById(R.id.typetextView);
        }

        public void bind(final JSONObject myObject, final searchResultAdapter.OnItemClickListener listener){
            try {
                textViewView.setText(myObject.getString("Title"));
                yearTextView.setText(myObject.getString("Year"));
                typeTextView.setText(myObject.getString("Type"));
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

