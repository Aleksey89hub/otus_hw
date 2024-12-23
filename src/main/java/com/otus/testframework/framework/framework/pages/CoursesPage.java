package com.otus.testframework.framework.framework.pages;

import com.otus.testframework.framework.framework.utils.CourseInfo;
import lombok.extern.log4j.Log4j;
import org.jsoup.Jsoup;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.jsoup.nodes.Document;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Represents the Courses page, providing methods to interact with its elements and extract course information.
 *
 */

@Log4j
public class CoursesPage extends AbstractWebPage {

    @FindBy(xpath = "//div[contains(text(),'Каталог')]")
    private WebElement coursesHeader;

    @FindBy(xpath = "//div[contains(@class,'sc-19syvs')]")
    private WebElement searchField;

    @FindBy(xpath = "//input[@type='search']")
    private WebElement searchInput;

    @FindBy(xpath = "//div[contains(@class,'sc-hrqzy3-1')]")
    private List<WebElement> courseTitles;

    @FindBy(xpath = "//a[contains(@class,'sc-zzdkm7-0')]")
    private List<WebElement> courseCards;


    public CoursesPage open() {
        navigateTo("/catalog/courses");
        shortWaitUntil(() -> isPageLoaded());
        return this;
    }

    public boolean isPageLoaded() {
        return coursesHeader.isDisplayed();
    }

    public void clickSearchField() {
        beforeClick(searchField);
        waitForElementToBeClickable(searchField);
        doubleClick(searchField);
    }

    public CoursesPage enterTextInSearchField(String text) {
        clickSearchField();
        beforeSendKeys(searchInput);
        searchInput.sendKeys(text);

        return this;
    }

    public String findCourseByName(String courseName) {
        waitForVisibilityOfElements(courseTitles);
        return courseTitles.stream()
                .filter(course -> course.getText().equalsIgnoreCase(courseName))
                .findFirst()
                .map(WebElement::getText)
                .orElse("Course not found");
    }

    public Map<String, CourseInfo> getAllCourses() {
        Map<String, CourseInfo> courses = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM, yyyy", Locale.forLanguageTag("ru"));

        for (WebElement card : courseCards) {
            String html = card.getDomProperty("outerHTML");
            Document doc = Jsoup.parse(html);

            String titleXPath = "//h6[contains(@class, 'sc-1yg5ro0-1')]//div[contains(@class, 'jEGzDf')]";
            String dateXPath = ".//div[contains(@class, 'sc-157icee-0')]//div[contains(@class, 'jEGzDf')]";
            String linkXPath = "//a[contains(@class, 'sc-zzdkm7-0')]";

            String courseTitle = doc.selectXpath(titleXPath).text();
            String firstDate = doc.selectXpath(dateXPath).text().split(" · ")[0].trim();
            String courseLink =  doc.selectXpath(linkXPath).attr("href");


            try {
                LocalDate startDate = LocalDate.parse(firstDate, formatter);
                CourseInfo courseInfo = new CourseInfo(courseTitle, startDate, courseLink);
                courses.put(courseTitle, courseInfo);
            } catch (Exception e) {
                log.error(String.format("Error parsing course: %s", courseTitle));
                e.printStackTrace();
            }
        }
        return courses;
    }
}
