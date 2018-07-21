
package Utils;

//import MobilePages.*;

import org.apache.poi.xslf.usermodel.XMLSlideShow;

import java.io.FileInputStream;

/*
 * Created by timothy.hallbeck on 4/11/2017.
 * Copyright (c) Hallbeck Pepper Company LLC
 * All rights reserved
 */

public class PowerPointUtil {
    static String LOCAL_POWERPOINT_PATH           = General.DATA_PATH + "Screenshots/";
    static String LOCAL_POWERPOINT_PHONE_TEMPLATE = "AndroidTemplate.pptx";
    static String LOCAL_POWERPOINT_WEB_TEMPLATE   = "WebTemplate.pptx";

    // This is static so all test pages add to the same slideshow object
    //   Due to the single emulator limitation we run single threaded always, so multi access is not an issue
    public static XMLSlideShow slides = null;
    public static boolean saveScreenshots    = false;
    public static boolean savePresentation   = false;
    public static boolean attachPresentation = false;

    public PowerPointUtil() {}

    // Adds a single bullet point to the current slide.
    public static void addBulletPoint(String text) {
//        if (slides == null)
            return;
/*
        List<XSLFSlide> allSlides = slides.getSlides();
        Assert.assertTrue(allSlides.size() > 0, "Trying to add bullet to slide before calling PowerPointUtil.saveScreenshot(this, )");
        XSLFSlide currentSlide = allSlides[allSlides.size() - 1];
        General.Debug("addBulletPoint(" + text + ")");

        XSLFTextShape bullet = currentSlide.getPlaceholder(1);
//        if (bullet.getText().contains(MobileXpath.Presentation_DefaultBullet))
//            bullet.setText(text);
//        else {
            XSLFTextParagraph paragraph = bullet.addNewTextParagraph();
            XSLFTextRun run = paragraph.addNewTextRun();
            run.setFontSize(14);
            run.setText(text);
//        }
*/    }

    public static void InitPowerPoint(Class theClass) {
        String path = LOCAL_POWERPOINT_PATH;

//        if (MobileAppPage.class.isAssignableFrom(theClass))
//            path += LOCAL_POWERPOINT_PHONE_TEMPLATE;
 //       else
            path += LOCAL_POWERPOINT_WEB_TEMPLATE;

        try {
            slides = new XMLSlideShow(new FileInputStream(path));
        } catch (Exception e) {
            General.Debug("Error in new XMLSlideShow(new FileInputStream(path))");
            throw new RuntimeException();
        }
    }

    public static void saveScreenshot( Page page, String description) {
        // saveScreenshots can be off while savePresentation is on
//        if (!(saveScreenshots || savePresentation))
            return;
/*
        if (slides == null)
            InitPowerPoint(page.getClass());

        General.Debug("saveScreenshot(" + description + ")");
        File scrFile = ((TakesScreenshot) page.driver).getScreenshotAs(OutputType.FILE);
        String fullPngName = "";

        try {
            String datePrefix = General.GetDateAndTimeFilePrefix();

            fullPngName = LOCAL_POWERPOINT_PATH + datePrefix + StringUtils.cleanoutSpecialChars(description) + ".png";
            FileUtils.copyFile(scrFile, new File(fullPngName));

            XSLFSlideMaster defaultMaster = slides.getSlideMasters()[0];
            XSLFSlideLayout[] layouts = defaultMaster.getSlideLayouts();

            XSLFSlide blankSlide = slides.createSlide(layouts[0]);

            // Title
            XSLFTextShape title = blankSlide.getPlaceholder(0);
            title.setText(description);

            // Default bullet
            XSLFTextShape body = blankSlide.getPlaceholder(1);
            body.clearText();
            XSLFTextParagraph paragraph = body.addNewTextParagraph();
            XSLFTextRun run = paragraph.addNewTextRun();
            run.setFontSize(14);
            run.setText(MobileXpath.Presentation_DefaultBullet);

            // Screenshot
            byte[] pictureData = IOUtils.toByteArray(new FileInputStream(fullPngName));
            int index = slides.addPicture(pictureData, XSLFPictureData.PICTURE_TYPE_PNG);

            // The template has a picture placeholder, but we only use the anchor
            XSLFTextShape picPlaceholder = blankSlide.getPlaceholder(2);
            XSLFPictureShape shape = blankSlide.createPicture(index);
            shape.setAnchor(picPlaceholder.getAnchor());
        } catch (IOException e) {
            e.printStackTrace();
            throw new TestNGException("Fatal error in PowerPointUtil::saveScreenshot()");
        } finally {
            // If saving the presentation but not the individual screenshots, delete the .png because we're done with it
            if (!saveScreenshots) {
                Page.Sleep(1000);
                try {
                    FileUtils.forceDelete(new File(fullPngName));
                } catch (IOException e) {
                }
            }
        }
 */   }

    // Both saves the presentation and attaches it to Jira if info supplied
    public static void savePresentation( Page page, String jiraIssue, String pptxName) {
//        if (page == null || !savePresentation || slides == null)
            return;
/*
        // First slide is blank, delete it
        slides.removeSlide(0);
        String fullPath = "";

        try {
            if (page.VERSION.isEmpty())
                fullPath = LOCAL_POWERPOINT_PATH + General.GetDateAndTimeFilePrefix() + pptxName;
            else
                fullPath = LOCAL_POWERPOINT_PATH + "v" + page.VERSION + "_" + General.GetDateAndTimeFilePrefix() + pptxName;

            General.Debug("Saving " + slides.getSlides().length + " slides to " + fullPath);

            File file = new File(fullPath);
            if (file.exists())
                file.delete();
            Thread.sleep(1000);
            FileOutputStream out = new FileOutputStream(fullPath);
            slides.write(out);
            out.close();
        } catch (Exception e) {
            General.assertDebug(false, "Fatal system error in savePresentation()");
            System.exit(1);
        }

        // Attach to Jira issue
        if (attachPresentation && jiraIssue.length() != 0) {
            General.Debug("Attaching presentation to " + jiraIssue);
            wguJiraUtils.attachFileToIssue(jiraIssue, fullPath);
        }
 */   }

}
