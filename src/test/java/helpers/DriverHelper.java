package helpers;

import io.cucumber.java.Scenario;
import settings.DriverManager;
import utils.ExceptionHandler;

public class DriverHelper {

    public static void configureDriver(Scenario scenario) {
        try {
            DriverManager.configureDriverBasedOnTag(scenario);
        } catch (Exception e) {
            ExceptionHandler.handleException("Error configuring WebDriver for scenario: " + scenario.getName(), e);
        }
    }
}
