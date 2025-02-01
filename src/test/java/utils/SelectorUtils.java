package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class SelectorUtils {

    private static final Logger logger = Logger.getLogger(SelectorUtils.class);

    private static final String ELEMENTS_JSON_PATH = "src/test/resources/elements.json";

    private static JsonNode selectors;

    static {
        loadSelectors();
    }

    private static void loadSelectors() {
        try {
            logger.info("Loading selectors from JSON file: " + ELEMENTS_JSON_PATH);
            ObjectMapper mapper = new ObjectMapper();
            selectors = mapper.readTree(new File(ELEMENTS_JSON_PATH));
            if (selectors == null || selectors.isNull()) {
                throw new IllegalStateException("No data found in " + ELEMENTS_JSON_PATH);
            }
            logger.info("Selector JSON file loaded successfully.");
        } catch (IOException e) {
            logger.error("Error loading elements.json file from path: " + ELEMENTS_JSON_PATH, e);
            throw new RuntimeException("Failed to load selectors JSON file", e);
        }
    }

    public static By getSelector(String elementName) {
        if (selectors == null) {
            throw new IllegalStateException("Selectors JSON is not initialized. Check file path or file format.");
        }

        JsonNode elementNode = selectors.path(elementName);
        if (elementNode.isMissingNode() || elementNode.isNull()) {
            logger.error("Element '" + elementName + "' not found in JSON at path: " + ELEMENTS_JSON_PATH);
            throw new IllegalArgumentException("Element not found in JSON: " + elementName);
        }

        Iterator<String> fieldNames = elementNode.fieldNames();
        if (!fieldNames.hasNext()) {
            logger.error("No valid selector type found for element: '" + elementName + "'");
            throw new IllegalArgumentException("No selector type defined in JSON for element: " + elementName);
        }

        String selectorType = fieldNames.next();
        String selectorValue = elementNode.get(selectorType).asText();

        switch (selectorType.toLowerCase()) {
            case "cssselector":
                logger.debug("Using CSS Selector for element '" + elementName + "' -> " + selectorValue);
                return By.cssSelector(selectorValue);

            case "xpath":
                logger.debug("Using XPath for element '" + elementName + "' -> " + selectorValue);
                return By.xpath(selectorValue);

            case "id":
                logger.debug("Using ID for element '" + elementName + "' -> " + selectorValue);
                return By.id(selectorValue);

            case "name":
                logger.debug("Using Name for element '" + elementName + "' -> " + selectorValue);
                return By.name(selectorValue);

            default:
                logger.error("Unsupported selector type '" + selectorType + "' for element '" + elementName + "'");
                throw new IllegalArgumentException("Unsupported selector type: " + selectorType
                        + " for element: " + elementName);
        }
    }
}
