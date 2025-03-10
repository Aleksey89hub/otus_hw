package homework.ui.steps;

import com.otus.testframework.framework.api.config.TestAPIConfig;
import io.cucumber.spring.CucumberContextConfiguration;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


@CucumberContextConfiguration
@SpringBootTest
@ContextConfiguration(classes = TestAPIConfig.class)
public class CucumberSpringConfiguration extends AbstractTestNGCucumberTests {

}
