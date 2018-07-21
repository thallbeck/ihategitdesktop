
package Utils;

/*
 * Created by timothy.hallbeck on 5/26/2015.
 */

public class wguJiraUtils {

    wguJiraUtils() {}

    static public void attachFileToIssue(String jiraIssue, String pptxFullPath) {
        assert(pptxFullPath.length() > 0 && jiraIssue.contains("MAUT"));
        jiraHomePage myPage = new jiraHomePage();

        myPage.login();
        myPage.get("https://jira-it.wgu.edu/browse/" + jiraIssue);
        Page.Sleep();
        myPage.getByXpath("//*[@id='opsbar-operations_more']").click();
        Page.Sleep();
        myPage.getByXpath("//*[@id='attach-file']/span").click();
        Page.Sleep();
        myPage.getByXpath("//*[@id='attach-file-dialog']");

        // Latest selenium checks path, doesnt supply default drive letter anymore
        if ( General.getOS() == General.OS.Windows)
            pptxFullPath = "C:" + pptxFullPath;
        myPage.getByXpath("//input[@type='file']").sendKeys(pptxFullPath);
        General.Sleep(12000);
        myPage.getByXpath("//*[@id='attach-file-submit']").click();
        General.Sleep(4000);
        myPage.closeTab();
    }

}
