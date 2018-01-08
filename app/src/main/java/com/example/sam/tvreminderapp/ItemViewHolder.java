package com.example.sam.tvreminderapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sam.tvreminderapp.Object.Item;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Angelo on 07/01/2018.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder {

    private TextView textViewView;
    private TextView yearTextView;
    private TextView descriptionView;
    private View view;

    public ItemViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        textViewView = itemView.findViewById(R.id.title_item);
        yearTextView = itemView.findViewById(R.id.year_item);
        descriptionView = itemView.findViewById(R.id.description_item);
    }

    public void bind(final Item item, final MovieAdapter.OnItemClickListener movieListener, final TvShowAdapter.OnItemClickListener tvShowListener) {
        textViewView.setText(item.getTitle());
        yearTextView.setText(item.getYear());

        if(item.getType() == "movie") {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movieListener.onItemClick(item);
                }
            });
            OMDBApiConnection.getItemById(item.getIdOMDB(), view.getContext(), new OMDBApiConnection.VolleyCallbackObject() {
                @Override
                public JSONObject onSuccess(JSONObject result) {
                    try {
                        Picasso.with(view.getContext()).load(result.getString("Poster")).into((ImageView) view.findViewById(R.id.poster));
                        descriptionView.setText(result.getString("Plot"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return result;
                }
            });
        }
        else if(item.getType() == "series") {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvShowListener.onItemClick(item);
                }
            });
            OMDBApiConnection.getItemById(item.getIdOMDB(), view.getContext(), new OMDBApiConnection.VolleyCallbackObject() {
                @Override
                public JSONObject onSuccess(JSONObject result) {
                    try {
                        Picasso.with(view.getContext()).load(result.getString("Poster")).into((ImageView) view.findViewById(R.id.poster));
                        descriptionView.setText(result.getString("Plot"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return result;
                }
            });
        }
    }
}
