package com.otus.testframework.framework.framework.pages;

import com.otus.testframework.framework.enums.CourseCategory;
import com.otus.testframework.framework.framework.driver.WebDriverRun;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Represents a single course page, providing methods to retrieve course details.
 *
 */

public class SingleCoursePage extends AbstractWebPage {

    @FindBy(xpath = "//h1[contains(@class,'sc-1x9oq14')]")
    private WebElement title;


    public String getCourseTitle() {
        shortWaitUntil(() -> title.isDisplayed());
        return title.getText();
    }

    public boolean isCoursesOptionChecked(CourseCategory courseCategory) {
        shortWaitUntil(() -> title.isDisplayed());
        String xpath = String.format(
                "//label[contains(text(),'%s')]/ancestor::div[contains(@class, 'sc-1f') and " +
                        "contains(@class, 'eQrMuA')]//input",
                courseCategory.getDisplayName()
        );

        return WebDriverRun.getDriver().findElement(By.xpath(xpath)).isSelected();
    }


}
