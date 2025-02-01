package helpers;

import settings.DriverSettings;
import utils.ExceptionHandler;

public class NavigationHelper {

    private NavigationHelper() {

    }

    public static void navigateToUrl(String url) {
        try {
            DriverSettings.getDriver().get(url);
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to navigate to URL: " + url, e);
        }
    }

    public static void refreshPage() {
        try {
            DriverSettings.getDriver().navigate().refresh();
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to refresh the page", e);
        }
    }

    public static void navigateBack() {
        try {
            DriverSettings.getDriver().navigate().back();
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to navigate back", e);
        }
    }

    public static void navigateForward() {
        try {
            DriverSettings.getDriver().navigate().forward();
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to navigate forward", e);
        }
    }
}
