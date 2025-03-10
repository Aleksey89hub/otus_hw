package homework.ui.steps;

import com.otus.testframework.framework.framework.pages.CoursesPage;
import com.otus.testframework.framework.framework.pages.SingleCoursePage;
import com.otus.testframework.framework.framework.utils.CourseInfo;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Map;

public class SingleCoursePageSteps {
    @Autowired
    private SingleCoursePage singleCoursePage;

    @Autowired
    private CoursesPage coursesPage;

    private SoftAssert soft = new SoftAssert();
    private Map<String, List<CourseInfo>> earliestAndLatestCourses;

    @When("the user retrieves the earliest and latest course titles")
    public void retrieveEarliestAndLatestCourses() {
        Map<String, CourseInfo> allCoursesInfo = coursesPage.getAllCourses();
        earliestAndLatestCourses = CourseInfo.findEarliestAndLatestCourses(allCoursesInfo);
    }

    @Then("the course page should display the correct titles")
    public void verifyCourseTitles() {
        earliestAndLatestCourses.values().stream()
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

}
