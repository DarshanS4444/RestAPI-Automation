package steps;

import BookStore.APICalls;
import BookStore.POJO.CreateUserRequest;
import BookStore.POJO.CreateUserResponse;
import BookStore.Validations;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.APIRequestSpecification;
import utils.TestDataLoader;
import utils.ThreadLocalContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySteps {
    Response response;
    RequestSpecification requestSpecification;

    @Given("I create a new user for Book store")
    public void iCreateAnewUserForBookStore() throws IOException {
//        using test data manager and POJO to send request to createUser
        Map<String, Object> testData = TestDataLoader.loadTestData("src/test/resources/TestData.json");
        CreateUserRequest createUserRequest = new CreateUserRequest();
        Map<String, String> userData = ((List<Map<String, String>>) testData.get("users")).get(0);
        createUserRequest.setUserName(userData.get("username"));
        createUserRequest.setPassword(userData.get("password"));

        response = new APICalls().createNewUser(createUserRequest);
    }

    @Then("I verify user is Created")
    public void iVerifyUserIsCreated() {
// using POJO class of response to validate
        CreateUserResponse createUserResponse = response.as(CreateUserResponse.class);
        new Validations().verifyNewUserCreated(createUserResponse);
    }

    @And("I generate authorisation token for the user")
    public void iGenerateAuthorisationTokenForTheUser() {
        response = new APICalls().generateAuthToken();
    }

    @And("I delete the user without authorisation")
    public void iDeleteTheUserWithoutAuthorisation() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-type", "application/json");
        HashMap<String, Object> pathParams = new HashMap<>();
        pathParams.put("UUID", ThreadLocalContext.get("UUID"));
        requestSpecification = APIRequestSpecification.setRequestSpecification(headers, pathParams, null);
        response = new APICalls().deleteUser(requestSpecification);
    }

    @And("I delete the user with authorisation")
    public void iDeleteTheUserWithAuthorisation() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-type", "application/json");
        headers.put("Authorization", "Bearer " + ThreadLocalContext.get("authToken"));
        HashMap<String, Object> pathParams = new HashMap<>();
        pathParams.put("UUID", ThreadLocalContext.get("UUID"));
        requestSpecification = APIRequestSpecification.setRequestSpecification(headers, pathParams, null);
        response = new APICalls().deleteUser(requestSpecification);
    }

    @Then("I verify user is deleted")
    public void iVerifyUserIsDeleted() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-type", "application/json");
        headers.put("Authorization", "Bearer " + ThreadLocalContext.get("authToken"));
        HashMap<String, Object> pathParams = new HashMap<>();
        pathParams.put("UUID", ThreadLocalContext.get("UUID"));
        requestSpecification = APIRequestSpecification.setRequestSpecification(headers, pathParams, null);
        new Validations().verifyUserIsDeleted(requestSpecification);
    }

    @And("I verify user is not authorised")
    public void iVerifyUserIsNotAuthorised() {
        new Validations().verifyUserIsNotAuthorised(response);
    }
}
