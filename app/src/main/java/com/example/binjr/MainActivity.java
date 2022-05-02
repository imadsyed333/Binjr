package com.example.binjr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Movie> movies;
    ArrayList<Movie> userPicks;
    MovieListAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    String apiKey = "k_pv6qgh82";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.movieList);

        movies = new ArrayList<>();
        userPicks = new ArrayList<>();
        adapter = new MovieListAdapter(movies);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        fetchMovies();
    }

    public void fetchMovies() {
        String url = "https://imdb-api.com/API/Top250Movies/" + apiKey;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray movieObjects = response.getJSONArray("items");
                    for (int i = 0; i < movieObjects.length(); i++) {
                        JSONObject movieObject = (JSONObject) movieObjects.get(i);
                        String id = movieObject.getString("id");
                        String title = movieObject.getString("title");
                        Movie movie = new Movie(title, id, getApplicationContext());
                        movies.add(movie);
                        adapter.notifyItemInserted(i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Error", "badness");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "Could not make request");
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}