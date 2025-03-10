package homework.api.negative;

import com.otus.testframework.framework.api.config.TestAPIConfig;
import com.otus.testframework.framework.api.services.PetStoreIpi;
import homework.api.BaseTestAPI;
import io.restassured.response.ValidatableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.Test;


@SpringBootTest(classes = TestAPIConfig.class)
public class UserApiNegativeTests extends BaseTestAPI {
    @Autowired
    private PetStoreIpi petStoreApi;

    /**
     * Tests that attempting to retrieve a non-existent user from the API
     * returns a 404 Not Found response.
     */
    @Test
    public void getUserThatDoesNotExistShouldReturnNotFoundTest() {
        ValidatableResponse response = petStoreApi.getUser("nonexistentuser");

        soft.assertEquals(response.extract().statusCode(), 404, "Expected status code 404.");
        soft.assertEquals(response.extract().path("message"), "User not found", "Expected 'User not found' message.");
        soft.assertAll();
    }
}
