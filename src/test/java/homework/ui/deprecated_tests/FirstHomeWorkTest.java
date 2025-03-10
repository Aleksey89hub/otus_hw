package homework.ui.deprecated_tests;

import com.otus.testframework.framework.enums.CourseCategory;
import com.otus.testframework.framework.framework.pages.CoursesPage;
import com.otus.testframework.framework.framework.pages.MainPage;
import com.otus.testframework.framework.framework.pages.SingleCoursePage;
import com.otus.testframework.framework.framework.utils.CourseInfo;
import jdk.jfr.Description;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static com.otus.testframework.framework.framework.utils.StringUtils.stripNumbersInParentheses;

public class FirstHomeWorkTest extends BaseTest {
    private final String COURSE_NAME = "Java QA Engineer. Professional";
    private CoursesPage coursesPage = new CoursesPage();
    private SingleCoursePage singleCoursePage = new SingleCoursePage();
    private MainPage mainPage = new MainPage();


    @Description("This test verifies that the user can find a course by its name.")
    @Test
    public void findAndVerifyCoursePageTest() {
        coursesPage.
                open().
                enterTextInSearchField(COURSE_NAME);
        String coursesTitle = coursesPage.findCourseByName(COURSE_NAME);

        Assert.assertEquals(coursesTitle, COURSE_NAME, String.format("Course title %s does not match.", coursesTitle));
    }

    @Description("Verify that the course cards for the earliest and latest courses display the correct titles")
    @Test
    public void verifyCourseCardsTest() {
        coursesPage.
                open();
        Map<String, CourseInfo> allCoursesInfo = coursesPage.getAllCourses();
        Map<String, List<CourseInfo>> earliestAndLatest = CourseInfo.findEarliestAndLatestCourses(allCoursesInfo);

        earliestAndLatest.values().stream()
                .flatMap(List::stream)
                .forEach(courseInfo -> {
                    coursesPage.navigateTo(courseInfo.getLink());
                    String actualTitle = singleCoursePage.getCourseTitle();
                    soft.assertTrue(
                            actualTitle.contains(courseInfo.getTitle()),
                            String.format("Expected course title '%s' to be displayed, but got '%s'", courseInfo.getTitle(), actualTitle)
                    );
                });
        soft.assertAll();
    }

    @Test
    @Description("Verify that selecting a random course category from the 'Обучение' menu opens the correct course")
    public void verifyCorrectCourseCatalogOpensForRandomCategory() {
        mainPage
                .open()
                .clickLearningBtn();
        WebElement randomElement = mainPage.getRandomCourseOption();
        String courseName = stripNumbersInParentheses(randomElement.getText());
        CourseCategory courseCategory = CourseCategory.fromString(courseName);
        SingleCoursePage  singleCoursePage = mainPage.clickWebElement(randomElement);

        Assert.assertTrue(singleCoursePage.isCoursesOptionChecked(courseCategory),
                "The opened course category does not match the selected one.");

    }

}
