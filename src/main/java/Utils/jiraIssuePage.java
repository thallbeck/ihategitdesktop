
package Utils;

/*
 * Created by timothy.hallbeck on 5/23/2015.
 */

public class jiraIssuePage extends WebPage {

    public jiraIssuePage(WebPage existingPage) {
        super(existingPage);
    }

    public jiraIssuePage ExercisePage(boolean cascade) {
        General.Debug("\njiraIssuePage::ExercisePage(" + cascade + ")");
        load();

        if (cascade) {
            return this;
        }

        return this;
    }
}
