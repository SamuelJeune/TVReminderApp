package com.example.sam.tvreminderapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sam on 07/12/2017.
 */

public class OMDBApiConnection {

    private static String url = "http://www.omdbapi.com/?&apikey=31595ce6";

    public static void getJsonArray(String searchQuery, Context context, final VolleyCallbackArray callback){
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

    public static void getItemById(String itemId, Context context, final VolleyCallbackObject callback){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);

        // Request a string response from the provided URL.
        String query = url + "&i=" + itemId;
        Log.d("Item Id : ", query);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                query,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Do something with response
                        //mTextView.setText(response.toString());

                        // Process the JSON
                        // Loop through the array elements
                        JSONObject movieObject = response;
                        Log.d("movie Object : ", movieObject.toString());
                        callback.onSuccess(movieObject);
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

        queue.add(jsonObjectRequest);
    }

    public static void getSeasonDetail(String serieId, int seasonNumber, Context context , final VolleyCallbackObject callback){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);

        // Request a string response from the provided URL.
        String query = url + "&i=" + serieId + "&Season=" + seasonNumber;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                query,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Do something with response
                        //mTextView.setText(response.toString());

                        // Process the JSON
                        // Loop through the array elements
                        callback.onSuccess(response);
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

        queue.add(jsonObjectRequest);
    }


    interface VolleyCallbackArray {
        JSONArray onSuccess(JSONArray result);
    }

    public interface VolleyCallbackObject {
        JSONObject onSuccess(JSONObject result);
    }
}


