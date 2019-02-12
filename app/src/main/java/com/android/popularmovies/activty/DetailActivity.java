package com.android.popularmovies.activty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.popularmovies.R;
import com.android.popularmovies.utilites.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView movieTitle = findViewById(R.id.movie_title);

        String BASE_POSTER_URL = "https://image.tmdb.org/t/p/w185/";
        ImageView imageView = findViewById(R.id.movie_thumbnail);

        TextView movieYear = findViewById(R.id.movie_release_date);
        TextView movieRating = findViewById(R.id.movie_rating);
        TextView moviePlotSynopsis = findViewById(R.id.movie_plot_synopsis);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                Movie movie = intentThatStartedThisActivity.getParcelableExtra(Intent.EXTRA_TEXT);
                /*Set the movie title*/
                movieTitle.setText(movie.getTitle());

                /*Add image to ImageView*/
                Picasso.get().load(BASE_POSTER_URL + movie.getPosterPath()).placeholder(R.drawable.ic_launcher_background).into(imageView);

                /*Set movie details*/
                String str[] = movie.getReleaseDate().split("-");
                String year = str[0];
                movieYear.setText(year);

                String movieRatingString = Float.toString(movie.getVoteAverage());
                movieRating.setText(String.format("%s/10", movieRatingString));

                moviePlotSynopsis.setText(movie.getOverview());
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // I wanted a way to make sure the list stayed the same when the user hit the up navigation button.
            // This code is from here : https://alvinalexander.com/source-code/android/how-get-android-actionbar-backup-button-work-android-back-button
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
