package steps;

import helpers.NavigationHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import utils.ExceptionHandler;

public class NavigationSteps {

    private static final Logger logger = Logger.getLogger(NavigationSteps.class);

    @Step("Navigate to the URL: {url}")
    @Given("Navigate to {string} url")
    public void navigateToUrl(String url) {
        try {
            NavigationHelper.navigateToUrl(url);
            logger.info("Successfully navigated to URL: " + url);
        } catch (Exception e) {
            ExceptionHandler.handleException("Error navigating to URL: " + url, e);
        }
    }

    @Step("Refresh the page")
    @When("Refresh the page")
    public void refreshPage() {
        try {
            NavigationHelper.refreshPage();
            logger.info("Page refreshed successfully");
        } catch (Exception e) {
            ExceptionHandler.handleException("Error refreshing the page", e);
        }
    }

    @Step("Navigate back")
    @When("Navigate back")
    public void navigateBack() {
        try {
            NavigationHelper.navigateBack();
            logger.info("Navigated back successfully");
        } catch (Exception e) {
            ExceptionHandler.handleException("Error navigating back", e);
        }
    }

    @Step("Navigate forward")
    @When("Navigate forward")
    public void navigateForward() {
        try {
            NavigationHelper.navigateForward();
            logger.info("Navigated forward successfully");
        } catch (Exception e) {
            ExceptionHandler.handleException("Error navigating forward", e);
        }
    }
}
