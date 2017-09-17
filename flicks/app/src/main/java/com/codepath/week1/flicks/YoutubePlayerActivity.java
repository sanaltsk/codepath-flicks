package com.codepath.week1.flicks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.codepath.week1.flicks.model.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubePlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener  {
    Movie movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);
        Intent intent = getIntent();
        movie = (Movie) intent.getSerializableExtra("movie");
        Log.d("debug",movie.toString());

        if(movie.getRating()>5.0) {
            YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
            youTubeView.initialize(Config.API_KEY, this);
        }
    }
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            youTubePlayer.setFullscreen(true);
            youTubePlayer.cueVideo(movie.getTrailerUrl());
            youTubePlayer.play();
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "not loaded", Toast.LENGTH_LONG).show();
        Log.d("ss",youTubeInitializationResult.toString());
    }
}

