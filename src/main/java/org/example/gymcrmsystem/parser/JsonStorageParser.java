package org.example.gymcrmsystem.parser;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class JsonStorageParser<K, T extends Identifiable<K>> {

    private final ObjectMapper objectMapper;

    @Autowired
    public JsonStorageParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Map<K, T> parseJsonToMap(String filePath, Class<T> type) {
        Map<K, T> storageMap = new HashMap<>();
        try {
            log.info("Starting JSON parsing for file: {}", filePath);

            JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, type);
            List<T> entities = objectMapper.readValue(new File(filePath), javaType);

            log.debug("Successfully read {} entities from file: {}", entities.size(), filePath);

            for (T entity : entities) {
                K id = entity.getId();
                if (id == null) {
                    log.warn("Entity with null ID encountered: {}", entity);
                    throw new IllegalArgumentException("Entity ID cannot be null for entity: " + entity);
                }
                storageMap.put(id, entity);
            }

            log.info("Successfully parsed JSON into {} entities.", storageMap.size());
        } catch (IOException e) {
            log.warn("Error reading or parsing JSON file: {}", filePath, e);
        } catch (IllegalArgumentException e) {
            log.warn("Illegal argument: {}", e.getMessage(), e);
        } catch (Exception e) {
            log.warn("Unexpected error occurred while parsing JSON file: {}", filePath, e);
        }
        return storageMap;
    }
}
