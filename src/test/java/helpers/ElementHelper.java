package helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import settings.DriverSettings;
import utils.SelectorUtils;

import java.time.Duration;

public class ElementHelper {

    private static By selector;
    private static WebElement element;

    public static void setElement(String elementKey) {
        selector = SelectorUtils.getSelector(elementKey);
        if (selector == null) {
            throw new IllegalStateException("Selector not found for: " + elementKey);
        }
        element = waitAndFindElement(selector);
    }

    public static WebElement getElement() {
        if (element == null) {
            throw new IllegalStateException("Element is not set. Call setElement() first.");
        }
        return element;
    }

    private static WebElement waitAndFindElement(By selector) {
        WebDriverWait wait = new WebDriverWait(DriverSettings.getDriver(), Duration.ofSeconds(30));
        return wait.until(ExpectedConditions.presenceOfElementLocated(selector));
    }

    public static WebElement waitAndFindElementClickable(By selector) {
        WebDriverWait wait = new WebDriverWait(DriverSettings.getDriver(), Duration.ofSeconds(30));
        return wait.until(ExpectedConditions.elementToBeClickable(selector));
    }
}
