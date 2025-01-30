package homework;

import com.otus.testframework.framework.api.dto.UserDTO;
import com.otus.testframework.framework.api.services.PetStoreIpi;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;


public class UserApiTest extends BaseTest {
    private final PetStoreIpi api = new PetStoreIpi();
    private final UserDTO USER = PetStoreIpi.createUser();

    /**
     * Test: Verify that creating a user returns a success response.
     * Expected Behavior:
     * - The server should respond with HTTP 200.
     * - The response message should contain the ID of the created user as a string.
     */
    @Test
    public void createUserShouldReturnSuccessTest() {
        ValidatableResponse response = api.createUser(USER);
        String user_id = USER.getId().toString();

        soft.assertEquals(response.extract().statusCode(), 200,
                "Expected status code 200, but got a different value.");
        soft.assertEquals(response.extract().path("message"), user_id,
                String.format("Expected response message to match the user ID %s, but it did not.", user_id));
        soft.assertAll();
    }

    /**
     * Test: Verify that retrieving an existing user by username returns the correct user details.
     * Expected Behavior:
     * - The server should respond with HTTP 200.
     * - The response should contain the correct user details, including username, first name, and email.
     */
    @Test(dependsOnMethods = "createUserShouldReturnSuccessTest")
    public void getUserShouldReturnCorrectUserDetailsTest() {
        ValidatableResponse response = api.getUser(USER.getUsername());

        soft.assertEquals(response.extract().statusCode(), 200,
                "Expected status code 200, but got a different value.");
        soft.assertEquals(response.extract().path("username"), USER.getUsername(),
                String.format("Expected username '%s', but got a different value.", USER.getUsername()));
        soft.assertEquals(response.extract().path("firstName"), USER.getFirstName(),
                String.format("Expected first name '%s', but got a different value.", USER.getFirstName()));
        soft.assertEquals(response.extract().path("email"), USER.getEmail(),
                String.format("Expected email '%s', but got a different value.", USER.getEmail()));
        soft.assertAll();
    }

    /**
     * Test: Verify that logging in with valid credentials returns a success response.
     * Expected Behavior:
     * - The server should respond with HTTP 200.
     * - The response should include a "message" field that confirms successful login.
     */
    @Test(dependsOnMethods = "getUserShouldReturnCorrectUserDetailsTest")
    public void loginUserShouldReturnSuccessTest() {
        ValidatableResponse response = api.loginUser(USER.getFirstName(), USER.getPassword());

        soft.assertEquals(response.extract().statusCode(), 200,
                "Expected status code 200 for successful login, but got a different value.");
        soft.assertNotNull(response.extract().path("message"),
                "Expected a non-null 'message' in the response, but it was null.");
        soft.assertAll();
    }

    /**
     * Test: Verify that retrieving a user who does not exist returns a not found error.
     * Expected Behavior:
     * - The server should respond with HTTP 404.
     * - The response should indicate that the user was not found.
     */
    @Test
    public void getUserThatDoesNotExistShouldReturnNotFoundTest() {

        String nonExistentUsername = "nonexistentuser";
        ValidatableResponse response = api.getUser(nonExistentUsername);

        soft.assertEquals(response.extract().statusCode(), 404,
                "Expected status code 404 for a non-existent user, but got a different value.");
        soft.assertEquals(response.extract().path("message"), "User not found",
                "Expected error message 'User not found' for a non-existent user, but got a different value.");
        soft.assertAll();
    }

}
