package com.otus.testframework.framework.api.config;

import com.otus.testframework.framework.api.services.PetStoreIpi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestAPIConfig {

    @Bean
    public PetStoreIpi petStoreApi() {
        return new PetStoreIpi();
    }
}
