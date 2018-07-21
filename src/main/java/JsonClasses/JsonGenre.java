package JsonClasses;

/*
 * Created by timothy.hallbeck on 4/11/2017.
 * Copyright (c) Hallbeck Pepper Company LLC
 * All rights reserved
 */

public class JsonGenre extends JsonData {

    public JsonGenre() {
        super();
    }

    public JsonGenre( String jsonResult ) {
        super( jsonResult );
    }

    public String get_id() {
        return getAsString( "id" );
    }

    public String get_name() {
        return getAsString( "name" );
    }

    public void checkAllKeys() {
        get_id();
        get_name();
    }

}
