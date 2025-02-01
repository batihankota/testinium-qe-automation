package steps;

import helpers.ElementHelper;
import helpers.WaitHelper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import utils.ExceptionHandler;

public class WaitSteps {

    private static final Logger logger = Logger.getLogger(WaitSteps.class);

    @Step("Wait until element {elementKey} is visible")
    @Then("Wait until element {string} is visible")
    public void waitForElementToBeVisible(String elementKey) {
        try {
            ElementHelper.setElement(elementKey);
            WaitHelper.waitForElementToBeVisible(ElementHelper.getElement());
            logger.info("Waited until element '" + elementKey + "' became visible.");
        } catch (Exception e) {
            ExceptionHandler.handleException("Error occurred while waiting for element '" + elementKey + "' to be visible", e);
        }
    }

    @Step("Wait until element {elementKey} is clickable")
    @Then("Wait until element {string} is clickable")
    public void waitForElementToBeClickable(String elementKey) {
        try {
            ElementHelper.setElement(elementKey);
            WaitHelper.waitForElementToBeClickable(ElementHelper.getElement());
            logger.info("Waited until element '" + elementKey + "' became clickable.");
        } catch (Exception e) {
            ExceptionHandler.handleException("Error occurred while waiting for element '" + elementKey + "' to be clickable", e);
        }
    }

    @Step("Wait for {seconds} seconds")
    @When("Wait for {int} seconds")
    public void waitForSeconds(int seconds) {
        try {
            logger.info("Waiting for " + seconds + " seconds.");
            WaitHelper.waitForSeconds(seconds);
            logger.info("Waited for " + seconds + " seconds.");
        } catch (Exception e) {
            ExceptionHandler.handleException("Error occurred while waiting for " + seconds + " seconds", e);
        }
    }
}
