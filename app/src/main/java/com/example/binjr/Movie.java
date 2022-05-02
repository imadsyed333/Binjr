package com.example.binjr;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Movie {

    String title;
    String id;
    String posterUrl;
    Context context;
    RequestQueue requestQueue;

    public Movie (String title, String id, Context context) {
        this.title = title;
        this.id = id;
        this.context = context;
        requestQueue = Volley.newRequestQueue(this.context);
        setPosterUrl();
    }

    public String getTitle() {
        return this.title;
    }

    public String getId() {
        return this.id;
    }

    public void setPosterUrl() {
        String url = "https://imdb-api.com/en/API/Title/k_pv6qgh82/" + getId();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    posterUrl = response.getString("image");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Error", "poster badness");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "Request failed");
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public String getPosterUrl() {
        return this.posterUrl;
    }
}
