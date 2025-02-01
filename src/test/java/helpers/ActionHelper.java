package helpers;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import settings.DriverSettings;
import utils.ExceptionHandler;

import static org.assertj.core.api.Assertions.assertThat;

public class ActionHelper {

    public static void clickElement(String elementKey) {
        try {
            ElementHelper.setElement(elementKey);
            ElementHelper.getElement().click();
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to click on element '" + elementKey + "'", e);
        }
    }

    public static void clickWithJavaScript(String elementKey) {
        try {
            ElementHelper.setElement(elementKey);
            JavascriptExecutor js = (JavascriptExecutor) DriverSettings.getDriver();
            js.executeScript("arguments[0].click();", ElementHelper.getElement());
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to click on element '" + elementKey + "' using JavaScript", e);
        }
    }

    public static void enterText(String elementKey, String text) {
        try {
            ElementHelper.setElement(elementKey);
            ElementHelper.getElement().sendKeys(text);
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to enter text into element '" + elementKey + "'", e);
        }
    }

    public static void clearAndEnterText(String elementKey, String text) {
        try {
            ElementHelper.setElement(elementKey);
            ElementHelper.getElement().clear();
            ElementHelper.getElement().sendKeys(text);
            assertThat(ElementHelper.getElement().getAttribute("value")).isEqualTo(text);
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to clear and enter text into element '" + elementKey + "'", e);
        }
    }

    public static void clearText(String elementKey) {
        try {
            ElementHelper.setElement(elementKey);
            ElementHelper.getElement().clear();
            assertThat(ElementHelper.getElement().getAttribute("value")).isEmpty();
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to clear text in element '" + elementKey + "'", e);
        }
    }

    public static void clickAndClearText(String elementKey) {
        try {
            ElementHelper.setElement(elementKey);
            ElementHelper.getElement().click();
            ElementHelper.getElement().clear();

            while (!ElementHelper.getElement().getAttribute("value").isEmpty()) {
                ElementHelper.getElement().sendKeys(Keys.BACK_SPACE);
            }
            assertThat(ElementHelper.getElement().getAttribute("value")).isEmpty();
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to click and clear text in element '" + elementKey + "'", e);
        }
    }

    public static void selectValue(String elementKey, String value) {
        try {
            ElementHelper.setElement(elementKey);
            Select select = new Select(ElementHelper.getElement());
            select.selectByVisibleText(value);
            assertThat(select.getFirstSelectedOption().getText().trim()).isEqualTo(value);
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to select value '" + value + "' in element '" + elementKey + "'", e);
        }
    }

    public static void clickAndSendBackspaceUntilEmpty(String elementKey) {
        try {
            ElementHelper.setElement(elementKey);
            ElementHelper.getElement().click();
            Thread.sleep(200);

            String value = ElementHelper.getElement().getAttribute("value");

            while (value != null && !value.isEmpty()) {
                ElementHelper.getElement().sendKeys(Keys.BACK_SPACE);
                Thread.sleep(50);
                value = ElementHelper.getElement().getAttribute("value");
            }

            assertThat(ElementHelper.getElement().getAttribute("value")).isEmpty();
        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to click and send backspace in element '" + elementKey + "'", e);
        }
    }
}
