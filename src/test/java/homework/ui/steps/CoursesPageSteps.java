package homework.ui.steps;

import com.otus.testframework.framework.framework.pages.CoursesPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;

public class CoursesPageSteps {
    @Autowired
    private CoursesPage coursesPage;

    @When("the user searches for {string}")
    public void searchForCourse(String courseName) {
        coursesPage.enterTextInSearchField(courseName);
    }

    @Then("the course title should be {string}")
    public void verifyCourseTitle(String expectedTitle) {
        String actualTitle = coursesPage.findCourseByName(expectedTitle);
        Assert.assertEquals(actualTitle, expectedTitle, "Course title does not match.");
    }
}
