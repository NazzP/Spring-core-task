package org.example.gymcrmsystem.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

class JacksonConfigTest {

    @InjectMocks
    private JacksonConfig jacksonConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void objectMapperBeanShouldBeConfiguredCorrectly() {
        ObjectMapper mapper = jacksonConfig.objectMapper();
        assertNotNull(mapper, "ObjectMapper bean should not be null");
        assertEquals("yyyy-MM-dd", ((SimpleDateFormat) mapper.getDateFormat()).toPattern(),
                "ObjectMapper should have the correct date format");
    }

    @Test
    void requestMappingHandlerAdapterShouldBeConfiguredWithCustomObjectMapper() {
        ObjectMapper mapper = jacksonConfig.objectMapper();
        RequestMappingHandlerAdapter adapter = jacksonConfig.requestMappingHandlerAdapter(mapper);

        assertNotNull(adapter, "RequestMappingHandlerAdapter bean should not be null");
        assertEquals(1, adapter.getMessageConverters().size(),
                "RequestMappingHandlerAdapter should have exactly one message converter");
        assertTrue(adapter.getMessageConverters().get(0) instanceof MappingJackson2HttpMessageConverter,
                "Message converter should be of type MappingJackson2HttpMessageConverter");

        MappingJackson2HttpMessageConverter converter =
                (MappingJackson2HttpMessageConverter) adapter.getMessageConverters().get(0);
        assertEquals(mapper, converter.getObjectMapper(), "Message converter should use the custom ObjectMapper");
    }

    @Test
    void mappingJackson2HttpMessageConverterShouldBeCreatedCorrectly() {
        ObjectMapper mapper = jacksonConfig.objectMapper();
        MappingJackson2HttpMessageConverter converter = jacksonConfig.mappingJackson2HttpMessageConverter(mapper);

        assertNotNull(converter, "MappingJackson2HttpMessageConverter bean should not be null");
        assertEquals(mapper, converter.getObjectMapper(), "MappingJackson2HttpMessageConverter should use the custom ObjectMapper");
    }
}