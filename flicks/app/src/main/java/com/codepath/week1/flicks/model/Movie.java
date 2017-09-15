package com.codepath.week1.flicks.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sanal on 9/12/17.
 */

public class Movie implements Serializable{
    String id;
    Double rating;
    Double popularity;
    String originalTitle;
    String posterPath;
    String overview;
    String backdropPath;

    public Double getPopularity() {
        return popularity;
    }

    public String getId() {
        return id;
    }

    public Double getRating() {
        return rating;
    }

    public String getBackdropPath() {
        String prefix = "https://image.tmdb.org/t/p/w1280";
        return prefix + backdropPath;
    }

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
            this.id = obj.getString("id");
            this.originalTitle = obj.getString("original_title");
            this.posterPath = obj.getString("poster_path");
            this.overview = obj.getString("overview");
            this.backdropPath = obj.getString("backdrop_path");
            this.rating = obj.getDouble("vote_average");
            this.popularity = obj.getDouble("popularity");
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

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", rating=" + rating +
                ", popularity=" + popularity +
                ", originalTitle='" + originalTitle + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", overview='" + overview + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                '}';
    }
}
