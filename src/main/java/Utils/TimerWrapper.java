
package Utils;

import org.apache.commons.lang3.time.StopWatch;

/*
 * Created by timothy.hallbeck on 4/11/2017.
 * Copyright (c) Hallbeck Pepper Company LLC
 * All rights reserved
 */

public class TimerWrapper extends StopWatch {

    public TimerWrapper() {
        super();
    }

    public void startTimer() {
        try {
            this.reset();
            this.start();
        } catch (IllegalStateException e) {
            General.Debug( "IllegalStateException in startTimer()" );
        }
    }

    public long stopAndLogTime(String displayText) {
        return stopAndLogTime(displayText, false);
    }

    public long stopAndLogTime(String displayText, boolean asIs) {
        try {
            this.stop();
        } catch (IllegalStateException e) {
            ;
        }

        long millis = this.getTime();
         if (millis > 0) {
            if (asIs) {
                General.Debug(displayText);
            } else {
                if (millis == 1)
                    General.Debug(displayText + ": " + millis + " ms\n");
                else
                    General.Debug(displayText + ": " + millis + " ms\n");
            }
        }
        return millis;
    }

}
