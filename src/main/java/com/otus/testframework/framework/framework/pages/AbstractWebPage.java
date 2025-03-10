package com.otus.testframework.framework.framework.pages;

import com.otus.testframework.framework.framework.config.TestConfig;
import com.otus.testframework.framework.framework.driver.WebDriverRun;
import com.otus.testframework.framework.framework.utils.AdditionalConditions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.WebDriverListener;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.function.Supplier;

/**
 * Abstract base class for web pages in the framework.
 * Provides common functionality for interacting with web elements and managing WebDriver actions.
 */

public abstract class AbstractWebPage implements WebDriverListener {

    private static final long DEFAULT_TIMEOUT_TO_WAIT = 40;
    private static final int SHORT_TIMEOUT_TO_WAIT = 10;
    private static final String BASE_URL = TestConfig.CONFIG.orgURL();
    private final WebDriver driver;

    protected AbstractWebPage() {
        this.driver = WebDriverRun.getDriver();
        PageFactory.initElements(driver, this);
    }

    public Actions getActions() {
        return new Actions((driver));
    }

    protected WebDriverWait getWebDriverWait(long timeoutInSeconds) {
        return (WebDriverWait) new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT_TO_WAIT))
                .pollingEvery(Duration.ofMillis(500))
                .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
    }

    public void navigateTo(String relativePath) {
        String fullPath = BASE_URL + relativePath;
        driver.get(fullPath);
    }

    protected boolean shortWaitUntil(Supplier<Boolean> condition) {
        return getWebDriverWait(SHORT_TIMEOUT_TO_WAIT).until(AdditionalConditions.isTrue(condition));
    }

    public void waitForElementToBeClickable(WebElement element) {
        getWebDriverWait(SHORT_TIMEOUT_TO_WAIT).until(ExpectedConditions.elementToBeClickable(element));
    }

    public void doubleClick(WebElement element) {
        getActions().doubleClick(element).perform();
    }

    public void waitForVisibilityOfElements(List<WebElement> elements) {
        try {
            getWebDriverWait(SHORT_TIMEOUT_TO_WAIT)
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.visibilityOfAllElements(elements));
        } catch (TimeoutException e) {
            // nothing to do here
        }
    }

    public void clickWithHighlight(WebElement element) {
        highlightElement(element);
        element.click();
    }

    private void highlightElement(WebElement element) {
        if (driver instanceof JavascriptExecutor) {
            JavascriptExecutor js = (JavascriptExecutor) driver;

            String script =
                    "var element = arguments[0];" +
                            "element.style.boxShadow = '0 0 0 16px blue, 0 0 0 24px yellow';" +
                            "element.style.border = 'none';" +
                            "setTimeout(function() { element.style.boxShadow = ''; }, 3000);";

            js.executeScript(script, element);
        }
    }

    @Override
    public void beforeClick(WebElement element) {
        highlightElement(element);
    }

    @Override
    public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
        highlightElement(element);
    }
}
