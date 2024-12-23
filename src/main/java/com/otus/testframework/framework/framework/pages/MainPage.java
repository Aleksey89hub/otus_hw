package com.otus.testframework.framework.framework.pages;

import com.otus.testframework.framework.framework.exceptions.NoCourseOptionsAvailableException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Random;

/**
 * Represents the Courses page, providing methods to interact with its elements and extract course information.
 *
 */

public class MainPage extends AbstractWebPage {

    @FindBy(xpath = "//img[@alt='OTUS Logo']")
    private WebElement otusLogo;

    @FindBy(xpath = "//nav//span[contains(text(),'Обучение')]")
    private WebElement learningOptionBtn;

    @FindBy(xpath = "//div[contains(@class,'gXAYGA')]//a[contains(@class,'dNitgt')]")
    private List<WebElement> coursesOptions;


    public MainPage open() {
        navigateTo("");
        shortWaitUntil(() -> otusLogo.isDisplayed());
        return this;
    }

    public MainPage clickLearningBtn() {
        waitForElementToBeClickable(learningOptionBtn);
        doubleClick(learningOptionBtn);
        return this;
    }

    public WebElement getRandomCourseOption() {
        if (coursesOptions.isEmpty()) {
            throw new NoCourseOptionsAvailableException("No course options are available to choose.");
        }

        return coursesOptions.get(new Random().nextInt(coursesOptions.size()));
    }

    public SingleCoursePage clickWebElement(WebElement element) {
        clickWithHighlight(element);
        return new SingleCoursePage();
    }

}
