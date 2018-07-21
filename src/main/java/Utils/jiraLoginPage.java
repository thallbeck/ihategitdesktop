
package Utils;

/*
 * Created by timothy.hallbeck on 5/23/2015.
 */

public class jiraLoginPage extends WebPage {

    public jiraLoginPage(WebPage existingPage) {
        super(existingPage);
    }

    public jiraLoginPage ExercisePage(boolean cascade) {
        General.Debug("\njiraLoginPage::ExercisePage(" + cascade + ")");
        load();

        if (cascade) {
            return this;
        }

        return this;
    }
}
