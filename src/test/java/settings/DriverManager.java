package settings;

import io.cucumber.java.Scenario;
import org.apache.log4j.Logger;
import utils.ExceptionHandler;

public class DriverManager {

    private static final Logger logger = Logger.getLogger(DriverManager.class);

    public static void configureDriverBasedOnTag(Scenario scenario) {
        try {
            String browser = scenario.getSourceTagNames()
                    .stream()
                    .filter(tag -> tag.startsWith("@browser:"))
                    .map(tag -> tag.split(":")[1])
                    .findFirst()
                    .orElse("chrome");

            boolean isMobile = scenario.getSourceTagNames().contains("@mobile");

            logger.info("Scenario tags: " + scenario.getSourceTagNames() + " -> Browser: " + browser
                    + ", Mobile: " + isMobile);

            DriverSettings.setDriver(browser, isMobile);
            logger.info("WebDriver setup successful for browser: " + browser + " (Mobile mode: " + isMobile + ")");

        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to set up WebDriver for scenario: " + scenario.getName(), e);
        }
    }
}
