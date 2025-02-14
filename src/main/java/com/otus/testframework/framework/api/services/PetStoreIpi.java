package com.otus.testframework.framework.api.services;

import com.github.javafaker.Faker;
import com.otus.testframework.framework.api.dto.UserDTO;
import com.otus.testframework.framework.framework.config.TestConfig;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static io.restassured.RestAssured.given;

/**
 * This class contains automated tests for the PetStore API.
 * It tests the functionality of creating users (POST /user)
 * and retrieving user details (GET /user/{username}) to ensure the API behaves as expected.
 */

@Service
public class PetStoreIpi {

    private RequestSpecification spec;
    private static final String BASE_URL = TestConfig.CONFIG.urlAPI();
    private static final Faker faker = new Faker();
    private static final String USER_PATH = "/user";
    private static final String RETRIEVE_USER = "/{username}";
    private static final String LOGIN_USER = "/login";

    public PetStoreIpi() {
        spec = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .log().all();
    }

    public ValidatableResponse createUser(UserDTO user) {
        return given(spec)
                .body(user)
                .basePath(USER_PATH)
                .when()
                .post()
                .then()
                .log().ifValidationFails();
    }

    public ValidatableResponse getUser(String username) {
        return given(spec)
                .pathParam("username", username)
                .when()
                .get(USER_PATH + RETRIEVE_USER)
                .then();
    }

    public ValidatableResponse loginUser(String username, String password) {
        return given(spec)
                .queryParam("username", username)
                .queryParam("password", password)
                .when()
                .get(USER_PATH + LOGIN_USER)
                .then()
                .log().all();
    }

    public static UserDTO createUser() {
        return UserDTO.builder()
                .lastName(faker.name().lastName())
                .phone(faker.phoneNumber().phoneNumber())
                .email(faker.internet().emailAddress())
                .username(faker.name().username())
                .userStatus(faker.number().randomNumber(3, true))
                .id(faker.number().randomNumber(3, true))
                .firstName(faker.name().firstName())
                .password(faker.internet().password())
                .build();
    }

    public ValidatableResponse deleteUserByUsername(String username) {
        return given(spec)
                .pathParam("username", username)
                .when()
                .delete(USER_PATH + RETRIEVE_USER)
                .then()
                .log().ifValidationFails();
    }

}
