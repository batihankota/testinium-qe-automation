package utils;

import org.apache.log4j.Logger;

public class ExceptionHandler {

    private static final Logger logger = Logger.getLogger(ExceptionHandler.class);

    public static void handleException(String message, Exception e) {
        logger.error(message + ": " + e.getMessage(), e);
        throw new RuntimeException(message, e);
    }
}
