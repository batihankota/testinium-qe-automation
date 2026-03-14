package helpers;
import settings.DriverSettings;
import utils.ExceptionHandler;
import java.util.regex.Pattern;
import static org.assertj.core.api.Assertions.assertThat;

public class AssertionHelper {

    public static void verifyElementIsDisplayed(String elementKey) {
        try {
            ElementHelper.setElement(elementKey);
            if (!ElementHelper.getElement().isDisplayed()) {
                throw new AssertionError("Expected element '" + elementKey + "' to be displayed, but it was not.");
            }
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to verify that element '" + elementKey + "' is displayed", e);
        }
    }

    public static void verifyTextMatch(String actualText, String expectedText, String elementKey) {
        try {
            if (!actualText.trim().equals(expectedText.trim())) {
                throw new AssertionError("Expected text of element '" + elementKey +
                        "' to be '" + expectedText + "', but found: '" + actualText + "'");
            }
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to verify text of element '" + elementKey + "'", e);
        }
    }

    public static void verifyTextMatchesPattern(String elementKey, String regexPattern) {
        try {
            ElementHelper.setElement(elementKey);
            verifyMatchesPattern(elementKey, ElementHelper.getElement().getText().trim(), regexPattern);
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to verify regex pattern for element '" + elementKey + "'", e);
        }
    }

    public static void verifyValueMatchesPattern(String elementKey, String regexPattern) {
        try {
            ElementHelper.setElement(elementKey);
            verifyMatchesPattern(elementKey, ElementHelper.getElement().getAttribute("value").trim(), regexPattern);
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to verify regex pattern for value of element '" + elementKey + "'", e);
        }
    }

    public static void verifyElementPresence(String elementKey) {
        try {
            ElementHelper.setElement(elementKey);
            assertThat(ElementHelper.getElement().isDisplayed()).isTrue();
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to verify presence of element '" + elementKey + "'", e);
        }
    }

    public static void verifyNumericValue(String elementKey) {
        try {
            ElementHelper.setElement(elementKey);
            String elementText = ElementHelper.getElement().getText().trim();
            double value = Double.parseDouble(elementText.replaceAll("[^0-9.]", ""));

            assertThat(value).isGreaterThan(0.0);
        } catch (NumberFormatException e) {
            ExceptionHandler.handleException("Invalid numeric value in element: " + elementKey, e);
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to verify numeric value in element '" + elementKey + "'", e);
        }
    }

    public static boolean isElementDisabled() {
        try {
            String ariaDisabled = ElementHelper.getElement().getAttribute("aria-disabled");
            return ariaDisabled != null && ariaDisabled.equalsIgnoreCase("true");
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to check disabled state of element", e);
            return false;
        }
    }

    public static boolean isInputFieldEmpty() {
        try {
            String inputValue = ElementHelper.getElement().getAttribute("value");
            return inputValue == null || inputValue.trim().isEmpty();
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to check if input field is empty", e);
            return false;
        }
    }

    public static void verifyPageTitle(String title) {
        try {
            String actualTitle = DriverSettings.getDriver().getTitle();
            assertThat(actualTitle).isEqualTo(title);
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to verify page title '" + title + "'", e);
        }
    }

    private static void verifyMatchesPattern(String elementKey, String actualText, String regexPattern) {
        if (!Pattern.compile(regexPattern).matcher(actualText).matches()) {
            throw new AssertionError("Expected text of element '" + elementKey +
                    "' to match regex pattern: " + regexPattern + ", but found: " + actualText);
        }
    }
}
