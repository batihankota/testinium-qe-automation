package context;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {

    private static final ThreadLocal<Map<String, Object>> scenarioData = ThreadLocal.withInitial(HashMap::new);

    public static void put(String key, Object value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key or Value cannot be null. Key: " + key);
        }
        scenarioData.get().put(key, value);
    }

    public static String getString(String key) {
        Object value = scenarioData.get().get(key);
        return (value instanceof String) ? (String) value : null;
    }

    public static BigDecimal getNumeric(String key) {
        Object value = scenarioData.get().get(key);
        if (value instanceof Number) {
            return BigDecimal.valueOf(((Number) value).doubleValue());
        }
        return null;
    }

    public static boolean containsKey(String key) {
        return scenarioData.get().containsKey(key);
    }
}
