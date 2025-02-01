package steps;

import context.ScenarioContext;
import helpers.DataHelper;
import helpers.ElementHelper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import utils.ExceptionHandler;

public class DataSteps {

    private static final Logger logger = Logger.getLogger(DataSteps.class);

    @Step("Store text from element: {elementKey} into variable: {variableName}")
    @When("Store text from element {string} into variable {string}")
    public void storeTextFromElementIntoVariable(String elementKey, String variableName) {
        try {
            ElementHelper.setElement(elementKey);
            DataHelper.storeText(variableName, ElementHelper.getElement().getText());
            logger.info("Stored text from element '" + elementKey + "' into variable '" + variableName + "'.");
        } catch (Exception e) {
            ExceptionHandler.handleException("Error storing text from element '" + elementKey + "' into variable '" + variableName + "'", e);
        }
    }

    @Step("Store trimmed text from element: {elementKey} into variable: {variableName}")
    @When("Store trimmed text from element {string} into variable {string}")
    public void storeTrimmedTextFromElementIntoVariable(String elementKey, String variableName) {
        try {
            ElementHelper.setElement(elementKey);
            DataHelper.storeTrimmedText(variableName, ElementHelper.getElement().getText());
            logger.info("Stored trimmed text from element '" + elementKey + "' into variable '" + variableName + "'.");
        } catch (Exception e) {
            ExceptionHandler.handleException("Error storing trimmed text from element '" + elementKey + "' into variable '" + variableName + "'", e);
        }
    }

    @Step("Store value from element: {elementKey} into variable: {variableName}")
    @When("Store value from element {string} into variable {string}")
    public void storeValueFromElementIntoVariable(String elementKey, String variableName) {
        try {
            ElementHelper.setElement(elementKey);
            DataHelper.storeValue(variableName, ElementHelper.getElement().getAttribute("value"));
            logger.info("Stored value from element '" + elementKey + "' into variable '" + variableName + "'.");
        } catch (Exception e) {
            ExceptionHandler.handleException("Error storing value from element '" + elementKey + "' into variable '" + variableName + "'", e);
        }
    }

    @Step("Store numeric value from element: {elementKey} into variable: {variableName}")
    @When("Store numeric value from element {string} into variable {string}")
    public void storeNumericValueFromElementIntoVariable(String elementKey, String variableName) {
        try {
            ElementHelper.setElement(elementKey);
            DataHelper.storeNumericValue(variableName, ElementHelper.getElement());
            logger.info("Stored numeric value from element '" + elementKey + "' into variable '" + variableName + "'.");
        } catch (Exception e) {
            ExceptionHandler.handleException("Error storing numeric value from element '" + elementKey + "' into variable '" + variableName + "'", e);
        }
    }

    @Step("Compare text in variable: {variableName} with text in element: {elementKey}")
    @Then("Compare text in variable {string} with text in element {string}")
    public void compareTextInVariableWithElement(String variableName, String elementKey) {
        try {
            ElementHelper.setElement(elementKey);
            DataHelper.compareTextWithElement(variableName, ElementHelper.getElement());
            logger.info("Compared text in variable '" + variableName + "' with text in element '" + elementKey + "'.");
        } catch (Exception e) {
            ExceptionHandler.handleException("Error comparing stored text in variable '" + variableName + "' with element '" + elementKey + "'", e);
        }
    }

    @Step("Compare text in variable: {variableName} with expected text: {expectedText}")
    @Then("Compare text in variable {string} with {string}")
    public void compareTextInVariableWithString(String variableName, String expectedText) {
        try {
            DataHelper.storeText(variableName, expectedText);
            logger.info("Compared text in variable '" + variableName + "' with expected text '" + expectedText + "'.");
        } catch (Exception e) {
            ExceptionHandler.handleException("Error comparing stored text in variable '" + variableName + "' with expected text '" + expectedText + "'", e);
        }
    }

    @Step("Compare numeric difference between stored variable: {variableName1} and {variableName2} equals {difference}")
    @Then("Compare numeric difference between {string} and {string} is {int}")
    public void compareNumericDifference(String variableName1, String variableName2, int difference) {
        try {
            DataHelper.compareNumericDifference(variableName1, variableName2, difference);
            logger.info("Compared numeric difference between variables '" + variableName1 + "' and '" + variableName2 + "' with expected difference '" + difference + "'.");
        } catch (Exception e) {
            ExceptionHandler.handleException("Error comparing numeric difference between variables '" + variableName1 + "' and '" + variableName2 + "'", e);
        }
    }

    @Step("Verify that sum of variable {variableOne} minus {variableTwo} equals {variableThree}")
    @Then("Verify that sum of variable {string} minus {string} equals {string}")
    public void verifySumOfVariableMinusAnother(String variableOne, String variableTwo, String variableThree) {
        try {
            DataHelper.verifySubtraction(variableOne, variableTwo, variableThree);
            logger.info("Verified that sum of '" + variableOne + "' minus '" + variableTwo + "' equals '" + variableThree + "'.");
        } catch (Exception e) {
            ExceptionHandler.handleException("Error verifying subtraction of variables '" + variableOne + "', '" + variableTwo + "', '" + variableThree + "'", e);
        }
    }

    @Step("Verify that sum of variable {variableOne} plus {variableTwo} equals {variableThree}")
    @Then("Verify that sum of variable {string} plus {string} equals {string}")
    public void verifySumOfVariablePlusAnother(String variableOne, String variableTwo, String variableThree) {
        try {
            DataHelper.verifyAddition(variableOne, variableTwo, variableThree);
            logger.info("Verified that sum of '" + variableOne + "' plus '" + variableTwo + "' equals '" + variableThree + "'.");
        } catch (Exception e) {
            ExceptionHandler.handleException("Error verifying sum of variables '" + variableOne + "', '" + variableTwo + "', '" + variableThree + "'", e);
        }
    }

    @Step("Compare sum of variables: {variableOne} and {variableTwo} with element: {elementKey}")
    @And("Compare sum of variables {string} and {string} with element {string}")
    public void compareSumOfVariablesWithElement(String variableOne, String variableTwo, String elementKey) {
        try {
            ElementHelper.setElement(elementKey);
            DataHelper.compareSumWithElement(variableOne, variableTwo, ElementHelper.getElement());
            logger.info("Compared sum of variables '" + variableOne + "' and '" + variableTwo + "' with value in element '" + elementKey + "'.");
        } catch (Exception e) {
            ExceptionHandler.handleException("Error comparing sum of variables '" + variableOne + "' and '" + variableTwo + "' with element '" + elementKey + "'", e);
        }
    }

    @Step("Compare difference of variables: {variableOne} and {variableTwo} with element: {elementKey}")
    @And("Compare difference of variables {string} and {string} with element {string}")
    public void compareDiffOfVariablesWithElement(String variableOne, String variableTwo, String elementKey) {
        try {
            ElementHelper.setElement(elementKey);
            DataHelper.compareDifferenceWithElement(variableOne, variableTwo, ElementHelper.getElement());
            logger.info("Compared difference of variables '" + variableOne + "' and '" + variableTwo + "' with value in element '" + elementKey + "'.");
        } catch (Exception e) {
            ExceptionHandler.handleException("Error comparing difference of variables '" + variableOne + "' and '" + variableTwo + "' with element '" + elementKey + "'", e);
        }
    }

    @Step("Print value of variable {variableName}")
    @When("Print value of variable {string}")
    public void printValueOfVariable(String variableName) {
        try {
            DataHelper.printVariable(variableName);
            logger.info("Printed value of variable '" + variableName + "'.");
        } catch (Exception e) {
            ExceptionHandler.handleException("Error printing value of variable '" + variableName + "'", e);
        }
    }

    @Step("Enter text in element: {elementKey} from variable: {variableName}")
    @When("Enter text in element {string} from variable {string}")
    public void enterTextInElementFromVariable(String elementKey, String variableName) {
        try {
            ElementHelper.setElement(elementKey);
            String text = ScenarioContext.getString(variableName);
            ElementHelper.getElement().sendKeys(text);
            logger.info("Entered text from variable '" + variableName + "' into element '" + elementKey + "'.");
        } catch (Exception e) {
            ExceptionHandler.handleException("Error entering text from variable '" + variableName + "' into element '" + elementKey + "'", e);
        }
    }

    @Step("Verify text of element {elementKey} should match variable {variableName}")
    @Then("Verify text of element {string} should match variable {string}")
    public void verifyTextOfElementShouldMatchVariable(String elementKey, String variableName) {
        try {
            ElementHelper.setElement(elementKey);
            DataHelper.compareTextWithElement(variableName, ElementHelper.getElement());
            logger.info("Verified text of element '" + elementKey + "' matches variable '" + variableName + "'.");
        } catch (Exception e) {
            ExceptionHandler.handleException("Error verifying text match between element '" + elementKey + "' and variable '" + variableName + "'", e);
        }
    }
}
