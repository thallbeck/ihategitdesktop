package Utils;

import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogEntries;
import org.testng.Assert;

import java.awt.Dimension;
import java.awt.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

/*
 * Created by timothy.hallbeck on 4/11/2017.
 * Copyright (c) Hallbeck Pepper Company LLC
 * All rights reserved
 */

public class WebPage extends Page {

    public enum Browser {
        Firefox,
        Chrome,
//        IE
    }

    public static boolean BY_XPATH = true;
    public static boolean BY_LINKTEXT = false;
    private boolean bDataValidation = true;
    private boolean bShowPageLoadTimes = false;

    public long PAGE_GET_SLEEP_IN_MILLISECS = 2000;

    public String PAGE_VERIFIER = "";
    public Browser browser;
    protected HashMap<Object, HashMap<String, Object>> AllPageClasses = new HashMap<>();
    HashMap listOfMethods = new HashMap <String, Object> ();


    public WebPage( WebPage existingPage ) {
        this( existingPage, null, null );
    }

    public WebPage( WebPage existingPage, LoginType loginType, Browser browser ) {
        super( existingPage, loginType );
        General.Debug( "WebPage()" );

        if ( existingPage != null ) {

            driver = existingPage.driver;
            if ( browser != null )

                this.browser = existingPage.browser;
        }
        else {

            this.loginType = loginType;
            this.browser = browser;

        }

        if ( existingPage == null || existingPage.driver == null ) {
            // Windows will auto add c: to the front of this, so have chromedriver.exe in the path. Also make sure Mac version has .exe on end
            File driverfile;

            if ( browser == Browser.Chrome ) {
                if ( General.getOS() == General.OS.Mac ) {
                    General.Debug( "os is mac" );
                    driverfile = new File( General.DATA_PATH + "chromedrivermac.exe" );
                }
                else if ( General.getOS() == General.OS.Linux ) {
                    General.Debug( "os is linux" );
                    driverfile = new File( General.DATA_PATH + "chromedriverlinux.exe" );
                }
                else {
                    General.Debug( "os is windows" );
                    driverfile = new File( General.DATA_PATH + "chromedriver.exe" );
                }
                System.setProperty( ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, driverfile.getAbsolutePath() );

                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments( "test-type", "start-maximized", "enable-network-information", "disable-infobars" );
                driver = new ChromeDriver( chromeOptions );
            }
            else if ( browser == Browser.Firefox ) {
                System.setProperty( "webdriver.gecko.driver", General.DATA_PATH + "geckodriver.exe" );
                driver = new FirefoxDriver();
            }
            else { // IE
                System.setProperty( "webdriver.ie.driver.extractpath", General.DATA_PATH );
                System.setProperty( "webdriver.ie.driver", General.DATA_PATH + "IEDriverServer.exe" );
                System.setProperty( "webdriver.ie.logfile", General.DATA_PATH + "IEDriverServer.log" );
                driver = new InternetExplorerDriver();
            }
            General.Sleep( 1000 );
            //driver.manage().window().setPosition( new Point( 0, 0 ) );
            restoreDefaultSizeAndPosition();
        }
    } // public WebPage

    public Page ExercisePage( boolean recursive ) {

        load();
        return this;

    }

    public void waitForControl( String xpath ) {
        General.Debug( "WebPage::waitForControl(" + xpath + ")" );
        General.Sleep( 250 );
        scrollIntoView( xpath );
    }

    // return value is whether login action was performed or not
    protected boolean login() {
        General.Debug( "WebPage::login()" );
        if ( bLoggedIn )
            return false;

        get( loginType.getStartingUrl() );
        Page.Sleep();

        if ( !loginType.getUsername().isEmpty() ) {
            LoginType.Login( this, loginType );
            Page.Sleep();
        }
        bLoggedIn = true;

        return true;
    }

    public WebPage loadDataItems() {
        return this;
    }

    public WebPage load( WebPage existingPage ) {
        bLoggedIn = existingPage.bLoggedIn;
        driver = existingPage.driver;
        this.loginType = existingPage.loginType;

        load();

        return this;
    }

    public WebPage load() {
        if ( !login() ) {
            General.Debug( getClassName() + "::load()" );
            get( loginType.getStartingUrl(), PAGE_GET_SLEEP_IN_MILLISECS );
        }

        loadDataItems();
        PowerPointUtil.saveScreenshot( this, getClassName() + "::load()" );

        return this;
    }

    public WebPage fullScreen() {
        General.Debug( "WebPage::fullScreen()" );
        driver.manage().window().maximize();
        return this;
    }

    public WebPage get( String url ) {
        General.Debug( getClassName() + "::get(" + url + ")" );
        get( url, PAGE_GET_SLEEP_IN_MILLISECS );

        return this;
    }

    public Set<Cookie> getCookies() {
        General.Debug( "getCookies()" );
        return driver.manage().getCookies();
    }

    public String getCookie( String cookiename ) {
        General.Debug( "getCookie(" + cookiename + ")" );
        Set<Cookie> cookies = getCookies();
        for ( Cookie cookie : cookies )
            if ( cookie.getName().equals( cookiename ) )
                return cookie.getValue();

        return null;
    }

    public void getByXpathAndActivate( String xpath ) {
        getByXpathAndActivate( xpath, "no desc" );
    }

    public void getByXpathAndActivate( String xpath, String desc ) {
        getByXpathAndActivate( xpath, desc, 4000 );
    }

    public void getByXpathAndActivate( String xpath, String desc, int sleepMillis ) {
        try {
            getByXpath( xpath ).click();
        } catch ( WebDriverException e ) {
            if ( e.getMessage().contains( "Element is not clickable at point" ) ) {
                scrollIntoView( xpath, false );
                getByXpath( xpath ).click();
            }
            else {
                throw new WebDriverException( e );
            }
        }

        Page.Sleep();
        ArrayList<String> tabs = new ArrayList<>( driver.getWindowHandles() );
        int tabCount = tabs.size();

        while ( tabCount-- > 1 ) {
            driver.switchTo().window( tabs.get( 0 ) );
            driver.close();
        }
        tabs.clear();
        tabs = new ArrayList<>( driver.getWindowHandles() );
        driver.switchTo().window( tabs.get( 0 ) );
        PowerPointUtil.saveScreenshot( this, desc );

        restoreDefaultSizeAndPosition();
    }

    // If there are multiple tabs, closes the rightmost one
    public WebPage closeTab() {
        General.Debug( getClassName() + "::closeTab()" );
        ArrayList<String> tabs = new ArrayList<>( driver.getWindowHandles() );

        driver.switchTo().window( tabs.get( tabs.size() - 1 ) );
        driver.close();
        if ( tabs.size() > 1 )
            driver.switchTo().window( tabs.get( 0 ) );

        return this;
    }

    public WebPage clickBrowserBackButton() {
        General.Debug( getClassName() + "::clickBrowserBackButton()" );
        driver.navigate().back();

        return this;
    }

    public WebPage clickBrowserRefreshButton() {
        General.Debug( getClassName() + "::clickBrowserRefreshButton()" );
        driver.navigate().refresh();

        return this;
    }

    public WebPage get( String url, long sleepPeriodMilliseconds ) {
        if ( !General.isSilentMode() )
            startTimer();

        driver.get( url );

        if ( sleepPeriodMilliseconds > 0 )
            Page.Sleep();

        if ( !General.isSilentMode() )
            stopAndLogTime( "WebPage::get(" + url + ")" );

        return this;
    }

    public boolean doDataValidation( boolean flag ) {
        bDataValidation = flag;
        return bDataValidation;
    }

    public boolean doDataValidation() {
        General.Debug( getClassName() + "::doDataValidation()" );
        if ( General.getOS() == General.OS.Mac )
            return false;
        return bDataValidation;
    }

    public WebPage LoadAndSwitchTabs( String linkThatOpensNewTab ) {
        LoadAndSwitchTabs( linkThatOpensNewTab, BY_XPATH );

        return this;
    }

    // Clicking on some items opens a new tab. What we do here is click, grab the url from the new tab,
    //   close the new tab, then open the url in the old tab. This way we never have to deal with tab management,
    //   as all the action is redirected to the leftmost tab. Most of the time, except in the method below, we have
    //   a single open browser tab.
    public WebPage LoadAndSwitchTabs( String linkThatOpensNewTab, boolean byXpath ) {
        General.Debug( getClassName() + "::LoadAndSwitchTabs()" );
        if ( byXpath ) {
            getByXpath( linkThatOpensNewTab );
            getByXpath( linkThatOpensNewTab ).click();
        }
        else {
            getByLinkText( linkThatOpensNewTab );
            getByLinkText( linkThatOpensNewTab ).click();
        }

        // sleep just long enough for the tab and url to render, we don't care about the rest of the window
        General.Sleep( 4000 );

        // get all the tab handles
        ArrayList<String> tabs = new ArrayList<>( driver.getWindowHandles() );

        // switch to the new one
        driver.switchTo().window( tabs.get( 1 ) );

        // save the url
        String newTab = driver.getCurrentUrl();

        // close this new tab
        driver.close();

        // switch to the original (now sole) tab
        driver.switchTo().window( tabs.get( 0 ) );

        // get our new url in the old tab
        get( newTab );

        // In case the window moved because this was a new tab in a new window
        restoreDefaultSizeAndPosition();

        return this;
    }

    public Set<String> ListAllWindows() {
        General.Debug( getClassName() + "::ListAllWindows()" );
        Set<String> set = driver.getWindowHandles();
        General.Debug( "Found " + set.size() + " window(s)" );

        for ( String string : set )
            General.Debug( string );

//        List <WebElement> framesList = driver.findElements(By.xpath("//iframe"));
//        for (WebElement element : framesList)
//            General.Debug(element);

        return set;
    }

    public String GetTopWindow() {
        General.Debug( getClassName() + "::GetTopWindow()" );
        Set<String> set = ListAllWindows();

        return set.toArray()[ set.size() - 1 ].toString();
    }

    String[] ignoreFrameContents = {
        "adobedtm",
        "lpcdn",
        "doubleclick",
        "marketo",
    };

    public int CountFrames() {
        General.Debug( getClassName() + "::CountFrames()" );
        List<WebElement> framesList = driver.findElements( By.xpath( "//iframe" ) );

        // Ignore marketing bs frames
        String src = "";
        WebElement element = null;
        for ( int i = framesList.size() - 1; i > 0; i-- ) {
            element = framesList.get( i );
            src = element.getAttribute( "src" ).toLowerCase();
            for ( String ignoreMe : ignoreFrameContents )
                if ( src.contains( ignoreMe ) ) {
                    framesList.remove( i );
                    break;
                }
        }

        if ( framesList.size() == 1 )
            General.Debug( "Found " + framesList.size() + " frame" );
        else
            General.Debug( "Found " + framesList.size() + " frames" );

        return framesList.size();
    }

    public WebPage RestoreDefaultFrameAndWindow() {
        General.Debug( getClassName() + "::RestoreDefaultFrameAndWindow()" );
        driver.switchTo().defaultContent();

        return this;
    }

    public WebPage switchToFrame( String name ) {
        General.Debug( getClassName() + "::switchToFrame(" + name + ")" );
        driver.switchTo().frame( name );

        return this;
    }

    public WebPage switchToFrame( int index ) {
        General.Debug( getClassName() + "::switchToFrame(" + index + ")" );
        try {
            General.Debug( getClassName() + "::Switching to frame[" + index + "]" );
            driver.switchTo().frame( index );
        } catch ( Exception e ) {
            ;
        }

        return this;
    }

    public WebPage switchToDefaultFrame() {
        General.Debug( getClassName() + "::switchToDefaultFrame()" );
        driver.switchTo().defaultContent();

        return this;
    }

    public WebPage enterTextHitReturn( String text ) {
        driver.switchTo().activeElement().sendKeys( text + Keys.RETURN );

        return this;
    }

    public WebPage restoreDefaultSizeAndPosition() {
        // Bug in latest Firefox breaks all window sizing and positioning
//        if (this.browser == Browser.Firefox)
        //           return this;

        General.Debug( getClassName() + "::restoreDefaultSizeAndPosition()" );

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        // for those with double wide screens
        if ( width > 1600 )
            width = 1600;

        // double wide screens turned vertically
        if ( height > 900 )
            height = 900;

        if ( General.getOS() == General.OS.Mac ) {
            if ( driver instanceof ChromeDriver ) {
                driver.manage().window().setPosition( new Point( ( int ) (width * .05), ( int ) (height * .05) ) );
                WindowUtils.setSize( driver, ( int ) (width * .9), ( int ) (height * .9) );
            }
            else { // Firefox
                driver.manage().window().setPosition( new Point( ( int ) (width * .05), ( int ) (height * .05) ) );
                WindowUtils.setSize( driver, ( int ) (width * .9), ( int ) (height * .9) );
            }
        }
        else { // Windows
            if ( driver instanceof ChromeDriver ) {
                driver.manage().window().setPosition( new Point( ( int ) (width * .05), ( int ) (height * .05) ) );
                WindowUtils.setSize( driver, ( int ) (width * .8), ( int ) (height * .8) );
            }
            else if ( driver instanceof FirefoxDriver ) {
                driver.manage().window().setPosition( new Point( ( int ) (width * .05), ( int ) (height * .05) ) );
                WindowUtils.setSize( driver, ( int ) (width * .7), ( int ) (height * .7) );
            }
            else { // IE
                driver.manage().window().setPosition( new Point( ( int ) (width * .05), ( int ) (height * .05) ) );
                WindowUtils.setSize( driver, ( int ) (width * .9), ( int ) (height * .85) );
            }
        }
        return this;
    }

    // Workaround for quit bug in linux. Broken out below because I expect similar failure in Chrome and Safari
    public void quit() {
        if ( this.browser == Browser.Firefox ) {
            if ( General.getOS() == General.OS.Linux )
                driver.close();
            else
                driver.quit();
        }
        else {
            driver.quit();
        }
    }

    public WebPage scrollUp( int iCount ) {
        General.Debug( getClassName() + "::scrollUp()" );
        Assert.assertTrue( iCount > 0, "scrollUp(" + iCount + ") not valid, must be > 0" );
        int size = ( int ) (driver.manage().window().getSize().getHeight() * .66);

        while ( iCount-- > 0 ) {
            (( JavascriptExecutor ) driver).executeScript( "scrollBy(0, -" + size + ")" );
            General.Sleep( 500 );
        }

        return this;
    }

    public WebElement scrollIntoView( String xpath ) {
        return scrollIntoView( xpath, false );
    }

    public WebElement scrollIntoView( String xpath, boolean bBackup ) {
        return scrollIntoView( getByXpath( xpath ), bBackup );
    }

    public WebElement scrollIntoView( WebElement element, boolean bBackup ) {
        General.Debug( getClassName() + "::scrollIntoView(" + bBackup + ")" );
        if ( element == null )
            return null;

        if ( driver instanceof ChromeDriver || driver instanceof FirefoxDriver ) {
            // This goes too far because of the unknown to webdriver bar we use across the top. If bar present and you're not
            //   on the bottom of the page, pass in true
            (( JavascriptExecutor ) driver).executeScript( "arguments[0].scrollIntoView(true);", element );
        }
// firefox aug 2017 gecko decided not to support moveToElement. Morons.
        /*        else if ( driver instanceof FirefoxDriver ) {
            Actions actions = new Actions( driver );
            actions.moveToElement( element );
            actions.perform();
        }
*/
        else {
            General.Debug( "Unknown browser type in scrollIntoView() call...ignoring" );
        }

        if ( bBackup )
            scrollUp( 1 );

        return element;
    }

    public WebPage scrollDown( int iCount ) {
        General.Debug( getClassName() + "::scrollDown(" + iCount + ")" );
        Assert.assertTrue( iCount > 0, "scrollDown(" + iCount + ") not valid, must be > 0" );
        int size = ( int ) (driver.manage().window().getSize().getHeight() * .66);

        while ( iCount-- > 0 ) {
            (( JavascriptExecutor ) driver).executeScript( "scrollBy(0, " + size + ")" );
            General.Sleep( 500 );
        }
        return this;
    }

    public WebPage scrollLeft( int iCount ) {
        General.Debug( getClassName() + "::scrollLeft(" + iCount + ")" );
        Assert.assertTrue( iCount > 0, "scrollLeft(" + iCount + ") not valid, must be > 0" );
        int size = ( int ) (driver.manage().window().getSize().getWidth() * .25);

        while ( iCount-- > 0 ) {
            (( JavascriptExecutor ) driver).executeScript( "scrollBy(-" + size + ", 0)" );
            General.Sleep( 500 );
        }
        return this;
    }

    public WebPage scrollRight( int iCount ) {
        General.Debug( getClassName() + "::scrollRight(" + iCount + ")" );
        Assert.assertTrue( iCount > 0, "scrollLeft(" + iCount + ") not valid, must be > 0" );
        int size = ( int ) (driver.manage().window().getSize().getWidth() * .25);

        while ( iCount-- > 0 ) {
            (( JavascriptExecutor ) driver).executeScript( "scrollBy(" + size + ", 0)" );
            General.Sleep( 500 );
        }
        return this;
    }

    public void ExecuteRandomMethods( int count ) {

        // check for bogus call or nothing to test
        if ( count <= 0 || AllPageClasses.size() == 0 )
            return;

        int classCount = AllPageClasses.size();
        General.Debug( "class count: " + classCount + "  random methods to invoke: " + count );

        // duplicate classname hashmap, and eliminate those with no associated methods
        HashMap<WebPage, HashMap<String, Object>> copyOfPageClasses = new HashMap<>();
        HashMap<String, Object> methodMap;

        // dupe the class hashmap that have methods
        for ( int i = 0; i < classCount; i++ ) {
            WebPage key = ( WebPage ) AllPageClasses.keySet().toArray()[ i ];

            methodMap = AllPageClasses.get( key );
            if ( methodMap.keySet().size() != 0 )
                General.Debug( "available methods: " + Arrays.toString( methodMap.keySet().toArray() ) );
            if ( methodMap.size() != 0 )
                copyOfPageClasses.put( key, methodMap );
        }

        classCount = copyOfPageClasses.size();
        // check that we're not empty
        if ( classCount == 0 )
            return;

        int randomClassIndex, randomMethodIndex;
        String randomMethodName;
        WebPage randomWebPage;

        for ( int i = 0; i < count; i++ ) {

            randomClassIndex = General.getRandomInt( classCount );
            randomWebPage = ( WebPage ) copyOfPageClasses.keySet().toArray()[ randomClassIndex ];
            methodMap = copyOfPageClasses.get( randomWebPage );

            randomMethodIndex = General.getRandomInt( methodMap.size() );
            randomMethodName = methodMap.keySet().toArray()[ randomMethodIndex ].toString();

            General.Debug(
                "\n---- " + String.valueOf( i + 1 ) + " of " + count + " -----\n"
                    + "Randomly invoking " + randomWebPage.getClassName() + "::" + randomMethodName
                    + "\n------------------\n" );
            randomWebPage.load( this );
            General.executePageMethod( randomWebPage, randomMethodName );

        }
    }

    public void ExerciseUrlList( String[] urlList ) {
        for ( String url : urlList ) {
            if ( !url.isEmpty() && !url.contains( "zzz" ) ) {
                get( url, 2000 );
                PowerPointUtil.saveScreenshot( this, url + "-top" );
                JavascriptExecutor js = ( JavascriptExecutor ) driver;
                js.executeScript( "window.scrollTo(0, document.body.scrollHeight);" );
                PowerPointUtil.saveScreenshot( this, url + "-bottom" );
            }
        }
    }

    public void DocumentAllHrefs( HashMap map, int depth, String startingUrl, String[] inclusionFilter ) {
        driver.manage().timeouts().implicitlyWait( 2, TimeUnit.SECONDS );
        driver.manage().timeouts().pageLoadTimeout( 6, TimeUnit.SECONDS );
        driver.manage().timeouts().setScriptTimeout( 2, TimeUnit.SECONDS );

        map.put( startingUrl, new HashMap() );
        for ( int counter = 1; counter <= depth; counter++ ) {
            General.Debug( "  ------ Starting page level " + counter + " ------" );
            Object[] array = map.keySet().toArray();
            for ( Object object : array ) {
                try {
                    String key = object.toString();
                    if ( (( HashMap ) map.get( key )).size() == 0 ) {
                        get( key, 2000 );
                        map.put( key, getAllHrefsOnAPage( startingUrl, inclusionFilter ) );
                        PowerPointUtil.saveScreenshot( this, key );

                        checkForDuplicateUris();

                        // The top level map has all the other maps combined. This is to avoid duplicates regardless of level
                        StringUtils.addMapToMap( map, ( HashMap ) map.get( key ) );

                        // Therefore the top level map always has an accurate total of all other levels
                        General.Debug( " Total urls: " + map.size() );
                    }
                } catch ( Exception e ) {
                    PowerPointUtil.saveScreenshot( this, startingUrl );
                    PowerPointUtil.addBulletPoint( e.getMessage() );
                    General.Debug( "getAllHrefsOnAPage(): Failed to load page (" + startingUrl + ")", true );
                }
            }
        }
    }

    private HashMap getAllHrefsOnAPage( String startingUrl, String[] inclusionFilterArray ) {
        General.Debug( getClassName() + "::getAllHrefsOnAPage" );
        List<WebElement> listHref;
        List<String> hrefs = new ArrayList<>();
        HashMap mapUrls = new HashMap();
        String href;

        int frameCount = CountFrames(), i = 0;
        do {
            listHref = driver.findElements( By.tagName( "a" ) );
            for ( WebElement element : listHref ) {
// Skips all the menu entries
//                if (!element.isDisplayed())
//                    continue;
                href = element.getAttribute( "href" );

                // someone went ng- prefix happy, so check for both href and ng-href
                if ( href == null || href.isEmpty() ) {
                    href = element.getAttribute( "ng-href" );
                    if ( href == null || href.isEmpty() )
                        continue;
                }
                hrefs.add( href );
            }

        } while ( i < frameCount && switchToDefaultFrame() != null && switchToFrame( i++ ) != null );
        switchToDefaultFrame();

        for ( String url : hrefs ) {
//            url = url.toLowerCase();

            // Limited to our domain, no facebook redirects
            boolean bAdded = false;
            for ( String filter : inclusionFilterArray ) {
                if ( url.contains( filter ) && !url.contains( "facebook" ) && !url.contains( "logout" ) ) {
                    // Get rid of any in-page refs
                    if ( url.lastIndexOf( '#' ) > 0 )
                        url = url.substring( 0, url.lastIndexOf( '#' ) );

                    // Get rid of any trailing slashes
                    if ( url.charAt( url.length() - 1 ) == '/' )
                        url = url.substring( 0, url.length() - 1 );

                    // Unique urls only
                    if ( !mapUrls.containsKey( url ) ) {
                        bAdded = true;
                        General.Debug( " Added url : " + url );
                        mapUrls.put( url, new TreeMap() );
                        break;
                    }
                }
            }
            if ( !bAdded )
                General.Debug( " REJECTED url : " + url );
        }

        // If there are no links on the page, add empty string
        if ( mapUrls.size() == 0 )
            mapUrls.put( startingUrl, new HashMap() );

        return mapUrls;
    }

    private void checkForDuplicateUris() {
        String scriptToExecute = "var performance = window.performance || window.webkitPerformance || {}; var network = performance.getEntries() || {}; return network;";
        String netData = (( JavascriptExecutor ) driver).executeScript( scriptToExecute ).toString();

//        General.Debug("urls and counts: ");
        int nameIndex = 0, endIndex, maxUrls = 200;
        String name;
        HashMap<String, Integer> sortedCounts;
        sortedCounts = new HashMap<>();

        while ( maxUrls-- > 0 ) {
            if ( (nameIndex = netData.indexOf( "name=" )) == -1 )
                break;
            endIndex = netData.indexOf( ", startTime" );
            name = netData.substring( nameIndex + 5, endIndex );
            netData = netData.substring( endIndex + 11 );

            if ( sortedCounts.get( name ) == null )
                sortedCounts.put( name, 1 );
            else
                sortedCounts.put( name, sortedCounts.get( name ) + 1 );
        }

        for ( Map.Entry<String, Integer> entry : sortedCounts.entrySet() )
            if ( entry.getValue() != 1 )
                PowerPointUtil.addBulletPoint( " (" + entry.getKey() + ", " + entry.getValue() + ")" );
    }

    public WebPage CreatePageClasses( HashMap map, String startingUrl, General.SOURCE_LANGUAGE language ) {

        HashMap childHashMap = ( HashMap ) map.get( startingUrl );
        assert (childHashMap != null);
        String className = convertUrlToHomePageClassName( startingUrl );

        CreatePageClass( className, childHashMap, null, language );

        return this;
    }

    /*        writer.println( "    public " + className + " click_thisthing() {" );
            writer.println( "        return this;" );
            writer.println( "    }" );
            writer.println( "" );
    */
    public String CreatePageClass( String className, HashMap childMap, String parentClassName, General.SOURCE_LANGUAGE language ) {

        PrintWriter writer = General.CreateOutputFile( className, language );

        WriteImports( writer, language );
        WriteClassHeader( writer, className, parentClassName, language );
        WriteSimpleGetMethods( writer, className, childMap, language );
        WriteRandomActions( writer, className, childMap, language );
        WriteExercisePage( writer, className, childMap, language );
        WriteClassFooter( writer, language );

        writer.close();

        return className;
    }

    public void WriteImports( PrintWriter writer, General.SOURCE_LANGUAGE language ) {
        switch ( language ) {
            case Python:
                writer.println( "from selenium import webdriver" );
                writer.println( "from selenium.webdriver.firefox.firefox_binary import FirefoxBinary" );
                writer.println( "" );
                writer.println( "import general" );
                writer.println( "import logintype" );
                writer.println( "import browser" );
                writer.println( "import page" );
                writer.println( "" );
                break;

            case Java:
            default:
                writer.println( "import Utils.General;" );
                writer.println( "import Utils.LoginType;" );
                writer.println( "import Utils.WebPage;" );
                writer.println( "" );
                break;
        }
    }

    public void WriteClassHeader( PrintWriter writer, String className, String parentClassName, General.SOURCE_LANGUAGE language ) {
        switch ( language ) {
            case Python:
                writer.print( "class " + className );
                if ( parentClassName != null )
                    writer.print( " ( " + parentClassName + " )" );
                else
                    writer.print( " ( page )" );

                writer.println( ":" );
                writer.println( "" );
                writer.println( "    def __init__(self, existing_page = null, login_type = logintype.DEFAULT, page = browser.Firefox):" );
                writer.println( "        super(self).__init()" );
                writer.println( "" );
                writer.println( "    def load(self):" );
                writer.println( "        super(self).load()" );
                break;

            case Java:
            default:
                writer.print( "public class " + className );
                if ( parentClassName != null )
                    writer.println( " extends " + parentClassName + " {" );
                else
                    writer.println( " extends WebPage {" );

                writer.println( "" );
                writer.println( "    public " + className + "( WebPage existingPage ) {" );
                writer.println( "        super( existingPage );" );
                writer.println( "    }" );

                writer.println( "" );
                writer.println( "    public " + className + "() {" );
                writer.println( "        super( null, LoginType.DEFAULT, WebPage.Browser.Firefox );" );
                writer.println( "    }" );

                writer.println( "" );
                writer.println( "    public " + className + " load() {" );
                writer.println( "        super.load();" );
                writer.println( "        return this;" );
                writer.println( "    }" );

                //                new WebPage( null, LoginType.SAMPLE_CARBONBLACK, WebPage.Browser.Firefox );
                break;
        }
    }

    public void WriteSimpleGetMethods( PrintWriter writer, String className, HashMap childMap, General.SOURCE_LANGUAGE language ) {

        Map.Entry keyValuePair;
        String methodName = "";
        Iterator worker = childMap.entrySet().iterator();

        while ( worker.hasNext() ) {
            keyValuePair = (Map.Entry) worker.next();
            methodName = convertUrlToPageClassName( (String) keyValuePair.getKey() );
            listOfMethods.put( "get_" + methodName, null );

            switch ( language ) {
                case Python:
                    writer.println( "" );
                    writer.println( "    def get_" + methodName + "(self):" );
                    writer.println( "        self.get( \"" + ((String) keyValuePair.getKey()) + "\" )" );
                    break;

                case Java:
                default:
                    writer.println( "" );
                    writer.println( "    public " + className + " get_" + methodName + "() {" );
                    writer.println( "        get( \"" + ((String) keyValuePair.getKey()) + "\" );" );
                    writer.println( "        return this;" );
                    writer.println( "    }" );
                    break;
            }

        }
    }

    public void WriteRandomActions( PrintWriter writer, String className, HashMap childMap, General.SOURCE_LANGUAGE language ) {

        switch ( language ) {
            case Python:
                writer.println( "    def AddRandomActions(self):" );
                writer.println( "        placeholder = 1" );
                writer.println( "" );
                break;

            case Java:
            default:
                writer.println( "    public " + className + " AddRandomActions() {" );
                writer.println( "        int placeholder = 1;" );
                writer.println( "        return this;" );
                writer.println( "    }" );
                writer.println( "" );
                break;
        }
    }

    public void WriteExercisePage( PrintWriter writer, String className, HashMap childMap, General.SOURCE_LANGUAGE language ) {

        Iterator worker = listOfMethods.entrySet().iterator();
        Map.Entry keyValuePair = (Map.Entry) worker.next();

        switch ( language ) {
            case Python:
                writer.println( "    def ExercisePage(self, cascade):" );
                writer.println( "        self.load()" );
                writer.println( "" );

                while ( worker.hasNext() ) {
                    keyValuePair = ( Map.Entry ) worker.next();
                    writer.println( "        self."  + keyValuePair.getKey() + "()");
                }
                break;

            case Java:
            default:
                writer.println( "    public " + className + " ExercisePage( boolean cascade ) {" );
                writer.println( "" );
                writer.println( "        load();" );
                writer.println( "" );
                writer.println( "        if (cascade) {" );
                writer.println( "            // when page subclasses exist they will unstantiated and used here" );
                writer.println( "            return this;" );
                writer.println( "        }" );
                writer.println( "" );

                while ( worker.hasNext() ) {
                    keyValuePair = ( Map.Entry ) worker.next();
                    writer.println( "        "  + keyValuePair.getKey() + "();");
                }
                writer.println( "" );
                writer.println( "        return this;" );
                writer.println( "    }" );
                writer.println( "" );
                break;
        }
    }

    public void WriteClassFooter( PrintWriter writer, General.SOURCE_LANGUAGE language ) {
        switch ( language ) {
            case Python:
                writer.println( "" );
                break;

            case Java:
            default:
                writer.println( "}" );
                writer.println( "" );
                break;
        }
    }

    public String convertUrlToPageClassName( String url ) {

        // remove transport type
        url = url.replace( "https://", "" );
        url = url.replace( "http://", "" );

        // remove leading slash
        if ( url.indexOf( '/' ) == 0 )
            url = url.substring( 1 );

        // change remaining slashes, question marks, percent signs, equal signs, etc to underscores
        url = url.replace( "/", "_" );
        url = url.replace( "?", "_" );
        url = url.replace( "%", "_" );
        url = url.replace( "&", "_" );
        url = url.replace( "=", "_" );
        url = url.replace( "-", "_" );
        url = url.replace( ".", "_" );

        return url;
    }

    public String convertUrlToHomePageClassName( String url ) {

        // Handle both http and https cases
        String homePage = url.replace( "https://", "" );
        homePage = homePage.replace( "http://", "" );

        // Get rid of any prefix
        homePage = homePage.replace( "www.", "" );

        // Leave the suffix intact
        homePage = homePage.replace( ".", "_" );
        return homePage;
    }

    public WebPage showPageLoadTimes( boolean flag ) {
        this.bShowPageLoadTimes = flag;
        return this;
    }

    @SuppressWarnings( "unchecked" )
    public WebPage showPageLoadTimes( String pageName ) {
        if ( !bShowPageLoadTimes )
            return this;

        if ( !(this.driver instanceof ChromeDriver) ) {
            General.Debug( "WARNING - showPageLoadTimes() only valid when using Chrome driver " );
            return this;
        }

        JavascriptExecutor js = ( JavascriptExecutor ) this.driver;
        Object ret = js.executeScript( "return window.performance.getEntries();" );

        // Get the log errors for the current page, then clear the log
        Set<String> logtypes = driver.manage().logs().getAvailableLogTypes();
        LogEntries entries = driver.manage().logs().get( "browser" );
        LocalStorage local = (( WebStorage ) driver).getLocalStorage();
        local.clear();

        ArrayList<Object> castArray = ( ArrayList<Object> ) ret;
        Map<Object, Object> singleMapping;
        String name;
        String duration;

        // So we can show a sorted version later without any extra work
        TreeMap<Integer, String> sortedMap;
        sortedMap = new TreeMap<>();

        // This converts the 8 digit precision float to an int, and prints it in call order
        //   Also uses the greatest value of responseEnd as the time to finish loading page
        Double responseEnd = 0.0;
        General.Debug( "Load times for " + pageName + ", in call order from first to last:" );
        for ( Object entriesArray : castArray ) {
            singleMapping = ( Map<Object, Object> ) entriesArray;
            name = singleMapping.get( "name" ).toString();
            duration = singleMapping.get( "duration" ).toString();

            if ( !duration.contentEquals( "0" ) ) {
                sortedMap.put( ( int ) Float.parseFloat( duration ), name );
                General.Debug( ( int ) Float.parseFloat( duration ) + "," + name + "" );
            }

            responseEnd = Double.max( responseEnd, ( double ) (Double.valueOf( singleMapping.get( "responseEnd" ).toString() )) );
        }

        // This prints the same set of numbers in descending amount of time order
        General.Debug( "\nLoad times for " + pageName + ", in descending amount of time to load:" );
        for ( int i : sortedMap.descendingKeySet() )
            General.Debug( i + ", " + sortedMap.get( i ) + "" );

        General.Debug( "\nTotal page load time: " + responseEnd + " ms\n" );

        return this;
    }

}

