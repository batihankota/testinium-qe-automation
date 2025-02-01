package settings;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import utils.ExceptionHandler;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DriverSettings {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final Logger logger = Logger.getLogger(DriverSettings.class);

    public static WebDriver getDriver() {
        return Optional.ofNullable(driver.get())
                .orElseThrow(() -> new IllegalStateException("WebDriver is not initialized. Call setDriver() first."));
    }

    public static void setDriver(String browserName, boolean isMobile) {
        try {
            logger.info("Initializing WebDriver for browser: " + browserName + " | Mobile mode: " + isMobile);

            WebDriver webDriver = switch (browserName.toLowerCase()) {
                case "chrome" -> setupChromeDriver(isMobile);
                case "firefox" -> setupFirefoxDriver();
                case "edge" -> setupEdgeDriver();
                default -> {
                    logger.warn("Unsupported browser: " + browserName + ". Defaulting to Chrome.");
                    yield setupChromeDriver(false);
                }
            };

            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            driver.set(webDriver);

            logger.info("WebDriver initialized successfully for browser: " + browserName);

        } catch (Exception e) {
            ExceptionHandler.handleException("Failed to initialize WebDriver for browser: " + browserName, e);
        }
    }

    private static WebDriver setupChromeDriver(boolean isMobile) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();

        if (isMobile) {
            Map<String, Object> mobileEmulation = new HashMap<>();
            mobileEmulation.put("deviceName", "iPhone X");
            chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
            chromeOptions.addArguments("--disable-notifications", "--incognito");
            logger.info("Chrome initialized in mobile emulation (device: iPhone X)");
        } else {
            chromeOptions.addArguments("--start-maximized", "--disable-notifications", "--incognito");
        }

        return new ChromeDriver(chromeOptions);
    }

    private static WebDriver setupFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addPreference("dom.webnotifications.enabled", false);
        logger.info("Firefox initialized with notifications disabled.");
        return new FirefoxDriver(firefoxOptions);
    }

    private static WebDriver setupEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        logger.info("Edge browser initialized.");
        return new EdgeDriver();
    }

    public static void quitDriver() {
        Optional.ofNullable(driver.get()).ifPresent(webDriver -> {
            try {
                logger.info("Quitting WebDriver instance.");
                webDriver.quit();
                driver.remove();
                logger.info("WebDriver instance quit successfully.");
            } catch (Exception e) {
                ExceptionHandler.handleException("Error while quitting WebDriver instance.", e);
            }
        });
    }
}
