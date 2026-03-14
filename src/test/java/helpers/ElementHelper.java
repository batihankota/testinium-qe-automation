package helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import settings.DriverSettings;
import utils.SelectorUtils;

import java.time.Duration;

public class ElementHelper {

    private static final ThreadLocal<WebElement> element = new ThreadLocal<>();

    public static void setElement(String elementKey) {
        By selector = SelectorUtils.getSelector(elementKey);
        if (selector == null) {
            throw new IllegalStateException("Selector not found for: " + elementKey);
        }
        element.set(waitAndFindElement(selector));
    }

    public static WebElement getElement() {
        WebElement el = element.get();
        if (el == null) {
            throw new IllegalStateException("Element is not set. Call setElement() first.");
        }
        return el;
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
