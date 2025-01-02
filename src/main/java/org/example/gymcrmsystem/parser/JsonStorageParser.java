package org.example.gymcrmsystem.parser;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JsonStorageParser<K, T extends Identifiable<K>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonStorageParser.class);

    private final ObjectMapper objectMapper;

    public JsonStorageParser() {
        this.objectMapper = new ObjectMapper();
    }

    public Map<K, T> parseJsonToMap(String filePath, Class<T> type) {
        Map<K, T> storageMap = new HashMap<>();
        try {
            LOGGER.info("Starting JSON parsing for file: {}", filePath);

            JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, type);
            List<T> entities = objectMapper.readValue(new File(filePath), javaType);

            LOGGER.debug("Successfully read {} entities from file: {}", entities.size(), filePath);

            for (T entity : entities) {
                K id = entity.getId();
                if (id == null) {
                    LOGGER.warn("Entity with null ID encountered: {}", entity);
                    throw new IllegalArgumentException("Entity ID cannot be null for entity: " + entity);
                }
                storageMap.put(id, entity);
            }

            LOGGER.info("Successfully parsed JSON into {} entities.", storageMap.size());
        } catch (IOException e) {
            LOGGER.warn("Error reading or parsing JSON file: {}", filePath, e);
        } catch (IllegalArgumentException e) {
            LOGGER.warn("Illegal argument: {}", e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.warn("Unexpected error occurred while parsing JSON file: {}", filePath, e);
        }
        return storageMap;
    }
}
