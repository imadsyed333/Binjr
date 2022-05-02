package com.example.binjr;

import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Movie {

    String title;
    String id;
    String posterUrl;

    public Movie (String title, String id, String posterUrl) {
        this.title = title;
        this.id = id;
        this.posterUrl = posterUrl;
    }

    public String getTitle() {
        return this.title;
    }

    public String getId() {
        return this.id;
    }

    public String getPosterUrl() {
        return this.posterUrl;
    }

    public Drawable getPoster() {
        try {
            InputStream inputStream = (InputStream) new URL(this.posterUrl).getContent();
            return Drawable.createFromStream(inputStream, "posterImage");
        } catch (IOException e) {
            Log.d("Error", "Image could not be extracted");
            return null;
        }
    }
}
