package steps;

import BookStore.APICalls;
import BookStore.Validations;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

public class MySteps {
    Response response;
    @Given("I create a new user for Book store")
    public void iCreateAnewUserForBookStore() {
        response = new APICalls().createNewUser();
    }

    @Then("I verify user is Created")
    public void iVerifyUserIsCreated() {
        new Validations().verifyNewUserCreated(response);
    }
}
