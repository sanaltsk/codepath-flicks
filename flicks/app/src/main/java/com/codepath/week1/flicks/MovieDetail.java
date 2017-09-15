package com.codepath.week1.flicks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.week1.flicks.model.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();
        Movie movie = (Movie) intent.getSerializableExtra("movie");
        Log.d("debug",movie.toString());

        ImageView poster = (ImageView) findViewById(R.id.poster);
        Picasso.with(this.getApplicationContext())
                .load(movie.getBackdropPath())
                .placeholder(R.drawable.placeholder)
                .into(poster);
        RatingBar rating = (RatingBar) findViewById(R.id.rating);
        rating.setNumStars(5);
        rating.setRating((int)((movie.getRating()/10)*5));

        TextView tvTitle = (TextView) findViewById(R.id.title);
        tvTitle.setText(movie.getOriginalTitle());

        TextView tvOverview = (TextView) findViewById(R.id.overview);
        tvOverview.setText(movie.getOverview());
    }
}
