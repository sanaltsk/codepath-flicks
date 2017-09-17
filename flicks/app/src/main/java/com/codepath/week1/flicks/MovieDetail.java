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

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MovieDetail extends AppCompatActivity {
    @BindView(R.id.poster) ImageView poster;
    @BindView(R.id.rating) RatingBar rating;
    @BindView(R.id.title) TextView tvTitle;
    @BindView(R.id.overview) TextView tvOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Movie movie = (Movie) intent.getSerializableExtra("movie");
        Log.d("debug",movie.toString());

        Picasso.with(this.getApplicationContext())
                .load(movie.getBackdropPath())
                .transform(new RoundedCornersTransformation(10, 10))
                .placeholder(R.drawable.placeholder)
                .into(poster);
        rating.setNumStars(5);
        rating.setRating((int)((movie.getRating()/10)*5));
        tvTitle.setText(movie.getOriginalTitle());
        tvOverview.setText(movie.getOverview());
    }
}
