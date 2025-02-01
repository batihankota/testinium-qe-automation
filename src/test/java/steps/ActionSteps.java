package steps;

import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import helpers.ActionHelper;
import utils.ExceptionHandler;

public class ActionSteps {

    private static final Logger logger = Logger.getLogger(ActionSteps.class);

    @Step("Click on element: {elementKey}")
    @When("Click on element {string}")
    public void clickElement(String elementKey) {
        try {
            ActionHelper.clickElement(elementKey);
            logger.info("Successfully clicked on element: " + elementKey);
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to click on element '" + elementKey + "'", e);
        }
    }

    @Step("Click on element with JavaScript: {elementKey}")
    @When("Click on element with JavaScript {string}")
    public void clickWithJavaScript(String elementKey) {
        try {
            ActionHelper.clickWithJavaScript(elementKey);
            logger.info("Successfully clicked on element '" + elementKey + "' using JavaScript.");
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to click on element '" + elementKey + "' using JavaScript.", e);
        }
    }

    @Step("Enter text: {text} into element: {elementKey}")
    @When("Enter text {string} into element {string}")
    public void enterText(String text, String elementKey) {
        try {
            ActionHelper.enterText(elementKey, text);
            logger.info("Entered text '" + text + "' into element: " + elementKey);
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to enter text '" + text + "' into element '" + elementKey + "'", e);
        }
    }

    @Step("Clear element: {elementKey} and type text: {text}")
    @When("Clear element {string} and type text {string}")
    public void clearElementAndTypeText(String elementKey, String text) {
        try {
            ActionHelper.clearAndEnterText(elementKey, text);
            logger.info("Cleared and entered text '" + text + "' into element: " + elementKey);
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to clear and enter text in element '" + elementKey + "'", e);
        }
    }

    @Step("Clear text in element: {elementKey}")
    @When("Clear text in element {string}")
    public void clearTextInElement(String elementKey) {
        try {
            ActionHelper.clearText(elementKey);
            logger.info("Cleared text in element: " + elementKey);
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to clear text in element '" + elementKey + "'", e);
        }
    }

    @Step("Click and clear text in element: {elementKey}")
    @When("Click and clear text in element {string}")
    public void clickAndClearTextInElement(String elementKey) {
        try {
            ActionHelper.clickAndClearText(elementKey);
            logger.info("Clicked and cleared text in element: " + elementKey);
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to click and clear text in element '" + elementKey + "'", e);
        }
    }

    @Step("Select value: {value} in element: {elementKey}")
    @When("Select value {string} in element {string}")
    public void selectValueInElement(String value, String elementKey) {
        try {
            ActionHelper.selectValue(elementKey, value);
            logger.info("Selected value '" + value + "' in element: " + elementKey);
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to select value '" + value + "' in element '" + elementKey + "'", e);
        }
    }

    @Step("Click and send backspace until element {elementKey} is empty")
    @When("Click and send backspace until element {string} is empty")
    public void clickAndSendBackspaceUntilEmpty(String elementKey) {
        try {
            ActionHelper.clickAndSendBackspaceUntilEmpty(elementKey);
            logger.info("Clicked and sent backspace until element '" + elementKey + "' was empty.");
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to click and send backspace in element '" + elementKey + "'", e);
        }
    }


}
