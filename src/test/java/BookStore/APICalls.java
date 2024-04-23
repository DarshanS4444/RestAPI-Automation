package BookStore;

import BookStore.POJO.CreateUserRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.ThreadLocalContext;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class APICalls {

    private static int statusCode = 0;

    public APICalls() {
        RestAssured.baseURI = "https://bookstore.toolsqa.com/";
    }

    public Response createNewUser(CreateUserRequest createUserRequest) {
        System.out.println("Calling POST request to Create a new user");
//        We can send request body as File or string or POJO class - here i have used POJO
//        File newUser = new File("src/test/resources/BookstoreUserData.json");
//        String reqBody = "{\n" +
//               "  \"userName\": \"Megha\",\n" +
//               "  \"password\": \"Megha@123\"\n" +
//               "}";


        Response response = given()
                .header("Content-type", "application/json")
                .body(createUserRequest)
                .when()
                .post("/Account/v1/User")
                .then()
                .extract().response();

        response.prettyPrint();
        statusCode = response.statusCode();
        System.out.println("Status Code : " + statusCode);
        assertEquals(201, statusCode, "Status Code incorrect : " + statusCode);
        return response;
    }

    public Response deleteUser(RequestSpecification requestSpecification) {
        System.out.println("Calling DELETE request to delete user");

        Response response = given()
                .spec(requestSpecification)
                .when()
                .delete("/Account/v1/User/{UUID}")
                .then()
                .extract().response();

        response.prettyPrint();
        System.out.println("Status Code : " + response.statusCode());
        return response;
    }

    public Response generateAuthToken() {
//        For Generate token post call we dont use POJO we are using File as body
        System.out.println("Generating Authorisation token for User");
        File userData = new File("src/test/resources/BookstoreUserData.json");
        Response response = given()
                .header("Content-type", "application/json")
                .body(userData)
                .when()
                .post("/Account/v1/GenerateToken")
                .then()
                .extract().response();
        response.prettyPrint();
        String token = response.jsonPath().getString("token");
        System.out.println("Token : " + token);
        ThreadLocalContext.set("authToken", token);
        assertEquals(200, response.statusCode(), "status code mismatch : " + response.statusCode());
        return response;
    }
}
