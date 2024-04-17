package BookStore;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class APICalls {

    private static int statusCode = 0;

    public APICalls() {
        RestAssured.baseURI = "https://bookstore.toolsqa.com/";
    }

    public Response createNewUser() {
        System.out.println("Calling POST request to Create a new user");
        File newUser = new File("src/test/resources/BookstoreUserData.json");

        Response response = given()
                .header("Content-type", "application/json")
                .body(newUser)
                .when()
                .post("/Account/v1/User")
                .then()
                .extract().response();

        response.prettyPrint();
        statusCode = response.statusCode();
        System.out.println("Status Code : " + statusCode);
        System.out.println("Body" + response.body());
        assertEquals(201, statusCode, "Status Code incorrect : " + statusCode);
        return response;
    }
}
