
package Utils;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Created by timothy.hallbeck on 4/11/2017.
 * Copyright (c) Hallbeck Pepper Company LLC
 * All rights reserved
 */

public class RobotTyper extends Robot {

    public static final String KEY_BACKSP = "\b";
    public static final String KEY_DELETE = "\ue006";
    public static final String KEY_END    = "\ue007";
    public static final String KEY_ESCAPE = "\ue001b";
    public static final String KEY_HOME   = "\ue008";

    public RobotTyper() throws AWTException {
    }

    public RobotTyper hitReturn() {
        keyPress(KeyEvent.VK_ENTER);
        keyRelease(KeyEvent.VK_ENTER);
        return this;
    }

    public RobotTyper hitDelete() {
        keyPress(KeyEvent.VK_DELETE);
        keyRelease(KeyEvent.VK_DELETE);
        return this;
    }

    public RobotTyper hitTab() {
        keyPress(KeyEvent.VK_TAB);
        keyRelease(KeyEvent.VK_TAB);
        return this;
    }

    public RobotTyper hitEscape() {
        keyPress(KeyEvent.VK_ESCAPE);
        keyRelease(KeyEvent.VK_ESCAPE);
        return this;
    }

    public RobotTyper LeftClickMouse() {
        mousePress(InputEvent.BUTTON1_DOWN_MASK);
        mouseRelease(InputEvent.BUTTON1_MASK);
        return this;
    }

    public RobotTyper RightClickMouse() {
        mousePress(InputEvent.BUTTON3_DOWN_MASK);
        mouseRelease(InputEvent.BUTTON3_MASK);
        return this;
    }

    public RobotTyper SendKeys( String string) {
        int keycode;
        boolean bShift;
//        boolean bAlt;
//        boolean bControl;

        General.Debug("Typing(" + string + ")");
        for (int i=0; i<string.length(); i++) {
            bShift   = false;
//            bAlt     = false;
//            bControl = false;

            switch (string.charAt(i)) {
                case ' ':
                    keycode = KeyEvent.VK_SPACE;
                    break;
                case 'A':
                    bShift = true;
                case 'a':
                    keycode = KeyEvent.VK_A;
                    break;
                case 'B':
                    bShift = true;
                case 'b':
                    keycode = KeyEvent.VK_B;
                    break;
                case 'C':
                    bShift = true;
                case 'c':
                    keycode = KeyEvent.VK_C;
                    break;
                case 'D':
                    bShift = true;
                case 'd':
                    keycode = KeyEvent.VK_D;
                    break;
                case 'E':
                    bShift = true;
                case 'e':
                    keycode = KeyEvent.VK_E;
                    break;
                case 'F':
                    bShift = true;
                case 'f':
                    keycode = KeyEvent.VK_F;
                    break;
                case 'G':
                    bShift = true;
                case 'g':
                    keycode = KeyEvent.VK_G;
                    break;
                case 'H':
                    bShift = true;
                case 'h':
                    keycode = KeyEvent.VK_H;
                    break;
                case 'I':
                    bShift = true;
                case 'i':
                    keycode = KeyEvent.VK_I;
                    break;
                case 'J':
                    bShift = true;
                case 'j':
                    keycode = KeyEvent.VK_J;
                    break;
                case 'K':
                    bShift = true;
                case 'k':
                    keycode = KeyEvent.VK_K;
                    break;
                case 'L':
                    bShift = true;
                case 'l':
                    keycode = KeyEvent.VK_L;
                    break;
                case 'M':
                    bShift = true;
                case 'm':
                    keycode = KeyEvent.VK_M;
                    break;
                case 'N':
                    bShift = true;
                case 'n':
                    keycode = KeyEvent.VK_N;
                    break;
                case 'O':
                    bShift = true;
                case 'o':
                    keycode = KeyEvent.VK_O;
                    break;
                case 'P':
                    bShift = true;
                case 'p':
                    keycode = KeyEvent.VK_P;
                    break;
                case 'Q':
                    bShift = true;
                case 'q':
                    keycode = KeyEvent.VK_Q;
                    break;
                case 'R':
                    bShift = true;
                case 'r':
                    keycode = KeyEvent.VK_R;
                    break;
                case 'S':
                    bShift = true;
                case 's':
                    keycode = KeyEvent.VK_S;
                    break;
                case 'T':
                    bShift = true;
                case 't':
                    keycode = KeyEvent.VK_T;
                    break;
                case 'U':
                    bShift = true;
                case 'u':
                    keycode = KeyEvent.VK_U;
                    break;
                case 'V':
                    bShift = true;
                case 'v':
                    keycode = KeyEvent.VK_V;
                    break;
                case 'W':
                    bShift = true;
                case 'w':
                    keycode = KeyEvent.VK_W;
                    break;
                case 'X':
                    bShift = true;
                case 'x':
                    keycode = KeyEvent.VK_X;
                    break;
                case 'Y':
                    bShift = true;
                case 'y':
                    keycode = KeyEvent.VK_Y;
                    break;
                case 'Z':
                    bShift = true;
                case 'z':
                    keycode = KeyEvent.VK_Z;
                    break;
                case '0':
                    keycode = KeyEvent.VK_0;
                    break;
                case '1':
                    keycode = KeyEvent.VK_1;
                    break;
                case '2':
                    keycode = KeyEvent.VK_2;
                    break;
                case '3':
                    keycode = KeyEvent.VK_3;
                    break;
                case '4':
                    keycode = KeyEvent.VK_4;
                    break;
                case '5':
                    keycode = KeyEvent.VK_5;
                    break;
                case '6':
                    keycode = KeyEvent.VK_6;
                    break;
                case '7':
                    keycode = KeyEvent.VK_7;
                    break;
                case '8':
                    keycode = KeyEvent.VK_8;
                    break;
                case '9':
                    keycode = KeyEvent.VK_9;
                    break;
                case '&':
                    bShift = true;
                    keycode = KeyEvent.VK_7;
                    break;
                case '~':
                    bShift = true;
                    keycode = KeyEvent.VK_BACK_QUOTE;
                    break;
                case '\'':
                    keycode = KeyEvent.VK_QUOTE;
                    break;
                case '\"':
                    bShift = true;
                    keycode = KeyEvent.VK_QUOTEDBL;
                    break;
                case ':':
                    bShift = true;
                    keycode = KeyEvent.VK_SEMICOLON ;
                    break;
                case ';':
                    keycode = KeyEvent.VK_SEMICOLON ;
                    break;
                case '.':
                    keycode = KeyEvent.VK_PERIOD;
                    break;
                case '/':
                    keycode = KeyEvent.VK_SLASH;
                    break;
                case '\\':
                    keycode = KeyEvent.VK_BACK_SLASH;
                    break;
                case '-':
                    keycode = KeyEvent.VK_MINUS;
                    break;
                case '_':
                    bShift = true;
                    keycode = KeyEvent.VK_MINUS;
                    break;
                case '<':
                    bShift = true;
                    keycode = KeyEvent.VK_COMMA;
                    break;
                case '>':
                    bShift = true;
                    keycode = KeyEvent.VK_PERIOD;
                    break;
                case '?':
                    bShift = true;
                    keycode = KeyEvent.VK_SLASH;
                    break;
                case '=':
                    keycode = KeyEvent.VK_EQUALS;
                    break;
                case '%':
                    bShift = true;
                    keycode = KeyEvent.VK_5;
                    break;

                default:
                    General.Debug("Character not implemented in RobotTyper: " + string.charAt(i));
                    throw new NotImplementedException();
            }

            if (bShift)
                keyPress(KeyEvent.VK_SHIFT);
//            if (bAlt)
//                keyPress(KeyEvent.VK_ALT);
//            if (bControl)
//                keyPress(KeyEvent.VK_CONTROL);

            keyPress(keycode);
            keyRelease(keycode);

//            if (bControl)
//                keyRelease(KeyEvent.VK_CONTROL);
//            if (bAlt)
//                keyRelease(KeyEvent.VK_ALT);
            if (bShift)
                keyRelease(KeyEvent.VK_SHIFT);

        }
        return this;
    }

    public boolean Sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    public String GenerateUrlMetricsFile( WebPage page, String url, String outputFileName) throws AWTException, InterruptedException {
        page.fullScreen();

        Date date = new Date() ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm") ;
        String dateName = dateFormat.format(date);
        outputFileName = outputFileName + "_" + dateName + ".har";

        mouseMove(1900, 35); // Chrome menu
        Sleep(1000);
        LeftClickMouse();
        Sleep(1000);

        mouseMove(1750, 530); // move down to other tools item
        Sleep(1000); // let it render
        mouseMove(1550, 530); // move left to the popup menu
        Sleep(1000);
        mouseMove(1550, 700); // more down to the dev console item
        Sleep(1000);
        LeftClickMouse();

//        mouseMove(1620, 700); // move over the window divider
//        Sleep(1000);
//        mousePress(InputEvent.BUTTON1_DOWN_MASK); // hold down the left mouse button
//        mouseMove(1450, 700); // drag it to the left so more dev console is visible
//        Sleep(1000);
//        mouseRelease(InputEvent.BUTTON1_MASK); // release the drag
        Sleep(1000);
        mouseMove(1510, 70); // move over the Network item
        Sleep(1000);
        LeftClickMouse();

        Sleep(1000);
        mouseMove(1522, 97); // move over the Preserve Log checkbox
        Sleep(1000);
        LeftClickMouse();

        Sleep(1000);
        mouseMove(1380, 95); // move over the Stop button
        Sleep(1000);
        LeftClickMouse();

        Sleep(1000);
        mouseMove(1410, 95); // move over the Clear button
        Sleep(1000);
        LeftClickMouse();

        Sleep(1000);
        mouseMove(1380, 95); // move over the Start button
        Sleep(1000);
        LeftClickMouse();

        mouseMove(800, 45); // move over the URL edit control
        Sleep(1000);
        LeftClickMouse();
        Sleep(1000);

        SendKeys(url);
        hitReturn();

        Sleep(20000); // give it some time. extra doesn't matter as we use the raw timings
        mouseMove(1380, 95); // move over the Stop button
        Sleep(1000);
        LeftClickMouse();
        Sleep(1000);

        // scroll to the top to get a consistent menu
        mouseMove(1910, 175);
        for (int i=0;i<20;i++) {
            Sleep(250);
            LeftClickMouse();
        }

        mouseMove(1890, 145); // move to a nice neutral area
        Sleep(1000);
        RightClickMouse();
        Sleep(1000);

        // move to the save as HAR item
        if (outputFileName.contains("YouTube"))
            mouseMove(1890, 245);
        else
            mouseMove(1890, 300);
        Sleep(1000);
        LeftClickMouse();
        Sleep(1000);

        SendKeys(outputFileName);
        hitReturn();
        Sleep(1000);
        hitTab();
        hitReturn();

        Sleep(3000);
        return outputFileName;
    }

/*    public void ChangeExcelChartType(String fullPath) throws Exception {
        ChangeExcelChartType(fullPath, ExcelFileUtils.chartType.COLUMN_STACKED);
    }

    public void ChangeExcelChartType(String fullPath, ExcelFileUtils.chartType type) throws Exception {
        Runtime.getRuntime().exec("C:\\Program Files (x86)\\Microsoft Office\\Office15\\EXCEL.EXE " + fullPath);
        Sleep(2000);

        // Move mouse over the chart and right click to bring up menu
        mouseMove(600, 200);
        Sleep(1000);
        RightClickMouse();
        Sleep(1000);

        // Move cursor over Change Chart Type and click
        mouseMove(675, 275);
        Sleep(1000);
        LeftClickMouse();
        Sleep(1000);

        // Click on Column
        mouseMove(675, 200);
        Sleep(1000);
        LeftClickMouse();
        Sleep(1000);

        // Click on picture (stacked or 3D clustered) and hit return (ok button)
        if (type == ExcelFileUtils.chartType.COLUMN_STACKED)
            mouseMove(875, 150);
        else // 3D clustered. Only 2 types supported currently
            mouseMove(1000, 150);
        LeftClickMouse();
        Sleep(1000);
        hitReturn();
        Sleep(1000);

        // Move to the top right and close the Excel window
        mouseMove(1900, 15);
        Sleep(1000);
        LeftClickMouse();
        Sleep(1000);
        hitReturn();
        Sleep(1000);

    }
    */
}
