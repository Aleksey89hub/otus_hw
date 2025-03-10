package homework;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import lombok.extern.log4j.Log4j;


@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"homework.ui.steps"},
        plugin = {"pretty", "html:target/cucumber-reports.html"},
        monochrome = true
)
@Log4j
public class Runner_Test extends AbstractTestNGCucumberTests {

}
