package helpers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import settings.DriverSettings;
import utils.ExceptionHandler;

import java.time.Duration;

public class WaitHelper {

    private static final int DEFAULT_WAIT_TIME = 5;

    private WaitHelper() {

    }

    public static void waitForElementToBeVisible(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverSettings.getDriver(), Duration.ofSeconds(DEFAULT_WAIT_TIME));
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            ExceptionHandler.handleException("Error occurred while waiting for element to be visible", e);
        }
    }

    public static void waitForElementToBeClickable(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverSettings.getDriver(), Duration.ofSeconds(DEFAULT_WAIT_TIME));
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            ExceptionHandler.handleException("Error occurred while waiting for element to be clickable", e);
        }
    }

    public static void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            ExceptionHandler.handleException("Interrupted while waiting for " + seconds + " seconds", e);
            Thread.currentThread().interrupt();
        }
    }
}
