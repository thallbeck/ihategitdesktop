package JsonClasses;

/*
 * Created by timothy.hallbeck on 4/11/2017.
 * Copyright (c) Hallbeck Pepper Company LLC
 * All rights reserved
 */

public class JsonSpokenLanguage extends JsonData {

    public JsonSpokenLanguage() {
        super();
    }

    public JsonSpokenLanguage( String jsonResult ) {
        super( jsonResult );
    }

    public String get_iso_639_1() {
        return getAsString( "iso_639_1" );
    }

    public String get_name() {
        return getAsString( "name" );
    }

    public void checkAllKeys() {
        get_iso_639_1();
        get_name();
    }

}
