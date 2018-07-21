package JsonClasses;

/*
 * Created by timothy.hallbeck on 4/11/2017.
 * Copyright (c) Hallbeck Pepper Company LLC
 * All rights reserved
 */

import com.google.gson.JsonArray;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class JsonGenreArray extends JsonData implements Iterable<JsonGenre>, Iterator<JsonGenre> {

    public JsonGenreArray() {}
    public JsonGenreArray( String jsonResult) {
        super(jsonResult, "");
    }
    public JsonGenreArray( JsonArray array) {
        super(array);
    }

    public JsonGenreArray getAllActivities() {
        return new JsonGenreArray(jsonArray);
    }

    public JsonGenre getGenre( int index) {
        return new JsonGenre(jsonArray.get(index).getAsJsonObject().toString());
    }

    public void checkAllKeys() {
        for ( JsonGenre resource : getAllActivities())
            resource.checkAllKeys();
    }

    private int cursor;
    private int size;

    @Override
    public boolean hasNext() {
        return cursor < size;
    }

    @Override
    public JsonGenre next() {
        if(this.hasNext()) {
            int current = cursor;
            cursor ++;

            return new JsonGenre(jsonArray.get(current).getAsJsonObject().toString());
        }
        throw new NoSuchElementException();
    }

    @Override
    public Iterator<JsonGenre> iterator() {
        cursor = 0;

        if (jsonArray == null)
            size = 0;
        else
            size = jsonArray.size();

        return this;
    }

}
