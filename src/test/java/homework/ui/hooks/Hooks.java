package homework.ui.hooks;

import com.otus.testframework.framework.framework.driver.WebDriverRun;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import lombok.extern.log4j.Log4j;

@Log4j
public class Hooks {

    @Before
    public void setUp() {
        log.info("Starting scenario...");
        WebDriverRun.getDriver();
    }

    @After
    public void tearDown() {
        log.info("Closing WebDriver after scenario...");
        WebDriverRun.quitAllDrivers();
    }
}