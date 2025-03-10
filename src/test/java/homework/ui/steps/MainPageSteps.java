package homework.ui.steps;

import com.otus.testframework.framework.enums.CourseCategory;
import com.otus.testframework.framework.framework.pages.MainPage;
import com.otus.testframework.framework.framework.pages.SingleCoursePage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;

import static com.otus.testframework.framework.framework.utils.StringUtils.stripNumbersInParentheses;

public class MainPageSteps {

    @Autowired
    private MainPage mainPage;

    private SingleCoursePage singleCoursePage;
    private WebElement selectedCategoryElement;
    private String selectedCategoryName;
    private CourseCategory selectedCourseCategory;

    @When("the user selects a random course category from the 'Обучение' menu")
    public void selectRandomCourseCategory() {
        mainPage.clickLearningBtn();
        selectedCategoryElement = mainPage.getRandomCourseOption();
        selectedCategoryName = stripNumbersInParentheses(selectedCategoryElement.getText());
        selectedCourseCategory = CourseCategory.fromString(selectedCategoryName);
        singleCoursePage = mainPage.clickWebElement(selectedCategoryElement);
    }

    @Then("the correct course catalog should open")
    public void verifyCorrectCourseCatalogOpens() {
        Assert.assertTrue(
                singleCoursePage.isCoursesOptionChecked(selectedCourseCategory),
                "The opened course category does not match the selected one."
        );
    }
}
