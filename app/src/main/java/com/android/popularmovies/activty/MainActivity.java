package com.android.popularmovies.activty;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.popularmovies.R;
import com.android.popularmovies.adapter.MovieAdapter;
import com.android.popularmovies.utilites.Movie;
import com.android.popularmovies.utilites.MovieResponse;
import com.android.popularmovies.utilites.NetworkUtils;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.android.popularmovies.activty.MainActivity.SortOrder.MOST_POPULAR;
import static com.android.popularmovies.activty.MainActivity.SortOrder.TOP_RATED;

public class MainActivity extends AppCompatActivity implements  MovieAdapter.MovieAdapterOnClickHandler {

    protected enum SortOrder { MOST_POPULAR, TOP_RATED }

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;

    private TextView errorMessage;
    private ProgressBar loadingIndicator;

    private List<Movie> results = new ArrayList<>();
    private Boolean topRated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview_movies);
        errorMessage = findViewById(R.id.error_message);
        loadingIndicator = findViewById(R.id.loading_indicator);

        int NUMBER_OF_COLUMNS = 2;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, NUMBER_OF_COLUMNS, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);

        movieAdapter = new MovieAdapter(this);

        recyclerView.setAdapter(movieAdapter);

        loadMovieData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_by_most_popular:
                sortBy(MOST_POPULAR);
                return true;
            case R.id.action_sort_by_top_rated:
                sortBy(TOP_RATED);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadMovieData() {
        if (NetworkUtils.isOnline(this)) {
            showMovieGridView();
            new FetchMovieData(this).execute();
        } else {
            showConnectToNetworkMessage();
        }
    }

    @Override
    public void onClick(Movie movie) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, movie);
        startActivity(intentToStartDetailActivity);
    }

    private void sortBy(SortOrder sortOrder) {
        if (results == null || results.size() <= 0) {
            return;
        }

        switch (sortOrder) {
            case MOST_POPULAR:
                topRated = false;
                loadMovieData();
                break;
            case TOP_RATED:
                topRated = true;
                loadMovieData();
                break;
            default:
                break;
        }

        movieAdapter.setMovieData(results);
    }

    private void showMovieGridView() {
        /* First, make sure the error is invisible */
        errorMessage.setVisibility(View.INVISIBLE);
        /* Then, make sure the movie data is visible */
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        /* First, hide the currently visible data */
        recyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        errorMessage.setText(R.string.error_message);
        errorMessage.setVisibility(View.VISIBLE);
    }

    private void showConnectToNetworkMessage() {
        /* First, hide the currently visible data */
        recyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        errorMessage.setText(R.string.network_message);
        errorMessage.setVisibility(View.VISIBLE);
    }

    private static class FetchMovieData extends AsyncTask<String, Void, List<Movie>> {

        private WeakReference<MainActivity> weakReference;

        FetchMovieData(MainActivity context) {
            weakReference = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakReference.get().loadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(String... params) {

            NetworkUtils networkUtils = new NetworkUtils();
            URL movieRequestUrl;
            if (weakReference.get().topRated) {
                movieRequestUrl = networkUtils.buildUrl(TOP_RATED);
            } else {
                movieRequestUrl = networkUtils.buildUrl(MOST_POPULAR);
            }

            try {
                MovieResponse response = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);

                if (response != null) {
                    weakReference.get().results = response.getResults();
                }

                return weakReference.get().results;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Movie> results) {
            weakReference.get().loadingIndicator.setVisibility(View.INVISIBLE);
            if (results != null) {
                weakReference.get().showMovieGridView();
                weakReference.get().movieAdapter.setMovieData(results);
            } else {
                weakReference.get().showErrorMessage();
            }
        }
    }
}
