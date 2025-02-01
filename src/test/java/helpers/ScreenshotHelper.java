package helpers;

import io.qameta.allure.Attachment;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import settings.DriverSettings;
import utils.ExceptionHandler;

public class ScreenshotHelper {

    private static final Logger logger = Logger.getLogger(ScreenshotHelper.class);

    @Attachment(value = "Screenshot on Failure", type = "image/png")
    public static byte[] captureScreenshot() {
        try {
            if (DriverSettings.getDriver() instanceof TakesScreenshot) {
                logger.info("Capturing screenshot for failed scenario.");
                return ((TakesScreenshot) DriverSettings.getDriver()).getScreenshotAs(OutputType.BYTES);
            } else {
                logger.warn("Current WebDriver does not support screenshots.");
                return new byte[0];
            }
        } catch (Exception e) {
            ExceptionHandler.handleException("Error while taking screenshot", e);
            return new byte[0];
        }
    }
}
