package JsonClasses;

/*
 * Created by timothy.hallbeck on 4/11/2017.
 * Copyright (c) Hallbeck Pepper Company LLC
 * All rights reserved
 */

import com.google.gson.JsonArray;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class JsonSpokenLanguageArray extends JsonData implements Iterable<JsonSpokenLanguage>, Iterator<JsonSpokenLanguage> {

    public JsonSpokenLanguageArray() {}
    public JsonSpokenLanguageArray( String jsonResult) {
        super(jsonResult, "");
    }
    public JsonSpokenLanguageArray( JsonArray array) {
        super(array);
    }

    public JsonSpokenLanguageArray getAllSpokenLanguages() {
        return new JsonSpokenLanguageArray(jsonArray);
    }

    public JsonSpokenLanguage getSpokenLanguage( int index) {
        return new JsonSpokenLanguage(jsonArray.get(index).getAsJsonObject().toString());
    }

    public void checkAllKeys() {
        for ( JsonSpokenLanguage resource : getAllSpokenLanguages())
            resource.checkAllKeys();
    }

    private int cursor;
    private int size;

    @Override
    public boolean hasNext() {
        return cursor < size;
    }

    @Override
    public JsonSpokenLanguage next() {
        if(this.hasNext()) {
            int current = cursor;
            cursor ++;

            return new JsonSpokenLanguage(jsonArray.get(current).getAsJsonObject().toString());
        }
        throw new NoSuchElementException();
    }

    @Override
    public Iterator<JsonSpokenLanguage> iterator() {
        cursor = 0;

        if (jsonArray == null)
            size = 0;
        else
            size = jsonArray.size();

        return this;
    }

}
