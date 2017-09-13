package com.codepath.week1.flicks.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sanal on 9/12/17.
 */

public class Movie {
    String originalTitle;
    String posterPath;
    String overview;

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPosterPath() {
        String prefix = "https://image.tmdb.org/t/p/w342";
        return prefix + posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public Movie(JSONObject obj) {
        try {
            this.originalTitle = obj.getString("original_title");
            this.posterPath = obj.getString("poster_path");
            this.overview = obj.getString("overview");
        }catch(JSONException e) {
          e.printStackTrace();
        }
    }

    public static ArrayList<Movie> fromJsonArray(JSONArray array) {
        ArrayList<Movie> movies = new ArrayList<>();
        for(int i=0;i<array.length();i++)
        {
            try {
                movies.add(new Movie(array.getJSONObject(i)));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return movies;
    }

}
