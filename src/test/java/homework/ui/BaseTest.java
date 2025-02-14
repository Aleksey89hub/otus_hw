package homework.ui;

import com.otus.testframework.framework.framework.driver.WebDriverRun;
import lombok.extern.log4j.Log4j;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;

@Log4j
public class BaseTest {

    public SoftAssert soft = new SoftAssert();

    @BeforeMethod
    public void before(Method method) {
        WebDriverRun.getDriver();
        log.info(String.format("Test %s is started!", method.getName()));
    }

    @AfterMethod
    public void afterMethod(Method method, ITestResult result) {
        log.info("Tests automation is ended");
        String testName = method.getName();
        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                log.info(String.format("Test %s is PASSED", testName));
                break;
            case ITestResult.FAILURE:
                log.error(String.format("Test %s is FAILED", testName), result.getThrowable());
                break;
            case ITestResult.SKIP:
                log.warn(String.format("Test %s is SKIPPED", testName));
                break;
            default:
                log.warn(String.format("Test %s has UNKNOWN status", testName));
        }
    }

    @AfterSuite
    public void afterSuite() {
        WebDriverRun.quitAllDrivers();
    }
}
