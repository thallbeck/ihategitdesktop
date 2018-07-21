
package Utils;

import java.util.TreeMap;

/*
 * Created by timothy.hallbeck on 4/11/2017.
 * Copyright (c) Hallbeck Pepper Company LLC
 * All rights reserved
 */

public class StringUtils {
    StringUtils() {
    }

    static public String cleanoutSpecialChars( String url ) {
        String filename = url;
        filename = filename.replace( "http://", "" );
        filename = filename.replace( "https://", "" );

        filename = filename.replace( "!", "_" );
        filename = filename.replace( "#", "_" );
        filename = filename.replace( "$", "_" );
        filename = filename.replace( "%", "_" );
        filename = filename.replace( "^", "_" );
        filename = filename.replace( "&", "_" );
        filename = filename.replace( "*", "_" );
        filename = filename.replace( "(", "_" );
        filename = filename.replace( ")", "_" );
        filename = filename.replace( ":", "_" );
        filename = filename.replace( ";", "_" );
        filename = filename.replace( ".", "_" );
        filename = filename.replace( ",", "_" );
        filename = filename.replace( "/", "_" );
        filename = filename.replace( "?", "_" );
        filename = filename.replace( "=", "_" );

        return filename;
    }

    static public void addMapToMap( TreeMap destMap, TreeMap sourceMap ) {
        Object[] array = sourceMap.keySet().toArray();

        for ( Object object : array ) {
            String value = object.toString();
            if ( !destMap.containsKey( value ) ) {
                destMap.put( value, new TreeMap() );
                General.Debug( "Adding to master: " + value );
            }
        }
    }

    static public String ellisize( String string, int maxLength ) {
        return string.length() > maxLength ? string.substring( 0, maxLength ) + "..." : string;
    }

    static public String removeNewlines( String string ) {
        return string.replace( "\n", "(newline)" );
    }

    static public String indent( int count ) {
        return String.format( "%1$-" + count + "s", "" );
    }

    public static String padRight( String s, int n ) {
        return String.format( "%1$-" + n + "s", s );
    }

    public static String padLeft( String s, int n ) {
        return String.format( "%1$" + n + "s", s );
    }

    public static boolean verifyEqual( String one, String two ) {
        if ( one.contentEquals( two ) )
            return true;
        return false;
    }

    public static boolean verifyEqual( String one, String two, String three ) {
        if ( one.contentEquals( two ) && two.contentEquals( three ) )
            return true;
        return false;
    }

    public static String getTextBetweenStrings( String sourceString, String leftBookend, String rightBookend ) {
        String middle = sourceString;

        int left, right;
        left = sourceString.indexOf( leftBookend ) + leftBookend.length() - 1;
        right = sourceString.indexOf( rightBookend, left );

        if ( left == -1 || right == -1 || left >= right )
            return "";

        middle = middle.substring( left + 1, right - 1 ).trim();

        return middle;
    }
}
