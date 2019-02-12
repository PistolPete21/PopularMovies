package com.android.popularmovies.utilites;

import android.util.JsonReader;
import android.util.JsonToken;

import java.util.List;

import static com.android.popularmovies.utilites.JsonUtils.parseJsonObjectArray;

public class MovieResponse {

    private Integer page = null;
    private Integer totalResults = null;
    private Integer totalPages = null;
    private List<Movie> results = null;

    public Integer getPage() {
        return page;
    }

    private void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    private void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    private void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<Movie> getResults() {
        return results;
    }

    private void setResults(List<Movie> results) {
        this.results = results;
    }

    MovieResponse parseJson(JsonReader jsonReader) {
        MovieResponse response = new MovieResponse();

        try {
            if (jsonReader.peek() != JsonToken.NULL) {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    String name = jsonReader.peek() != JsonToken.NULL ? jsonReader.nextName() : "";
                    switch (name) {
                        case "page":
                            response.setPage(jsonReader.nextInt());
                            break;
                        case "total_results":
                            response.setTotalResults(jsonReader.nextInt());
                            break;
                        case "total_pages":
                            response.setTotalPages(jsonReader.nextInt());
                            break;
                        case "results":
                            response.setResults(parseJsonObjectArray(jsonReader));
                            break;
                    }
                }
                jsonReader.endObject();
            } else {
                jsonReader.skipValue();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return response;
    }
}
