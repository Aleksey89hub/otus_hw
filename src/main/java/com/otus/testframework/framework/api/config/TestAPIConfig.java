package com.otus.testframework.framework.api.config;

import com.otus.testframework.framework.api.services.PetStoreIpi;
import com.otus.testframework.framework.framework.pages.CoursesPage;
import com.otus.testframework.framework.framework.pages.MainPage;
import com.otus.testframework.framework.framework.pages.SingleCoursePage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.otus.testframework")
public class TestAPIConfig {

    @Bean
    public PetStoreIpi petStoreApi() {
        return new PetStoreIpi();
    }

    @Bean
    public CoursesPage coursesPage() {
        return new CoursesPage();
    }

    @Bean
    public SingleCoursePage singleCoursePage() {
        return new SingleCoursePage();
    }

    @Bean
    public MainPage mainPage() {
        return new MainPage();
    }

}
