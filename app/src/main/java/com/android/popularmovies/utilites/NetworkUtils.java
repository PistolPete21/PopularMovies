package com.android.popularmovies.utilites;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.JsonReader;
import android.util.Log;

import com.android.popularmovies.BuildConfig;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {

    /* The base url that our API uses */
    private static final String BASE_MOVIE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String URL_PARAMETER_MOST_POPULAR = "popular?";
    private static final String URL_PARAMETER_TOP_RATED = "top_rated?";

    private static final String API_KEY_PARAMETER = "api_key";
    private static final String API_KEY_VALUE = BuildConfig.ApiKey;

    public URL buildUrl(Enum sortOrder) {
        URL url = null;

        if (sortOrder.name().equalsIgnoreCase("TOP_RATED")) {
            Uri builtUri = Uri.parse(BASE_MOVIE_URL + URL_PARAMETER_TOP_RATED).buildUpon()
                    .appendQueryParameter(API_KEY_PARAMETER, API_KEY_VALUE)
                    .build();

            try {
                url = new URL(builtUri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            Log.v("buildUrl()", "Built URI " + url);
        } else {
            Uri builtUri = Uri.parse(BASE_MOVIE_URL + URL_PARAMETER_MOST_POPULAR).buildUpon()
                    .appendQueryParameter(API_KEY_PARAMETER, API_KEY_VALUE)
                    .build();

            try {
                url = new URL(builtUri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            Log.v("buildUrl()", "Built URI " + url);
        }

        return url;
    }

    public static MovieResponse getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStreamReader isr = new InputStreamReader(urlConnection.getInputStream());

            JsonReader jsonReader = new JsonReader(isr);

            return new MovieResponse().parseJson(jsonReader);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return null;
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
}
