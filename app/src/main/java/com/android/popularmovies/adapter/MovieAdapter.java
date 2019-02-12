package com.android.popularmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.popularmovies.R;
import com.android.popularmovies.utilites.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.support.v7.widget.RecyclerView.*;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    private List<Movie> results;

    private final MovieAdapterOnClickHandler clickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public MovieAdapter(MovieAdapterOnClickHandler movieAdapterOnClickHandler) {
        clickHandler = movieAdapterOnClickHandler;
    }

    public class MovieAdapterViewHolder extends ViewHolder implements OnClickListener {
        private final ImageView moviePosterImageView;

        private MovieAdapterViewHolder(View view) {
            super(view);
            moviePosterImageView = view.findViewById(R.id.movie_poster);
            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie selectedMovie = results.get(adapterPosition);
            clickHandler.onClick(selectedMovie);
        }
    }

    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        String moviePosterPath = results.get(position).getPosterPath();
        String BASE_POSTER_URL = "https://image.tmdb.org/t/p/w185/";
        Picasso.get()
                .load(BASE_POSTER_URL + moviePosterPath)
                .placeholder(R.drawable.ic_launcher_background)
                .into(movieAdapterViewHolder.moviePosterImageView);
        System.out.println(BASE_POSTER_URL + moviePosterPath);
    }

    @Override
    public int getItemCount() {
        if (null == results) return 0;
        return results.size();
    }

    public void setMovieData(List<Movie> movieResults) {
        results = movieResults;
        notifyDataSetChanged();
    }
}
