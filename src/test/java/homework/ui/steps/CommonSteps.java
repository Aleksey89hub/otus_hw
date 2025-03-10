package homework.ui.steps;

import com.otus.testframework.framework.framework.pages.CoursesPage;
import com.otus.testframework.framework.framework.pages.MainPage;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

public class CommonSteps {
    @Autowired
    private CoursesPage coursesPage;

    @Autowired
    private MainPage mainPage;

    @Given("the user is on the courses page")
    public void openCoursesPage() {
        coursesPage.open();
    }

    @Given("the user is on the main page")
    public void openMainPage() {
        mainPage.open();
    }
}
