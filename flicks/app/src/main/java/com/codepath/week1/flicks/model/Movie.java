package com.codepath.week1.flicks.model;

import android.util.Log;

import com.codepath.week1.flicks.MainActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
    String trailerUrl;

    public String getTrailerUrl() {
        return trailerUrl;
    }

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
            if(this.rating>5.0) {
                getTrailerUrl(this);
            }
        }catch(JSONException e) {
          e.printStackTrace();
        }
    }

    private void getTrailerUrl(final Movie movieObj) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.themoviedb.org/3/movie/" + this.id + "/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US";
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JSONArray movieJsonResults =null;
                try {
                    String responseData = response.body().string();
                    Log.d("debug", "Resp Data "+responseData);
                    JSONObject obj = new JSONObject(responseData);
                    movieJsonResults = obj.getJSONArray("results");
                    JSONObject movie= (JSONObject)movieJsonResults.get(0);
                    String youtubeId = (String) movie.get("key");
                    if(youtubeId!=null || youtubeId.length()>0) {
                        Movie.this.trailerUrl = youtubeId;
                        Log.d("debug","youtubeid" + Movie.this.trailerUrl );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

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
                ", tailerUrl='" + trailerUrl + '\'' +
                '}';
    }
}
