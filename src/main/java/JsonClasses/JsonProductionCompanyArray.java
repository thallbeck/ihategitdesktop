package JsonClasses;

/*
 * Created by timothy.hallbeck on 4/11/2017.
 * Copyright (c) Hallbeck Pepper Company LLC
 * All rights reserved
 */

import com.google.gson.JsonArray;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class JsonProductionCompanyArray extends JsonData implements Iterable<JsonProductionCompany>, Iterator<JsonProductionCompany> {

    public JsonProductionCompanyArray() {}
    public JsonProductionCompanyArray( String jsonResult) {
        super(jsonResult, "");
    }
    public JsonProductionCompanyArray( JsonArray array) {
        super(array);
    }

    public JsonProductionCompanyArray getAllProductionCompanies() {
        return new JsonProductionCompanyArray(jsonArray);
    }

    public JsonGenre getGenre( int index) {
        return new JsonGenre(jsonArray.get(index).getAsJsonObject().toString());
    }

    public void checkAllKeys() {
        for ( JsonProductionCompany resource : getAllProductionCompanies())
            resource.checkAllKeys();
    }

    private int cursor;
    private int size;

    @Override
    public boolean hasNext() {
        return cursor < size;
    }

    @Override
    public JsonProductionCompany next() {
        if(this.hasNext()) {
            int current = cursor;
            cursor ++;

            return new JsonProductionCompany(jsonArray.get(current).getAsJsonObject().toString());
        }
        throw new NoSuchElementException();
    }

    @Override
    public Iterator<JsonProductionCompany> iterator() {
        cursor = 0;

        if (jsonArray == null)
            size = 0;
        else
            size = jsonArray.size();

        return this;
    }

}
