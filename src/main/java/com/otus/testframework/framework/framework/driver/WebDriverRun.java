package com.otus.testframework.framework.framework.driver;

import com.otus.testframework.framework.framework.config.TestConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

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
        ALL_DRIVERS_THREADS.forEach(WebDriverRun::quitDriver);
    }

    private static void quitDriver(Thread thread) {
        long threadId = thread.getId();
        ALL_DRIVERS_THREADS.remove(thread);
        WebDriver webdriver = DRIVERS_PER_THREAD.remove(threadId);

        if (webdriver != null) {
            try {
                webdriver.quit();
            } catch (WebDriverException e) {
                log.error("Failed to quit driver: " + e.getMessage());
            }
        }
    }

    private static void rememberDriver(WebDriver webDriver) {
        DRIVERS_PER_THREAD.put(currentThread().getId(), webDriver);
        ALL_DRIVERS_THREADS.add(currentThread());
    }
    public static List<String> friend(List<String> x){

        return x.stream().filter(str ->str.length() <=4).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        System.out.println(friend(List.of("Ryan","Kieran")));

    }
}

