
package Utils;

import org.testng.SkipException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/*
 * Created by timothy.hallbeck on 4/11/2017.
 * Copyright (c) Hallbeck Pepper Company LLC
 * All rights reserved
 */

public class General {

    // Global data objects
    private static boolean silentMode = false;
    private static boolean detailedDebugMode = false;
    private static int initialStackDepth=1;
    private static Random randomGenerator = new Random( System.currentTimeMillis() );

    public static String DATA_PATH = "/Users/Public/Documents/testautomation/";

    General() {
        initialStackDepth = Thread.currentThread().getStackTrace().length;
    }

    // return previous state
    static public boolean setSilentMode( boolean mode ) {
        boolean prev = silentMode;
        silentMode = mode;
        return prev;
    }

    static public boolean isSilentMode() {
        return silentMode;
    }

    static public boolean setDetailedDebugMode( boolean mode ) {
        boolean prev = detailedDebugMode;
        detailedDebugMode = mode;
        return prev;
    }

    static public boolean getDetailedDebugMode() {
        return detailedDebugMode;
    }

    static public int getStackDepth() {
        return Integer.max( Thread.currentThread().getStackTrace().length - initialStackDepth - 30, 1 );
    }

    /*static int cursorPos = 0;

        static public void MarkOutputCursor() {
            if (outputWindow != null)
                cursorPos = outputWindow.getLength();
        }

        static public void RestoreOutputCursor() {
            if (outputWindow != null)
                outputWindow.positionCaret(cursorPos);
        }

        static public void SetOutputWindow(TextInputControl output) {
            outputWindow = output;
        }

        static public void SendTextToOutputWindow(String text) {
            if (outputWindow == null)
                return;

            String current = outputWindow.getText();
            outputWindow.setText(current + text + "\n");
            General.RestoreOutputCursor();
            General.MarkOutputCursor();
        }
    */
    static public boolean DebugAndOutputWindow( String info ) {
//        SendTextToOutputWindow(info);
        return Debug( info, false );
    }

    static public boolean Debug( List<String> list ) {
        if ( silentMode )
            return false;
        for ( String string : list )
            Debug( string, false );
        return (true);
    }

    static public String ReadTextFile( String fileName ) {
        try {
            String contents = "", line;
            FileReader fileReader = new FileReader( fileName );
            BufferedReader bufferedReader = new BufferedReader( fileReader );
            while ( (line = bufferedReader.readLine()) != null ) {
                contents += line;
            }

            // Always close files.
            bufferedReader.close();
            return contents;
        } catch ( FileNotFoundException e ) {
            Debug( e.toString() );
        } catch ( java.io.IOException e ) {
            Debug( e.toString() );
        }
        return "";
    }

    static public boolean DebugFromFile( String fileName ) {
        return Debug( ReadTextFile( fileName ) );
    }

    static public boolean Debug( String info ) {
        return Debug( info, false );
    }

    static public boolean Debug( int info ) {

        return Debug( String.valueOf( info ), false );
    }

    static public boolean Debug( long info ) {
        return Debug( String.valueOf( info ), false );
    }

    static public boolean Debug( String info, boolean bAddBulletAlso ) {
        if ( silentMode )
            return false;

        System.out.println( info );
        System.out.flush();

        if ( bAddBulletAlso )
            PowerPointUtil.addBulletPoint( info );

        return true;
    }

    static public void assertDebug( boolean expression, String debugText ) {
        assert (expression);
        PowerPointUtil.addBulletPoint( debugText );
        if ( !silentMode )
            System.out.println( debugText );
    }

    static public void SkipTest( String text ) {
        General.Sleep( 250 );
        throw new SkipException( text );
    }

    static public int getRandomInt( int maxNumberExclusive ) {
        if ( maxNumberExclusive <= 0 )
            return 0;
        else
            return randomGenerator.nextInt( maxNumberExclusive );
    }

    static public boolean Sleep( long milliseconds ) {
        try {
            Thread.sleep( milliseconds );
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }

        return true;
    }

    static public void executePageMethod( WebPage page, String methodName ) {
        java.lang.reflect.Method method = null;

        try {
            method = page.getClass().getMethod( methodName );
        } catch ( SecurityException e ) {
            Debug( "SecurityException" );
        } catch ( NoSuchMethodException e ) {
            Debug( "NoSuchMethodException" );
        }

        assert (method != null);
        try {
            method.invoke( page );
        } catch ( IllegalArgumentException e ) {
            Debug( "IllegalArgumentException" );
        } catch ( IllegalAccessException e ) {
            Debug( "IllegalAccessException" );
        } catch ( InvocationTargetException e ) {
            Debug( "InvocationTargetException" );
        }
    }

    public enum OS {Mac, Windows, Linux}

    public enum SOURCE_LANGUAGE {Java, Python}

    static public OS getOS() {
        String uAgent = System.getProperty( "os.name" );
        Debug( "raw os name is " + uAgent );
        if ( uAgent.toLowerCase().contains( "windows" ) )
            return OS.Windows;
        else if ( uAgent.toLowerCase().contains( "linux" ) )
            return OS.Linux;
        else
            return OS.Mac;
    }

    static public String StringArrayToString( String[] array, String separator ) {
        String result = "";

        for ( String string : array )
            result += string + separator;

        return result;
    }

    static public String FindSubstringInStringList( List<String> stringList, String findMe ) {
        findMe = findMe.toLowerCase();
        for ( String searchMe : stringList )
            if ( searchMe.toLowerCase().contains( findMe ) )
                return searchMe;
        return "";
    }

    static public String[][] ConvertStringArraytoStringArrayArray( String[] array ) {
        String[][] arrayArray = new String[ array.length ][ 1 ];
        int i = 0;
        for ( String string : array )
            arrayArray[ i++ ][ 0 ] = string;
        return arrayArray;
    }

    static public String GetDateAndTimeFilePrefix() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyyMMdd_HHmmss_" );

        return dateFormat.format( date );
    }

}
