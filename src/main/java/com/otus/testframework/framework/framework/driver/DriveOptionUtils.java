package com.otus.testframework.framework.framework.driver;

import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Utility class for configuring browser-specific options for WebDriver.
 * This class provides methods to retrieve pre-configured browser options for use in automated tests.
 */


public class DriveOptionUtils {

    protected static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        return options;
    }
}
