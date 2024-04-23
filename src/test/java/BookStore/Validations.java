package BookStore;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.ThreadLocalContext;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Validations {
    public void verifyNewUserCreated(Response response) {
        System.out.println("Validating user created using User ID");
        String userID = response.jsonPath().getString("userID");
        System.out.println("User ID created : " + userID);
        ThreadLocalContext.set("UUID", userID);
        assertNotNull(userID, "UserID is null : " + userID);
    }

    public void verifyUserIsNotAuthorised(Response response) {
        System.out.println("Verifying User is not authorised to perform the action");
        int responseStatusCode = response.statusCode();
        assertEquals(401, responseStatusCode, "Status code mismatch : " + responseStatusCode);
    }

    public void verifyUserIsDeleted(RequestSpecification requestSpecification) {
        System.out.println("Verifying User is deleted");

        Response response = given()
                .baseUri("https://bookstore.toolsqa.com/")
                .spec(requestSpecification)
                .when()
                .get("/Account/v1/User/{UUID}")
                .then()
                .extract().response();

        String statusMessage = response.jsonPath().getString("message");
        System.out.println("Status Message Displayed : " + statusMessage);
        assertEquals(401, response.statusCode(), "StatusCode mismatch : " + response.statusCode());
        assertEquals("User not found!", statusMessage, "Status Message mismatch : " + statusMessage);
    }
}
