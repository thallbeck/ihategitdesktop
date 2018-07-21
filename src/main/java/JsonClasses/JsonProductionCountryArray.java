package JsonClasses;

/*
 * Created by timothy.hallbeck on 4/11/2017.
 * Copyright (c) Hallbeck Pepper Company LLC
 * All rights reserved
 */

import com.google.gson.JsonArray;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class JsonProductionCountryArray extends JsonData implements Iterable<JsonProductionCountry>, Iterator<JsonProductionCountry> {

    public JsonProductionCountryArray() {
    }

    public JsonProductionCountryArray( String jsonResult ) {
        super( jsonResult, "" );
    }

    public JsonProductionCountryArray( JsonArray array ) {
        super( array );
    }

    public JsonProductionCountryArray getAllCountries() {
        return new JsonProductionCountryArray( jsonArray );
    }

    public JsonProductionCountry getProductionCountry( int index ) {
        return new JsonProductionCountry( jsonArray.get( index ).getAsJsonObject().toString() );
    }

    public void checkAllKeys() {
        for ( JsonProductionCountry resource : getAllCountries() )
            resource.checkAllKeys();
    }

    private int cursor;
    private int size;

    @Override
    public boolean hasNext() {
        return cursor < size;
    }

    @Override
    public JsonProductionCountry next() {
        if ( this.hasNext() ) {
            int current = cursor;
            cursor++;

            return new JsonProductionCountry( jsonArray.get( current ).getAsJsonObject().toString() );
        }
        throw new NoSuchElementException();
    }

    @Override
    public Iterator<JsonProductionCountry> iterator() {
        cursor = 0;

        if ( jsonArray == null )
            size = 0;
        else
            size = jsonArray.size();

        return this;
    }

}
