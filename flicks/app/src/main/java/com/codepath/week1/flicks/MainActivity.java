package com.codepath.week1.flicks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.week1.flicks.adapters.MovieArrayAdapter;
import com.codepath.week1.flicks.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    ArrayList<Movie> movies;
    MovieArrayAdapter movieAdapter;

    @BindView(R.id.lvMovies) ListView lvMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        movies = new ArrayList<>();
        movieAdapter = new MovieArrayAdapter(this, movies);
        lvMovies.setAdapter(movieAdapter);


        // should be a singleton
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        Request request = new Request.Builder()
                .url(url)
                .build();
//        AsyncHttpClient client = new AsyncHttpClient();
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
                    movies.addAll(Movie.fromJsonArray(movieJsonResults));
                    Log.d("debug",movies.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Run view-related code back on the main thread
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            movieAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("debug","Navigating to movie detail " + movies.get(position).getOriginalTitle());

                Movie movie = (Movie)parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(),MovieDetail.class);
                intent.putExtra("movie", (Serializable) movie);
                startActivity(intent);
            }
        });
    }
}
