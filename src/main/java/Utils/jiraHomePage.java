
package Utils;

/*
 * Created by timothy.hallbeck on 5/23/2015.
 */

public class jiraHomePage extends WebPage {
    public jiraHomePage() {
        super( null, LoginType.JIRA_HOMEPAGE, Browser.Firefox );
    }

    public boolean login() {
        if (bLoggedIn)
            return false;

        get(loginType.getStartingUrl());
        WaitForPage();
//        switchToFrame("gadget-0");
        getByXpath(loginType.getUsernameXpath()).clear();
        getByXpath(loginType.getUsernameXpath()).sendKeys(loginType.getUsername());
        getByXpath(loginType.getPasswordXpath()).clear();
        getByXpath(loginType.getPasswordXpath()).sendKeys(loginType.getPassword());
        getByXpath(loginType.getLoginButtonXpath()).click();
        RestoreDefaultFrameAndWindow();

        getByXpath("//*[@id='dashboard-content']/div[1]");
        bLoggedIn = true;

        return true;
    }

    public jiraHomePage ExercisePage(boolean cascade) {
        General.Debug("\njiraHomePage::ExercisePage(" + cascade + ")");
        login();

        if (cascade) {
            // ...
            return this;
        }

        return this;
    }

    public jiraHomePage WaitForPage() {
        getByXpath("//*[@id='footer']/section/ul/li[1]");
        getByXpath("//*[@id='publicmodeoffmsg']");
        General.Sleep(2000);

        return this;
    }
}
