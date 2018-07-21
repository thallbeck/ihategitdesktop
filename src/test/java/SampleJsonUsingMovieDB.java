
/*
 * Created on 8/25/2017.
 */

import JsonClasses.JsonMovie;
import Utils.General;
import Utils.SimpleHttpGet;
import Utils.TimerWrapper;
import org.testng.TestNGException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SampleJsonUsingMovieDB {

    static String api_key = "api_key=661e370b612ec8e941f4b6366e128e7d";

    public static synchronized void setApiKey( String key ) {
        api_key = key;
    }

    @DataProvider( parallel = false, name = "PositiveTestUrls" )
    public static Object[][] PositiveTestUrls() {
        return new Object[][] {
            {"https://api.themoviedb.org/3/movie/551?"},
            {"https://api.themoviedb.org/3/movie/550?"},
            {"https://api.themoviedb.org/3/movie/500?"},
        };
    }

    @Test( dataProvider = "PositiveTestUrls" )
    public void runPositiveTests( String url ) throws Exception {

        String response = new SimpleHttpGet().get( url + api_key );
        General.Debug( response );

        JsonMovie movie = new JsonMovie( response );
        String id = movie.get_id();
        String budget = movie.get_budget();

        General.Debug( "all done" );

    }


    @DataProvider( parallel = false, name = "NegativeTestUrls" )
    public static Object[][] NegativeTestUrls() {
        return new Object[][] {
            {"https://api.themoviedb.org/7/movie/551?", null, FileNotFoundException.class}, // invalid path, invalid key
            {"https://api.themoviedb.org/movie/550?", api_key, FileNotFoundException.class}, // invalid path, valid key
            {"https://api.themoviedb.org/3/movie/550?", null, IOException.class}, // valid path, invalid key
            {"https://api.themoviedb.org/500?", api_key, FileNotFoundException.class}, // invalid path, valid key
            {"https://api.themoviedb.org/500?", null, FileNotFoundException.class}, // invalid path, invalid key
            {"https://api.themoviedb.org/3/movie/544451?", api_key, FileNotFoundException.class}, // valid path, valid key, invalid movie id
        };
    }

    @Test( dataProvider = "NegativeTestUrls" )
    public void runNegativeTests( String url, String key, Class expectedExceptionClass ) throws Exception {
        try {
            String response = new SimpleHttpGet().get( url + key );
            throw new TestNGException( "No exception thrown" );
        } catch ( Exception e ) {
            if ( e.getClass().equals( expectedExceptionClass ) )
                General.Debug( "expected exception thrown: " + e.getClass().getCanonicalName() );
            else
                throw new TestNGException( "Expected " + expectedExceptionClass.toString() + " but got " + e.getClass().toString() + " instead" );
        }
    }


}
