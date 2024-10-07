package com.purna.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class SecurityConfig {

    @Bean
    public Function<String, byte[]> convertJsonToBytes() {
        return jsonString -> {
            try {
                // Create an ObjectMapper instance
                ObjectMapper objectMapper = new ObjectMapper();

                // Convert JSON string to byte array
                return objectMapper.writeValueAsBytes(objectMapper.readTree(jsonString));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        };
    }

    @Bean
    public Function<byte[], String> byteToJson() {
        return byteArray -> {
            try {
                // Create an ObjectMapper instance
                ObjectMapper objectMapper = new ObjectMapper();

                // Convert byte array back to JSON string
                return objectMapper.readTree(byteArray).toPrettyString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        };
    }
}
