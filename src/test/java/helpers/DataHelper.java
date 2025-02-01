package helpers;

import context.ScenarioContext;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import steps.DataSteps;
import utils.ExceptionHandler;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class DataHelper {

    private static final Logger logger = Logger.getLogger(DataSteps.class);

    public static void storeTrimmedText(String elementKey, String variableName) {
        try {
            ElementHelper.setElement(elementKey);
            String text = ElementHelper.getElement().getText().trim();

            if (text.contains(".")) {
                text = text.replaceAll("\\.0+$", "").replaceAll("(\\.\\d*?)0+$", "$1");
            }

            ScenarioContext.put(variableName, text);
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to store trimmed text from element '" + elementKey + "'", e);
        }
    }

    public static void verifySubtraction(String variableOne, String variableTwo, String variableThree) {
        try {
            BigDecimal valueOne = ScenarioContext.getNumeric(variableOne);
            BigDecimal valueTwo = ScenarioContext.getNumeric(variableTwo);
            BigDecimal expectedValue = ScenarioContext.getNumeric(variableThree);

            if (valueOne == null || valueTwo == null || expectedValue == null) {
                throw new IllegalStateException("One or more variables are not set: " + variableOne + ", " + variableTwo + ", " + variableThree);
            }

            BigDecimal actualResult = valueOne.subtract(valueTwo);

            assertThat(actualResult.compareTo(expectedValue))
                    .withFailMessage("Expected: %s but found: %s when subtracting '%s' from '%s'",
                            expectedValue, actualResult, valueTwo, valueOne)
                    .isEqualTo(0);
        } catch (Exception e) {
            ExceptionHandler.handleException(String.format("Failed to verify that subtraction of '%s' minus '%s' equals '%s'",
                    variableOne, variableTwo, variableThree), e);
        }
    }

    public static void verifyAddition(String variableOne, String variableTwo, String variableThree) {
        try {
            BigDecimal valueOne = ScenarioContext.getNumeric(variableOne);
            BigDecimal valueTwo = ScenarioContext.getNumeric(variableTwo);
            BigDecimal expectedValue = ScenarioContext.getNumeric(variableThree);

            if (valueOne == null || valueTwo == null || expectedValue == null) {
                throw new IllegalStateException("One or more variables are not set: " + variableOne + ", " + variableTwo + ", " + variableThree);
            }

            BigDecimal actualResult = valueOne.add(valueTwo);

            assertThat(actualResult.compareTo(expectedValue))
                    .withFailMessage("Expected: %s but found: %s when adding '%s' to '%s'",
                            expectedValue, actualResult, valueTwo, valueOne)
                    .isEqualTo(0);
        } catch (Exception e) {
            ExceptionHandler.handleException(String.format("Failed to verify that addition of '%s' plus '%s' equals '%s'",
                    variableOne, variableTwo, variableThree), e);
        }
    }

    public static void storeText(String variableName, String text) {
        try {
            if (text == null || text.trim().isEmpty()) {
                throw new IllegalStateException("Cannot store empty text in variable: " + variableName);
            }
            ScenarioContext.put(variableName, text.trim());
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to store text into variable '" + variableName + "'", e);
        }
    }

    public static void storeValue(String variableName, String value) {
        try {
            if (value == null) {
                value = "";
            }
            ScenarioContext.put(variableName, value.trim());
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to store value into variable '" + variableName + "'", e);
        }
    }

    public static void storeNumericValue(String variableName, WebElement element) {
        try {
            String textValue = element.getAttribute("value");

            if (textValue == null || textValue.trim().isEmpty()) {
                textValue = element.getText().trim();
            }

            if (textValue.trim().isEmpty()) {
                throw new IllegalStateException("Element has no numeric value to store.");
            }

            BigDecimal numericValue = new BigDecimal(textValue.replaceAll("[^\\d.]", ""));
            ScenarioContext.put(variableName, numericValue);
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to store numeric value into variable '" + variableName + "'", e);
        }
    }


    public static void compareTextWithElement(String variableName, WebElement element) {
        try {
            String storedValue = ScenarioContext.getString(variableName);
            if (storedValue == null) {
                throw new IllegalStateException("No value found in ScenarioContext for variable: " + variableName);
            }

            String actualText = element.getText().trim();
            assertThat(actualText).isEqualTo(storedValue);
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to compare text of variable '" + variableName + "' with element", e);
        }
    }

    public static void compareSumWithElement(String variableOne, String variableTwo, WebElement element) {
        try {
            BigDecimal valueOne = ScenarioContext.getNumeric(variableOne);
            BigDecimal valueTwo = ScenarioContext.getNumeric(variableTwo);

            if (valueOne == null || valueTwo == null) {
                throw new IllegalStateException("One or more variables are not set: " + variableOne + ", " + variableTwo);
            }

            BigDecimal expectedSum = valueOne.add(valueTwo);
            BigDecimal actualValue = new BigDecimal(element.getText().replaceAll("[^\\d.]", ""));

            assertThat(actualValue.compareTo(expectedSum))
                    .withFailMessage("Expected: %s but found: %s when summing '%s' and '%s'",
                            expectedSum, actualValue, valueOne, valueTwo)
                    .isEqualTo(0);
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to compare sum with element", e);
        }
    }

    public static void compareDifferenceWithElement(String variableOne, String variableTwo, WebElement element) {
        try {
            BigDecimal valueOne = ScenarioContext.getNumeric(variableOne);
            BigDecimal valueTwo = ScenarioContext.getNumeric(variableTwo);

            if (valueOne == null || valueTwo == null) {
                throw new IllegalStateException("One or more variables are not set: " + variableOne + ", " + variableTwo);
            }

            BigDecimal expectedDifference = valueOne.subtract(valueTwo);
            BigDecimal actualValue = new BigDecimal(element.getText().replaceAll("[^\\d.]", ""));

            assertThat(actualValue.compareTo(expectedDifference))
                    .withFailMessage("Expected: %s but found: %s when subtracting '%s' from '%s'",
                            expectedDifference, actualValue, valueTwo, valueOne)
                    .isEqualTo(0);
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to compare difference with element", e);
        }
    }


    public static void compareNumericDifference(String variableName1, String variableName2, int expectedDifference) {
        try {
            BigDecimal value1 = ScenarioContext.getNumeric(variableName1);
            BigDecimal value2 = ScenarioContext.getNumeric(variableName2);

            if (value1 == null || value2 == null) {
                throw new IllegalStateException("One of the variables is not set: " + variableName1 + ", " + variableName2);
            }

            BigDecimal actualDifference = value2.subtract(value1);

            assertThat(actualDifference.intValueExact())
                    .withFailMessage("Expected difference: %s but found: %s between '%s' and '%s'",
                            expectedDifference, actualDifference, value1, value2)
                    .isEqualTo(expectedDifference);
        } catch (Exception e) {
            ExceptionHandler.handleException("Error comparing numeric difference", e);
        }
    }

    public static void printVariable(String variableName) {
        try {
            if (ScenarioContext.containsKey(variableName)) {
                BigDecimal numericValue = ScenarioContext.getNumeric(variableName);
                if (numericValue != null) {
                    logger.info("Variable '" + variableName + "' has numeric value: " + numericValue);
                    return;
                }

                String stringValue = ScenarioContext.getString(variableName);
                if (stringValue != null) {
                    logger.info("Variable '" + variableName + "' has string value: " + stringValue);
                    return;
                }

                logger.warn("Variable '" + variableName + "' exists but is of an unsupported type.");
            } else {
                logger.warn("Variable '" + variableName + "' is not set or is null.");
            }
        } catch (Exception e) {
            ExceptionHandler.handleException("Error retrieving value of variable '" + variableName + "'", e);
        }
    }

}
