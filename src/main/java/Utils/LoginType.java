
package Utils;

/*
 * Created by timothy.hallbeck on 4/11/2017.
 * Copyright (c) Hallbeck Pepper Company LLC
 * All rights reserved
 */

import org.openqa.selenium.Keys;
import org.testng.TestNGException;

import java.util.TreeMap;

public enum LoginType {

    DEFAULT(
        "",
        Environment.Prod,
        "",
        "",
        "",
        "",
        "",
        "https://www.google.com"
    ),

    SAMPLE_MOVIEDB(
        "",
        Environment.Prod,
        "",
        "",
        "",
        "",
        "",
        "http://www.themoviedb.org"
    ),

    SAMPLE_CARBONBLACK(
        "",
        Environment.Prod,
        "",
        "",
        "",
        "",
        "",
        "https://www.carbonblack.com"
    ),

    JIRA_HOMEPAGE(
        "",
        Environment.Prod,
        "timothy.hallbeck",
        "HellTheWhat12!",
        "//*[@id='login-form-username']",
        "//*[@id='login-form-password']",
        "//*[@id='login']",
        "https://jira-it.wgu.edu/secure/Dashboard.jspa"
        ),


    ;

    private final String displayName;
    private final Environment environment;
    private final String username;
    private final String password;
    private final String usernameXpath;
    private final String passwordXpath;
    private final String loginButtonXpath;
    private final String startingUrl;

    static TreeMap<String, String> macroMap = new TreeMap<>();
    static TreeMap<String, LoginType> displayNameMap = new TreeMap<>();

    LoginType( String displayName, Environment environment, String username, String password, String usernameXpath, String passwordXpath, String loginButtonXpath, String startingUrl ) {
        this.displayName = displayName;
        this.environment = environment;
        this.username = username;
        this.password = password;
        this.usernameXpath = usernameXpath;
        this.passwordXpath = passwordXpath;
        this.loginButtonXpath = loginButtonXpath;
        this.startingUrl = startingUrl;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUsernameXpath() {
        return this.usernameXpath;
    }

    public String getPasswordXpath() {
        return this.passwordXpath;
    }

    public String getLoginButtonXpath() {
        return this.loginButtonXpath;
    }

    public String getStartingUrl() {
        return this.startingUrl;
    }

    static public LoginType GetSystemDefault() {
        return LoginType.SAMPLE_MOVIEDB;
    }

    public String getUrlPrefix() {
        if ( startingUrl.toLowerCase().contains( "l1my" ) )
            return "l1";
        if ( startingUrl.toLowerCase().contains( "l2my" ) )
            return "l2";

        return "";
    }

    public Environment getEnv() {
        return environment;
    }

    static public String[] GetDisplayNames() {
        if ( displayNameMap.size() == 0 ) {
            for ( LoginType login : LoginType.values() )
                if ( !login.displayName.equals( "" ) )
                    displayNameMap.put( login.displayName, login );
        }

        return displayNameMap.keySet().toArray( new String[ displayNameMap.size() ] );
    }

    static public LoginType Find( String string ) {
        if ( displayNameMap.size() == 0 )
            GetDisplayNames();

        LoginType login = displayNameMap.get( string );
        if ( login == null )
            throw new TestNGException( "Unknown display name in LoginType::Find() " + string );

        return login;
    }

    public static void Login( Page page, LoginType type ) {
        General.Debug( "logging in as (" + type.getUsername() + ", " + type.getPassword() + ")" );
        page.getByXpath( type.getUsernameXpath() ).clear();
        page.getByXpath( type.getUsernameXpath() ).click();
        General.Sleep( 1000 );
        page.getByXpath( type.getUsernameXpath() ).sendKeys( type.getUsername() );
        General.Sleep( 1000 );
        page.getByXpath( type.getUsernameXpath() ).sendKeys( Keys.TAB );
        General.Sleep( 1000 );
        page.getByXpath( type.getPasswordXpath() ).clear();
        General.Sleep( 1000 );
        page.getByXpath( type.getPasswordXpath() ).sendKeys( type.getPassword() );
        General.Sleep( 1000 );
        PowerPointUtil.saveScreenshot( page, "LoginType::Login()" );
        page.getByXpath( type.getLoginButtonXpath() ).click();
    }

}
