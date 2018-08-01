/*
 * Created on 7/26/2018.
 */

import Utils.General;
import Utils.LoginType;
import Utils.WebPage;
import org.testng.annotations.Test;

import java.util.NavigableSet;
import java.util.TreeMap;


public class SampleSiteCrawler {

    private String[] inclusionFilterArray = new String[] {
        "https://www.carbonblack.com",
        "https://partners.carbonblack.com",
        "https://community.carbonblack.com"
    };

    @Test
    public void CarbonBlackCrawler() throws Exception {
        String startingUrl = "https://www.carbonblack.com";
        TreeMap masterMap   = new TreeMap();
        WebPage startPage = null;

        try {

            startPage = new WebPage( null, LoginType.SAMPLE_CARBONBLACK, WebPage.Browser.Firefox );
            startPage.load();
            startPage.DocumentAllHrefs(masterMap, 1, startingUrl, inclusionFilterArray);
            startPage.CreatePageClasses(masterMap, startingUrl, General.SOURCE_LANGUAGE.Java);
            NavigableSet mySet = masterMap.navigableKeySet();
            int size = masterMap.size();

        } catch (Exception e) {

            // the ppt library has changed significantly, I need to redo my usage before this call will work
//            PowerPointFileUtils.saveScreenshot(startPage, "ERROR SAMPLE_CARBONBLACK");
            throw new Exception(e);

        } finally {

//            PowerPointFileUtils.savePresentation(startPage, "", "SAMPLE_CARBONBLACK.pptx");
            startPage.quit();

        }
    }

}
