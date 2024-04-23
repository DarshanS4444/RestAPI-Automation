package steps;

import BookStore.APICalls;
import BookStore.Validations;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.APIRequestSpecification;
import utils.ThreadLocalContext;

import java.util.HashMap;

public class MySteps {
    Response response;
    RequestSpecification requestSpecification;

    @Given("I create a new user for Book store")
    public void iCreateAnewUserForBookStore() {
        response = new APICalls().createNewUser();
    }

    @Then("I verify user is Created")
    public void iVerifyUserIsCreated() {
        new Validations().verifyNewUserCreated(response);
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
