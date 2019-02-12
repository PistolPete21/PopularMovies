package com.android.popularmovies.utilites;

import android.util.JsonReader;
import android.util.JsonToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    static List<Integer> parseIntegerArray(JsonReader jsonReader) throws IOException {
        List<Integer> integerList = new ArrayList<>();

        if(jsonReader.peek() != JsonToken.NULL) {
            jsonReader.beginArray();
            if (jsonReader.peek() != JsonToken.NULL) {
                while (jsonReader.hasNext()) {
                    integerList.add(jsonReader.nextInt());
                }
            }
            jsonReader.endArray();
        } else {
            jsonReader.nextNull();
        }

        return integerList;
    }

    static List<Movie> parseJsonObjectArray(JsonReader jsonReader) throws IOException {
        List<Movie> list = new ArrayList<>();

        if(jsonReader.peek() != JsonToken.NULL) {
            jsonReader.beginArray();
            if (jsonReader.peek() != JsonToken.NULL) {
                while (jsonReader.hasNext()) {
                    try {
                        list.add(new Movie().getObject(jsonReader));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            jsonReader.endArray();
        } else {
            jsonReader.nextNull();
        }

        return list;
    }

    static boolean stringToFloat(String value) {
        try {
            Float.parseFloat(value);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
