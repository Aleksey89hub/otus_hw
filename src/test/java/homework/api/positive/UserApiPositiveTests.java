package homework.api.positive;

import com.otus.testframework.framework.api.config.TestAPIConfig;
import com.otus.testframework.framework.api.dto.UserDTO;
import com.otus.testframework.framework.api.services.PetStoreIpi;

import homework.api.BaseTestAPI;
import io.restassured.response.ValidatableResponse;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = TestAPIConfig.class)
public class UserApiPositiveTests extends BaseTestAPI {

    @Autowired
    private PetStoreIpi petStoreApi;

    private static final int HTTP_OK = 200;
    private static final List<String> createdUsers = new ArrayList<>();
    private UserDTO user;

    /**
     * Setup: Create a new unique user for each test case.
     */
    @BeforeMethod
    public void setup() {
        user = petStoreApi.createUser();
        petStoreApi.createUser(user);
        createdUsers.add(user.getUsername());
    }

    /**
     * Test: Verify that creating a user returns a success response.
     */
    @Test
    public void createUserAndVerifyDetailsTest() {

        ValidatableResponse createResponse = petStoreApi.createUser(user);
        ValidatableResponse getUserResponse = petStoreApi.getUser(user.getUsername());
        String userId = createResponse.extract().path("message");

        soft.assertEquals(createResponse.extract().statusCode(), HTTP_OK,
                "Expected status code 200 after user creation.");
        soft.assertNotNull(userId, "User ID should not be null in the response.");
        soft.assertEquals(getUserResponse.extract().statusCode(), HTTP_OK,
                "Expected status code 200 when retrieving the created user.");
        soft.assertEquals(getUserResponse.extract().path("username"), user.getUsername(),
                "Username should match.");
        soft.assertEquals(getUserResponse.extract().path("firstName"), user.getFirstName(),
                "First name should match.");
        soft.assertEquals(getUserResponse.extract().path("email"), user.getEmail(),
                "Email should match.");
        soft.assertAll();
    }

    /**
     * Test: Verify that retrieving an existing user returns correct details.
     */
    @Test
    public void getUserShouldReturnCorrectUserDetailsTest() {
        ValidatableResponse response = petStoreApi.getUser(user.getUsername());

        soft.assertEquals(response.extract().statusCode(), HTTP_OK,
                "Expected status code 200, but got a different value.");
        soft.assertEquals(response.extract().path("username"), user.getUsername(),
                String.format("Expected username '%s', but got a different value.", user.getUsername()));
        soft.assertEquals(response.extract().path("firstName"), user.getFirstName(),
                String.format("Expected first name '%s', but got a different value.", user.getFirstName()));
        soft.assertEquals(response.extract().path("email"), user.getEmail(),
                String.format("Expected email '%s', but got a different value.", user.getEmail()));
        soft.assertAll();
    }

    /**
     * Test: Verify that logging in with valid credentials returns a success response.
     */
    @Test
    public void loginUserShouldReturnSuccessTest() {
        ValidatableResponse response = petStoreApi.loginUser(user.getFirstName(), user.getPassword());

        soft.assertEquals(response.extract().statusCode(), HTTP_OK,
                "Expected status code 200 for successful login, but got a different value.");
        soft.assertNotNull(response.extract().path("message"),
                "Expected a non-null 'message' in the response, but it was null.");
        soft.assertAll();
    }

    /**
     * Cleanup: Delete all created users after all tests finish.
     */
    @AfterClass
    public void cleanData() {
        for (String username : createdUsers) {
            if (petStoreApi.getUser(username).extract().statusCode() == HTTP_OK) {
                petStoreApi.deleteUserByUsername(username);
            }
        }
    }

}
