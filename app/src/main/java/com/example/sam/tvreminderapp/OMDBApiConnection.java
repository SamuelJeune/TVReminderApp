package com.example.sam.tvreminderapp;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sam on 07/12/2017.
 */

public class OMDBApiConnection {


        protected static void getJsonArray(String searchQuery, Context context, String url, final VolleyCallback callback){
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(context);

            // Request a string response from the provided URL.
            String query = url + "&s=" + searchQuery;
            // Initialize a new JsonArrayRequest instance
            JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    query,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Do something with response
                            //mTextView.setText(response.toString());

                            // Process the JSON
                            try {
                                // Loop through the array elements
                                JSONArray jsonArray;
                                jsonArray = response.getJSONArray("Search");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    // Get current json object
                                    JSONObject film = jsonArray.getJSONObject(i);

                                    // Get the current student (json object) data
                                    String title = film.getString("Title");

                                    // Display the formatted json data in text view
                                    Log.d("title", title);
                                }
                                callback.onSuccess(jsonArray);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Do something when error occurred
                            Log.d("err", error.getMessage());
                        }
                    }
            );


// Add the request to the RequestQueue.
            queue.add(jsonArrayRequest);
    }


    interface VolleyCallback {
        JSONArray onSuccess(JSONArray result);
    }
}


