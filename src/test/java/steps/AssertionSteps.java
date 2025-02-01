package steps;

import helpers.ElementHelper;
import utils.ExceptionHandler;
import io.cucumber.java.en.Then;
import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import helpers.AssertionHelper;

public class AssertionSteps {

    private static final Logger logger = Logger.getLogger(AssertionSteps.class);

    @Step("Verify text of element: {elementKey} is {expectedText}")
    @Then("Verify text of element {string} is {string}")
    public void verifyElementText(String elementKey, String expectedText) {
        try {
            ElementHelper.setElement(elementKey);
            AssertionHelper.verifyTextMatch(ElementHelper.getElement().getText(), expectedText, elementKey);
            logger.info("Successfully verified text of element '" + elementKey + "' as expected: '" + expectedText + "'.");
        } catch (Exception e) {
            ExceptionHandler.handleException("Error verifying text of element '" + elementKey + "'", e);
        }
    }

    @Step("Verify text of element {elementKey} should match {regexPattern}")
    @Then("Verify text of element {string} should match {string}")
    public void verifyElementTextMatchesPattern(String elementKey, String regexPattern) {
        try {
            AssertionHelper.verifyTextMatchesPattern(elementKey, regexPattern);
            logger.info("Successfully verified that text of element '" + elementKey + "' matches pattern: '" + regexPattern + "'.");
        } catch (Exception e) {
            ExceptionHandler.handleException("Error verifying text pattern of element '" + elementKey + "'", e);
        }
    }

    @Step("Verify value of element {elementKey} should match {regexPattern}")
    @Then("Verify value of element {string} should match {string}")
    public void verifyElementValueMatchesPattern(String elementKey, String regexPattern) {
        try {
            AssertionHelper.verifyValueMatchesPattern(elementKey, regexPattern);
            logger.info("Successfully verified that value of element '" + elementKey + "' matches pattern: '" + regexPattern + "'.");
        } catch (Exception e) {
            ExceptionHandler.handleException("Error verifying value pattern of element '" + elementKey + "'", e);
        }
    }

    @Step("Verify numeric value of element {elementKey}")
    @Then("Verify numeric value of element {string}")
    public void verifyNumericValue(String elementKey) {
        try {
            AssertionHelper.verifyNumericValue(elementKey);
            logger.info("Successfully verified numeric value of element '" + elementKey + "'.");
        } catch (Exception e) {
            ExceptionHandler.handleException("Error verifying numeric value of element '" + elementKey + "'", e);
        }
    }

    @Step("Verify presence of element: {elementKey}")
    @Then("Verify presence of element {string}")
    public void verifyElementPresence(String elementKey) {
        try {
            AssertionHelper.verifyElementPresence(elementKey);
            logger.info("Successfully verified presence of element '" + elementKey + "'.");
        } catch (Exception e) {
            ExceptionHandler.handleException("Error verifying presence of element '" + elementKey + "'", e);
        }
    }

    @Step("Verify element {elementKey} is disabled")
    @Then("Verify element {string} is disabled")
    public void verifyElementIsDisabled(String elementKey) {
        try {
            ElementHelper.setElement(elementKey);
            if (!AssertionHelper.isElementDisabled()) {
                throw new AssertionError("Expected element '" + elementKey + "' to be disabled, but it was not.");
            }
            logger.info("Successfully verified that element '" + elementKey + "' is disabled.");
        } catch (Exception e) {
            ExceptionHandler.handleException("Error verifying disabled state of element '" + elementKey + "'", e);
        }
    }

    @Step("Verify element {elementKey} is clickable")
    @Then("Verify element {string} is clickable")
    public void verifyElementIsClickable(String elementKey) {
        try {
            ElementHelper.setElement(elementKey);
            if (!ElementHelper.getElement().isDisplayed() || !ElementHelper.getElement().isEnabled() || AssertionHelper.isElementDisabled()) {
                throw new AssertionError("Expected element '" + elementKey + "' to be clickable, but it is not.");
            }
            logger.info("Successfully verified that element '" + elementKey + "' is clickable.");
        } catch (Exception e) {
            ExceptionHandler.handleException("Error verifying clickable state of element '" + elementKey + "'", e);
        }
    }

    @Step("Verify element {elementKey} is not clickable")
    @Then("Verify element {string} is not clickable")
    public void verifyElementIsNotClickable(String elementKey) {
        try {
            ElementHelper.setElement(elementKey);
            if (ElementHelper.getElement().isDisplayed() && ElementHelper.getElement().isEnabled() && !AssertionHelper.isElementDisabled()) {
                throw new AssertionError("Expected element '" + elementKey + "' to be not clickable, but it is.");
            }
            logger.info("Successfully verified that element '" + elementKey + "' is not clickable.");
        } catch (Exception e) {
            ExceptionHandler.handleException("Error verifying non-clickable state of element '" + elementKey + "'", e);
        }
    }

    @Step("Verify input field {elementKey} is empty")
    @Then("Verify input field {string} is empty")
    public void verifyInputFieldIsEmpty(String elementKey) {
        try {
            ElementHelper.setElement(elementKey);
            boolean isEmpty = AssertionHelper.isInputFieldEmpty();
            if (!isEmpty) {
                throw new AssertionError("Expected input field '" + elementKey + "' to be empty, but it is not.");
            }
            logger.info(String.format("Successfully verified that input field '%s' is empty.", elementKey));
        } catch (Exception e) {
            ExceptionHandler.handleException("Error verifying that input field '" + elementKey + "' is empty", e);
        }
    }

    @Step("Page title should be: {title}")
    @Then("Page title should be {string}")
    public void verifyPageTitle(String title) {
        try {
            AssertionHelper.verifyPageTitle(title);
            logger.info("Successfully verified page title: '" + title + "'.");
        } catch (Exception e) {
            ExceptionHandler.handleException("Error verifying page title", e);
        }
    }
}
