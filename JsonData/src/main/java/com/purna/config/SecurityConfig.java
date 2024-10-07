package com.purna.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class SecurityConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public Function<String, byte[]> convertJsonToBytes() {
        return jsonString -> {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
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
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readTree(byteArray).toPrettyString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        };
    }
}
