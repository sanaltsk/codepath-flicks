package com.codepath.week1.flicks.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.week1.flicks.R;
import com.codepath.week1.flicks.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static android.R.attr.rating;
import static android.R.attr.type;
import static com.codepath.week1.flicks.R.id.tvOverview;
import static com.codepath.week1.flicks.R.id.tvTitle;

/**
 * Created by sanal on 9/13/17.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie>{
    String imageUri = null;

    //View lookup cache
   static class ViewHolder {
        @BindView(R.id.ivMovieImage) ImageView ivImage;
        @Nullable @BindView(R.id.tvTitle) TextView tvTitle;
        @Nullable @BindView(R.id.tvOverview) TextView tvOverview;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //get movie data
        Movie movie = getItem(position);
        ViewHolder viewHolder;
        int orientation = getContext().getResources().getConfiguration().orientation;

        //check the existing view being reused
        if(convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            int type = getItemViewType(position);
            convertView = getInflatedLayoutForType(type);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //find image view (done by butterknife)
        //clear out image view
        viewHolder.ivImage.setImageResource(0);

        if(movie.getRating()<5.0) {
            viewHolder.tvTitle.setText(movie.getOriginalTitle());
            viewHolder.tvOverview.setText(movie.getOverview());
        }
        //Switch image used based on the orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE || movie.getRating() > 5) {
            imageUri = movie.getBackdropPath();
        } else {
            imageUri = movie.getPosterPath();
        }

        Picasso.with(getContext())
                .load(imageUri)
                .placeholder(R.drawable.placeholder)
                .transform(new RoundedCornersTransformation(10, 10))
                .into(viewHolder.ivImage);
        return convertView;
    }

    private View getInflatedLayoutForType(int type) {
        if(type==0) {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_movie,null);
        }else {
            return LayoutInflater.from(getContext()).inflate(R.layout.popular_movie, null);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(getItem(position).getRating()<5.0){
            return 0;
        }
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
}
