package com.otus.testframework.framework.framework.config;

import com.otus.testframework.framework.enums.BrowserName;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

/**
 * Test configuration interface for accessing application settings defined in a properties file.
 * This interface uses the `Config` library to map configuration properties to Java methods.
 *
 * Annotations:
 * - @Config.Sources: Specifies the location of the properties file (`config.properties`) in the classpath.
 * - @Key: Maps a property key to a method.
 * - @DefaultValue: Provides a default value for a configuration property if it is not specified.
 *
 */


@Config.Sources("classpath:config.properties")
public interface TestConfig extends Config {
    TestConfig CONFIG = ConfigFactory.create(TestConfig.class);

    @Key("browser")
    @DefaultValue("CHROME")
    BrowserName browser();

    @Key("orgURL")
    default String orgURL() {
        return null;
    }

    @Key("urlAPI")
    String urlAPI();
}
