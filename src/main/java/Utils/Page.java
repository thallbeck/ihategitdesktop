package Utils;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;

import static Utils.LoginType.GetSystemDefault;

/*
 * Created by timothy.hallbeck on 4/11/2017.
 * Copyright (c) Hallbeck Pepper Company LLC
 * All rights reserved
 */

abstract public class Page extends TimerWrapper {
    private long TIMEOUT_IN_SECS = 60;
    public  long SHORT_SLEEP_IN_MILLISECS = 100;
    private boolean isJenkins = false;

    public LoginType loginType;
    public boolean bLoggedIn = false;
    public WebDriver driver = null;

    private static int standardPageSleep = 2000;

    public Page( Page existingPage, LoginType loginType ) {
//        General.setLogin(loginType);
        General.Debug( "creating base Page object" );

        if ( existingPage != null ) {
            driver = existingPage.driver;
            bLoggedIn = existingPage.bLoggedIn;

            if (loginType == null)
                this.loginType = existingPage.loginType;
            else
                this.loginType = loginType;
        }
    }

    public long getTimeoutInSecs() {
        return TIMEOUT_IN_SECS;
    }

    public Page setTimeoutInSecs( long seconds ) {
        TIMEOUT_IN_SECS = seconds;
        return this;
    }

    public String getClassName() {
        return this.getClass().getName();
    }

    public void setJenkins( boolean flag ) {
        isJenkins = flag;
    }

    public boolean isJenkins() {
        return isJenkins;
    }

    public String getTextByXpath( String xpath, boolean nullOk ) {
        General.Debug( getClassName() + "::getTextByXpath" );
        WebElement element = getByXpath( xpath, nullOk );
        if ( element != null )
            return element.getText();
        else
            return null;
    }

    public WebElement getByXpath( String xpath ) {
        return getByXpath( xpath, false );
    }

    public WebElement getByXpath( String xpath, boolean nullOk ) {
        WebElement element = null;
        General.Debug( getClassName() + "::getByXpath(" + xpath + ", " + nullOk + ")", true );

        if ( nullOk ) {
            try {
                General.Sleep( SHORT_SLEEP_IN_MILLISECS * 2 );
                element = new FluentWait<>( driver ).ignoring( TimeoutException.class, NoSuchElementException.class ).until( ExpectedConditions.presenceOfElementLocated( By.xpath( xpath ) ) );
            } catch ( Exception e ) {
                General.Debug(e.toString());

            }
        }
        else
            element = new WebDriverWait( driver, getTimeoutInSecs() ).until( ExpectedConditions.presenceOfElementLocated( By.xpath( xpath ) ) );

        General.Sleep( SHORT_SLEEP_IN_MILLISECS );

        return element;
    }

    public WebElement getByXpathVerifyTextPresent( String xpath, String text ) {
        General.Debug( getClassName() + "::getByXpathVerifyTextPresent(" + xpath + ", " + text + ")", true );
        WebElement element = getByXpath( xpath.replace( "REPLACETHIS", text ), false );
        assert (element != null);

        return element;
    }

    public Page verifyText( String text ) {
        General.Debug( getClassName() + "::verifyText(" + text + ")", true );
        WebElement element = getByXpath( "//*[contains(text(), '" + text + "')]", false );
        assert (element != null);

        return this;
    }

    public Page rightClick( WebElement element ) {
        General.Debug( getClassName() + "::rightClick()" );
        Actions action = new Actions( driver ).contextClick( element );
        action.build().perform();

        return this;
    }

    public Page clickActiveItemByXpath( String xpath ) {
        General.Debug( getClassName() + "::clickActiveItemByXpath(" + xpath + ")" );
        List<WebElement> list = getListByXpath( xpath );

        for ( WebElement element : list )
            if ( element.isDisplayed() && element.isEnabled() ) {
                element.click();
                General.Sleep( 3000 );
            }

        return this;
    }

    public WebElement getListItemByXpath( String xpath, String itemText ) {
        General.Debug( getClassName() + "::getListItemByXpath(" + xpath + ", " + itemText + ")" );
        List<WebElement> list = getListByXpath( xpath );

        return getListItem( list, itemText );
    }

    public WebElement getListItem( List<WebElement> list, String itemText ) {
        General.Debug( getClassName() + "::Verifying existence of " + itemText );

        String text;
        for ( WebElement element : list ) {
            text = element.getText();
            if ( text.contains( itemText ) )
                return element;
        }

        return null;
    }

    public WebElement getChildByXpath( WebElement element, String xpath ) {
        General.Debug( getClassName() + "::getChildByXpath(" + xpath + ")" );
        WebElement childElement = element.findElement( By.xpath( xpath ) );

        return childElement;
    }

    public List<WebElement> getChildListByXpath( WebElement element, String xpath ) {
        List<WebElement> childElements = element.findElements( By.xpath( xpath ) );

        return childElements;
    }

    public List<WebElement> getListByParentElement( WebElement parentElement ) {
        General.Debug( getClassName() + "::getListByParentElement(" + parentElement + ")" );
        List<WebElement> list = parentElement.findElements( By.xpath( ".//*" ) );

        String text;
        for ( WebElement element : list ) {
            text = element.getText();
            General.Debug( text );
        }

        return list;
    }

    public List<WebElement> getListByXpath( String xpath ) {
        return getListByXpath( null, xpath, false );
    }

    public List<WebElement> getListByXpath( String xpath, boolean debugOn ) {
        return getListByXpath( null, xpath, debugOn );
    }

    public List<WebElement> getListByXpath( WebElement element, String xpath ) {
        return getListByXpath( element, xpath, false );
    }

    public List<WebElement> getListByXpath( WebElement element, String xpath, boolean debugOn ) {
        General.Debug( getClassName() + "::getListByXpath(" + xpath + ")" );
        List<WebElement> list;

        if ( element == null )
            list = driver.findElements( By.xpath( xpath ) );
        else
            list = element.findElements( By.xpath( xpath ) );

        if ( debugOn ) {
            for ( WebElement debugElement : list ) {
                if ( debugElement.isDisplayed() ) {
                    General.Debug( "--start of element--" );
                    General.Debug( "  text " + debugElement.getText() );
                    General.Debug( "  tag " + debugElement.getTagName() );
                    General.Debug( "  class " + debugElement.getAttribute( "class" ) );
                    General.Debug( "  title " + debugElement.getAttribute( "title" ) );
                    General.Debug( "  id " + debugElement.getAttribute( "id" ) );
                }
            }
        }

        return list;
    }

    public void removeInactiveElements( List<WebElement> list ) {
        int index = list.size() - 1;

        while ( index >= 0 )
            if ( !list.get( index ).isDisplayed() || !list.get( index ).isEnabled() || list.get( index ).getAttribute( "hidden" ) != null )
                list.remove( index-- );
    }

    public WebElement getByLinkText( String text ) {
        return getByLinkText( text, false );
    }

    public WebElement getByLinkText( String text, boolean nullOk ) {
        General.Debug( getClassName() + "::getByLinkText(" + text + ", " + nullOk + ")" );
        WebElement element = null;

        if ( nullOk ) {
            try {
                element = new FluentWait<>( driver ).ignoring( TimeoutException.class, NoSuchElementException.class ).until( ExpectedConditions.presenceOfElementLocated( By.linkText( text ) ) );
            } catch ( NoSuchElementException e ) {
                ;
            }
        }
        else
            element = new WebDriverWait( driver, getTimeoutInSecs() ).until( ExpectedConditions.presenceOfElementLocated( By.linkText( text ) ) );

        General.Sleep( SHORT_SLEEP_IN_MILLISECS );

        return element;
    }

    public WebElement setSelected( WebElement element, boolean setToSelected ) {
        General.Debug( getClassName() + "::setSelected(" + setToSelected + ")" );
        if ( setToSelected ) {
            // If not selected, click it
            if ( !element.isSelected() )
                element.click();
            // Already selected, do nothing
            return element;
        }

        // We want to unselect it if selected, otherwise do nothing
        if ( element.isSelected() )
            element.click();

        return element;
    }

    public Page setCheckboxByXpath( String xpath, boolean checkOrUncheck ) {
        if ( checkOrUncheck ) {
            if ( !isCheckedByXpath( xpath ) )
                getByXpath( xpath ).click();
            return this;
        }

        if ( isCheckedByXpath( xpath ) )
            getByXpath( xpath ).click();
        return this;
    }

    public boolean isCheckedByXpath( String xpath ) {
        WebElement element = getByXpath( xpath );
        String value = element.getAttribute( "checked" );
        return value == null || value.toLowerCase().equals( "false" ) ? false : true;
    }

    public Page typeByXpath( String xpath, String text ) {
        return typeByXpath( xpath, text, false );
    }

    public Page typeByXpath( String xpath, String text, boolean hitReturn ) {
        WebDriverWait wait = new WebDriverWait( driver, getTimeoutInSecs() );
        WebElement element = wait.until( ExpectedConditions.presenceOfElementLocated( By.xpath( xpath ) ) );
        if ( text.contentEquals( "" ) )
            element.clear();
        else
            element.sendKeys( text );

        if ( hitReturn )
            element.sendKeys( Keys.RETURN );

        return this;
    }

    public Page selectDropdownByXpathAndText( String xpath, String text ) {
        Select dropdown = new Select( driver.findElement( By.xpath( xpath ) ) );
        dropdown.selectByVisibleText( text );

        return this;
    }

    public Page selectDropdownByXpathAndValue( String xpath, String value ) {
        Select dropdown = new Select( driver.findElement( By.xpath( xpath ) ) );
        dropdown.selectByValue( value );

        return this;
    }

    public boolean findTextItemInList( List<WebElement> list, String text ) {
        for ( WebElement element : list )
            if ( element.getText().contains( text ) )
                return true;

        return false;
    }

    public void verifyExists( String xpath ) {
        getByXpath( xpath, false );
    }

    public boolean clickIfExists( String xpath ) {
        return clickIfExists( getByXpath( xpath, true ) );
    }

    public boolean clickIfExists( WebElement element ) {
        if ( element == null || element.isDisplayed() == false )
            return false;

        element.click();
        General.Sleep( 1000 );

        return true;
    }

    static public boolean Sleep() {
        return General.Sleep(standardPageSleep);
    }

    // Implement this to exercise each control on the page individually (cascade == false),
    //   or to instantiate and ExercisePage(true) all of your child pages (cascade == true).

    abstract public Page ExercisePage( boolean cascade );

} // class Page












