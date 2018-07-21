
import Utils.General;
import Utils.LoginType;
import Utils.WebPage;
import org.testng.annotations.Test;

/*
 * Created by timothy.hallbeck on 4/11/2017.
 * Copyright (c) Hallbeck Pepper Company LLC
 * All rights reserved
 */

public class SamplePageTest {

    @Test( invocationCount = 1, threadPoolSize = 1 )
    public void BringUpHomePage() throws Exception {

        WebPage startPage = null;
        General.setSilentMode(false);
        try {

            startPage = new WebPage( null, LoginType.SAMPLE, WebPage.Browser.Firefox );
            startPage.ExercisePage( true );
            startPage.ExecuteRandomMethods( 20 );

        } finally {

            if ( startPage != null )
                startPage.quit();

        }
    }

}