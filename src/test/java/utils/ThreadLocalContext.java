package utils;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalContext {
    private static ThreadLocal<Map<String, Object>> threadLocal = ThreadLocal.withInitial(HashMap::new);

    public static void set(String key, Object value) {
        Map<String, Object> context = threadLocal.get();
        context.put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> context = threadLocal.get();
        return context.get(key);
    }

    public static void remove() {
        threadLocal.remove();
    }
}
