package JsonClasses;

/*
 * Created by timothy.hallbeck on 4/11/2017.
 * Copyright (c) Hallbeck Pepper Company LLC
 * All rights reserved
 */

public class JsonBelongsToCollection extends JsonData {

    public JsonBelongsToCollection() {
        super();
    }

    public JsonBelongsToCollection( String jsonResult ) {
        super( jsonResult );
    }

    public String get_backdrop_path() {
        return getAsString("backdrop_path");
    }

    public String get_id() {
        return getAsString("id");
    }

    public String get_name() {
        return getAsString("name");
    }

    public String get_poster_path() {
        return getAsString("poster_path");
    }

    public void checkAllKeys() {
        get_backdrop_path();
        get_id();
        get_name();
        get_poster_path();
    }

}
