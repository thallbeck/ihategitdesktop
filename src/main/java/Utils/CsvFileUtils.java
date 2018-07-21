
package Utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * Created by timothy.hallbeck on 4/11/2017.
 * Copyright (c) Hallbeck Pepper Company LLC
 * All rights reserved
 */

public class CsvFileUtils {
    CsvFileUtils() {}

    public static void appendDataToFile(String data, String file) {
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(General.DATA_PATH + file, true)))) {
            out.println(data);
        } catch (IOException e) {
            General.Debug(e.getMessage());
        }
    }

}
