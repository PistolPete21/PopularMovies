package com.android.popularmovies.utilites;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.JsonReader;
import android.util.JsonToken;

import java.util.ArrayList;
import java.util.List;

public class Movie extends JsonObject<Movie> implements Parcelable {
    // I used http://www.parcelabler.com/ for this part of the project to help generate the parcelable objects

    private int voteCount;
    private String id;
    private boolean isVideo;
    private float voteAverage;
    private String title;
    private float popularity;
    private String posterPath;
    private String originalLanguage;
    private String originalTitle;
    private List<Integer> genreIds;
    private String backdropPath;
    private boolean isAdult;
    private String overview;
    private String releaseDate;

    Movie() {
    }

    public int getVoteCount() {
        return voteCount;
    }

    private void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public boolean isVideo() {
        return isVideo;
    }

    private void setVideo(boolean video) {
        isVideo = video;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    private void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public float getPopularity() {
        return popularity;
    }

    private void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    private void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    private void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    private void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    private void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    private void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public boolean isAdult() {
        return isAdult;
    }

    private void setAdult(boolean adult) {
        isAdult = adult;
    }

    public String getOverview() {
        return overview;
    }

    private void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public Movie getObject(JsonReader jsonReader) throws Exception {
        Movie movie = new Movie();
        if (jsonReader.peek() != JsonToken.NULL) {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String name = jsonReader.peek() != JsonToken.NULL ? jsonReader.nextName() : "";
                switch (name) {
                    case "vote_count":
                        movie.setVoteCount(jsonReader.nextInt());
                        break;
                    case "id":
                        movie.setId(jsonReader.nextString());
                        break;
                    case "video":
                        movie.setVideo(jsonReader.nextBoolean());
                        break;
                    case "vote_average":
                        String voteAverage = jsonReader.nextString();
                        if (JsonUtils.stringToFloat(voteAverage)) {
                            movie.setVoteAverage(Float.parseFloat(voteAverage));
                        }
                        break;
                    case "title":
                        movie.setTitle(jsonReader.nextString());
                        break;
                    case "popularity":
                        String popularity = jsonReader.nextString();
                        if (JsonUtils.stringToFloat(popularity)) {
                            movie.setPopularity(Float.parseFloat(popularity));
                        }
                        break;
                    case "poster_path":
                        movie.setPosterPath(jsonReader.nextString());
                        break;
                    case "original_language":
                        movie.setOriginalLanguage(jsonReader.nextString());
                        break;
                    case "original_title":
                        movie.setOriginalTitle(jsonReader.nextString());
                        break;
                    case "genre_ids":
                        movie.setGenreIds(JsonUtils.parseIntegerArray(jsonReader));
                        break;
                    case "backdrop_path":
                        movie.setBackdropPath(jsonReader.nextString());
                        break;
                    case "adult":
                        movie.setAdult(jsonReader.nextBoolean());
                        break;
                    case "overview":
                        movie.setOverview(jsonReader.nextString());
                        break;
                    case "release_date":
                        movie.setReleaseDate(jsonReader.nextString());
                        break;
                    default:
                        jsonReader.skipValue();
                        break;
                }
            }
            jsonReader.endObject();
        } else {
            jsonReader.nextNull();
        }
        return movie;
    }

    private Movie(Parcel in) {
        voteCount = in.readInt();
        id = in.readString();
        isVideo = in.readByte() != 0x00;
        voteAverage = in.readFloat();
        title = in.readString();
        popularity = in.readFloat();
        posterPath = in.readString();
        originalLanguage = in.readString();
        originalTitle = in.readString();
        if (in.readByte() == 0x01) {
            genreIds = new ArrayList<Integer>();
            in.readList(genreIds, Integer.class.getClassLoader());
        } else {
            genreIds = null;
        }
        backdropPath = in.readString();
        isAdult = in.readByte() != 0x00;
        overview = in.readString();
        releaseDate = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(voteCount);
        dest.writeString(id);
        dest.writeByte((byte) (isVideo ? 0x01 : 0x00));
        dest.writeFloat(voteAverage);
        dest.writeString(title);
        dest.writeFloat(popularity);
        dest.writeString(posterPath);
        dest.writeString(originalLanguage);
        dest.writeString(originalTitle);
        if (genreIds == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(genreIds);
        }
        dest.writeString(backdropPath);
        dest.writeByte((byte) (isAdult ? 0x01 : 0x00));
        dest.writeString(overview);
        dest.writeString(releaseDate);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
