package JsonClasses;

/*
 * Created by timothy.hallbeck on 4/11/2017.
 * Copyright (c) Hallbeck Pepper Company LLC
 * All rights reserved
 */

public class JsonMovie extends JsonData {

    public JsonMovie() {
        super();
    }

    public JsonMovie( String jsonResult ) {
        super( jsonResult );
    }

    public Boolean get_adult() {
        return Boolean.valueOf( getAsString( "adult" ) );
    }

    public String get_backdrop_path() {

        int i = Integer.parseInt( "25" );
        return getAsString( "backdrop_path" );
    }

    public JsonBelongsToCollection get_belongs_to_collection() {
        Object object = getAsObject( "belongs_to_collection" );
        if (object == null)
            return null;
        String objectAsString = getAsObject( "belongs_to_collection" ).toString();
        return new JsonBelongsToCollection( objectAsString );

    }

    public String get_budget() {
        return getAsString( "budget" );
    }

    public JsonGenreArray get_genres() {
        String arrayAsString = getAsArray( "genres" ).toString();
        return new JsonGenreArray( arrayAsString );
    }

    public String get_homepage() {
        return getAsString( "homepage" );
    }

    public String get_id() {

        return getAsString( "id" );
    }

    public String get_imdb_id() {
        return getAsString( "imdb_id" );
    }

    public String get_original_language() {
        return getAsString( "original_language" );
    }

    public String get_original_title() {
        return getAsString( "original_title" );
    }

    public String get_overview() {
        return getAsString( "overview" );
    }

    public String get_popularity() {
        return getAsString( "popularity" );
    }

    public String get_poster_path() {
        return getAsString( "poster_path" );
    }

    public JsonProductionCompanyArray get_production_companies() {
        String arrayAsString = getAsArray( "production_companies" ).toString();
        return new JsonProductionCompanyArray( arrayAsString );
    }

    public JsonProductionCountryArray get_production_countries() {
        String arrayAsString = getAsArray( "production_countries" ).toString();
        return new JsonProductionCountryArray( arrayAsString );
    }

    public String get_release_date() {
        return getAsString( "release_date" );
    }

    public String get_revenue() {
        return getAsString( "revenue" );
    }

    public String get_runtime() {
        return getAsString( "runtime" );
    }

    public JsonSpokenLanguageArray get_spoken_languages() {
        String arrayAsString = getAsArray( "spoken_languages" ).toString();
        return new JsonSpokenLanguageArray( arrayAsString );
    }

    public String get_status() {
        return getAsString( "status" );
    }

    public String get_tagline() {
        return getAsString( "tagline" );
    }

    public String get_title() {
        return getAsString( "title" );
    }

    public String get_video() {
        return getAsString( "video" );
    }

    public String get_vote_average() {
        return getAsString( "vote_average" );
    }

    public String get_vote_count() {
        return getAsString( "vote_count" );
    }

    public void checkAllKeys() {
        get_adult();
        get_backdrop_path();
        get_belongs_to_collection();
        get_budget();
        get_genres();
        get_homepage();
        get_id();
        get_imdb_id();
        get_original_language();
        get_original_title();
        get_overview();
        get_popularity();
        get_poster_path();
        get_production_companies();
        get_production_countries();
        get_release_date();
        get_revenue();
        get_runtime();
        get_spoken_languages();
        get_status();
        get_tagline();
        get_title();
        get_video();
        get_vote_average();
        get_vote_count();
    }

}
