package org.example.gymcrmsystem.config;

import com.fasterxml.jackson.databind.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.text.SimpleDateFormat;
import java.util.Collections;

@Configuration
public class JacksonConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(JacksonConfig.class);

    @Bean
    public ObjectMapper objectMapper() {
        LOGGER.info("Configuring ObjectMapper with custom date format and registered modules.");
        return new ObjectMapper()
                .findAndRegisterModules()
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    }

    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter(ObjectMapper objectMapper) {
        LOGGER.info("Configuring RequestMappingHandlerAdapter with custom ObjectMapper.");
        RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();
        adapter.setMessageConverters(Collections.singletonList(new MappingJackson2HttpMessageConverter(objectMapper)));
        return adapter;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
        LOGGER.info("Creating MappingJackson2HttpMessageConverter with custom ObjectMapper.");
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }
}