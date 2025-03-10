package com.otus.testframework.framework.framework.driver;

import com.otus.testframework.framework.framework.config.TestConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.lang.Thread.currentThread;

/**
 * Manages the lifecycle of a WebDriver instance for automated tests.
 * This class ensures a singleton instance of WebDriver is created, configured, and properly closed.
 */
@Log4j
public class WebDriverRun {
    private static Map<Long, WebDriver> DRIVERS_PER_THREAD = new ConcurrentHashMap<>(4);
    private static Collection<Thread> ALL_DRIVERS_THREADS = new ConcurrentLinkedQueue<>();

    public static WebDriver getDriver() {
        long threadId = Thread.currentThread().getId();
        WebDriver driver = DRIVERS_PER_THREAD.get(threadId);
        if (driver == null) {
            driver = createDriver();
            DRIVERS_PER_THREAD.put(threadId, driver);
        }
        return driver;
    }

    private static WebDriver createDriver() {
        WebDriver createdDriver;
        switch (TestConfig.CONFIG.browser()) {
            case SAFARI:
                WebDriverManager.safaridriver().setup();
                createdDriver = new SafariDriver();
                log.info("SafariDriver has been launched.");
                break;
            default:
                WebDriverManager.chromedriver().setup();
                createdDriver = new ChromeDriver(new DriveOptionUtils().getChromeOptions());
                log.info("ChromeDriver has been launched.");
                break;
        }
        rememberDriver(createdDriver);
        return createdDriver;
    }

    public static void quitAllDrivers() {
        log.info("Attempting to quit all WebDriver instances...");

        if (DRIVERS_PER_THREAD.isEmpty()) {
            log.warn("No WebDriver instances found to quit.");
            return;
        }

        for (Map.Entry<Long, WebDriver> entry : DRIVERS_PER_THREAD.entrySet()) {
            WebDriver driver = entry.getValue();
            if (driver != null) {
                try {
                    log.info("Quitting WebDriver instance for thread ID: " + entry.getKey());
                    driver.quit();
                } catch (WebDriverException e) {
                    log.error("Failed to quit driver: " + e.getMessage());
                }
            }
        }
        DRIVERS_PER_THREAD.clear();
    }


    private static void quitDriver(Thread thread) {
        long threadId = thread.getId();
        WebDriver driver = DRIVERS_PER_THREAD.remove(threadId);

        if (driver != null) {
            try {
                log.info("Quitting WebDriver instance for thread ID: " + threadId);
                driver.quit();
            } catch (WebDriverException e) {
                log.error("Failed to quit driver: " + e.getMessage());
            }
        }
    }

    private static void rememberDriver(WebDriver webDriver) {
        DRIVERS_PER_THREAD.put(currentThread().getId(), webDriver);
        ALL_DRIVERS_THREADS.add(currentThread());
    }

}

