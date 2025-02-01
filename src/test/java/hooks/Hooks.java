package hooks;

import helpers.ScreenshotHelper;
import helpers.DriverHelper;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.apache.log4j.Logger;
import settings.DriverSettings;

public class Hooks {

    private static final Logger logger = Logger.getLogger(Hooks.class);

    @Before
    public void beforeScenario(Scenario scenario) {
        try {
            logger.info("Starting Scenario: \"" + scenario.getName() + "\"");
            Allure.addAttachment("Scenario Name", scenario.getName());
            Allure.addAttachment("Scenario Tags", scenario.getSourceTagNames().toString());

            DriverHelper.configureDriver(scenario);
        } catch (Exception e) {
            logger.error("Error before starting scenario: " + scenario.getName(), e);
        }
    }

    @After
    public void afterScenario(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                logger.error("Scenario Failed: \"" + scenario.getName() + "\"");
                ScreenshotHelper.captureScreenshot();
            } else {
                logger.info("Scenario Passed: \"" + scenario.getName() + "\"");
            }

            DriverSettings.quitDriver();
            logger.info("Scenario Finished: \"" + scenario.getName() + "\"");
        } catch (Exception e) {
            logger.error("Error after finishing scenario: " + scenario.getName(), e);
        }
    }
}
