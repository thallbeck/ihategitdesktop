
package Utils;

/*
 * Created by timothy.hallbeck on 4/11/2017.
 * Copyright (c) Hallbeck Pepper Company LLC
 * All rights reserved
 */

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

public class WindowUtils {
    WindowUtils() {}

    // awt dimension and selenium dimension classes are not compatible, so need this helper method
    public static void setSize(WebDriver driver, int width, int height) {
        driver.manage().window().setSize(new Dimension(width, height));

    }
}
