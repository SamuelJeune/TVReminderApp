package com.example.sam.tvreminderapp.ItemDetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sam.tvreminderapp.DB.Table.EpisodeDB;
import com.example.sam.tvreminderapp.DB.Table.SeasonDB;
import com.example.sam.tvreminderapp.DB.Table.TvShowDB;
import com.example.sam.tvreminderapp.OMDBApiConnection;
import com.example.sam.tvreminderapp.Object.Episode;
import com.example.sam.tvreminderapp.Object.TvShow;
import com.example.sam.tvreminderapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

/**
 * Created by Sam on 1/7/2018.
 */

class MySection extends StatelessSection {
    private ArrayList<Episode> list;
    private int season;
    private JSONArray jsonArray;
    boolean expanded = true;
    SectionedRecyclerViewAdapter sectionAdapter;

    public MySection(String movieId , final int season, Context context, final SectionedRecyclerViewAdapter sectionAdapter) {
        // call constructor with layout resources for this Section header and items
        super(new SectionParameters.Builder(R.layout.section_item)
                .headerResourceId(R.layout.section_header)
                .build());
        this.season=season;
        list = new ArrayList<>();
        this.sectionAdapter = sectionAdapter;

        TvShowDB tvShowDB = new TvShowDB(context);
        TvShow tvShow = tvShowDB.getTvShowByIdOMDB(movieId);

        if(tvShow == null) {
            OMDBApiConnection.getSeasonDetail(movieId, season, context, new OMDBApiConnection.VolleyCallbackObject() {
                @Override
                public JSONObject onSuccess(JSONObject result) {
                    try {
                        jsonArray = result.getJSONArray("Episodes");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                list.add(new Episode(0, jsonArray.getJSONObject(i).getString("Title"), i+1, false, 0));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            sectionAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return result;
                }
            });
        }
        else {
            SeasonDB seasonDB = new SeasonDB(context);
            EpisodeDB episodeDB = new EpisodeDB(context);
            episodeDB.allEpisodeFromSeason(list, seasonDB.getSeasonByIdTvShow(tvShowDB.getTvShowByIdOMDB(movieId).getId(), season).getId(), season);
            sectionAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public int getContentItemsTotal() {
        return expanded? list.size() : 0; // number of items of this section
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        // return a custom instance of ViewHolder for the items of this section
        return new MyItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyItemViewHolder itemHolder = (MyItemViewHolder) holder;

        // bind your view here
        itemHolder.tvItem.setText(list.get(position).getTitle());
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        final HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
        headerHolder.tvTitle.setText("Season "+season);
        headerHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expanded = !expanded;
                headerHolder.imgArrow.setImageResource(
                        expanded ? R.drawable.ic_keyboard_arrow_up_black_18dp : R.drawable.ic_keyboard_arrow_down_black_18dp
                );
                sectionAdapter.notifyDataSetChanged();
            }
        });
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;
        public View rootView;
        private final ImageView imgArrow;

        HeaderViewHolder(View view) {
            super(view);
            rootView = view;
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            imgArrow = (ImageView) view.findViewById(R.id.imgArrow);
        }
    }
}