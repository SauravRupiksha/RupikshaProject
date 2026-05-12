package com.rupiksha.fingpayaeps.faeps.config;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {

        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, false);

        // ❌ REMOVE THIS (IMPORTANT)
        // mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

        // ✅ KEEP DEFAULT ORDER
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, false);

        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        mapper.findAndRegisterModules();

        return mapper;
    }
}