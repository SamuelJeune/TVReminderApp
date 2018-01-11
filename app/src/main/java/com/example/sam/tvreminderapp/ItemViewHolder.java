package com.example.sam.tvreminderapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sam.tvreminderapp.DB.Table.MovieDB;
import com.example.sam.tvreminderapp.DB.Table.TvShowDB;
import com.example.sam.tvreminderapp.Object.Item;
import com.example.sam.tvreminderapp.Object.Movie;
import com.example.sam.tvreminderapp.Object.TvShow;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Angelo on 07/01/2018.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder {

    private TextView textViewView;
    private TextView yearTextView;
    private CheckBox seenBox;
    private View view;

    public ItemViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        textViewView = itemView.findViewById(R.id.title_item);
        yearTextView = itemView.findViewById(R.id.year_item);
        seenBox = itemView.findViewById(R.id.seen);
    }

    public void bind(final Item item, final MovieAdapter.OnItemClickListener movieListener, final TvShowAdapter.OnItemClickListener tvShowListener) {
        textViewView.setText(item.getTitle());
        yearTextView.setText(item.getYear());


        System.out.println("Movie ID : "+item.getIdOMDB());
        if(item.getType() == "movie") {
            final Movie movie = (Movie) item;
            if(movie.isSeen()){
                seenBox.setChecked(true);
            }else{
                seenBox.setChecked(false);
            }

            seenBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MovieDB movieDB = new MovieDB(view.getContext());
                    String[] values = new String[1];
                    String[] keys = new String[1];
                    keys[0] = "seen";

                    if (((CheckBox) v).isChecked()) {
                        values[0] = "1";
                    }
                    else {
                        values[0] = "0";
                    }
                    movieDB.update(movie.getId(), keys, values);
                }
            });

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

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return result;
                }
            });
        }
        else if(item.getType() == "series") {
            final TvShow tvShow = (TvShow) item;
            final TvShowDB tvShowDB = new TvShowDB(view.getContext());
            if(tvShowDB.isSeen(tvShow.getId(), tvShow.getIdOMDB())){
                seenBox.setChecked(true);
            }else{
                seenBox.setChecked(false);
            }

            seenBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvShowDB.setTvShowSeen(tvShow.getId(), tvShow.getIdOMDB(), ((CheckBox) v).isChecked() ? 1 : 0);
                }
            });

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
                        //descriptionView.setText(result.getString("Plot"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return result;
                }
            });
        }
    }
}
