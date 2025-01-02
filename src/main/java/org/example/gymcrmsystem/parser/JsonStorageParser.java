package org.example.gymcrmsystem.parser;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JsonStorageParser<ID, T extends Identifiable<ID>> {

    private final ObjectMapper objectMapper;

    public JsonStorageParser() {
        this.objectMapper = new ObjectMapper();
    }

    public Map<ID, T> parseJsonToMap(String filePath, Class<T> type) {
        Map<ID, T> storageMap = new HashMap<>();
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, type);
            List<T> entities = objectMapper.readValue(new File(filePath), javaType);

            for (T entity : entities) {
                ID id = entity.getId();
                if (id == null) {
                    throw new IllegalArgumentException("Entity ID cannot be null for entity: " + entity);
                }
                storageMap.put(id, entity);
            }
        } catch (IOException e) {
            System.err.println("Error reading or parsing JSON file: " + filePath);
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error occurred while parsing JSON file: " + filePath);
            e.printStackTrace();
        }
        return storageMap;
    }
}