package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class TestDataLoader {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, Object> loadTestData(String filePath) throws IOException {
        File file = new File(filePath);
        return objectMapper.readValue(file, Map.class);
    }
}
