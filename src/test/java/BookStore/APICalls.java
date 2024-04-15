package BookStore;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class APICalls {

    private static int statusCode = 0;
    public APICalls(){
        RestAssured.baseURI = "https://bookstore.toolsqa.com/";
    }
    public Response createNewUser() {
        System.out.println("Calling POST request to Create a new user");
        Response response = given().contentType(ContentType.JSON).
                body("{\n" +
                        "  \"userName\": \"Ranjith\",\n" +
                        "  \"password\": \"Ranjith@123\"\n" +
                        "}")
                .when()
                .post("/Account/v1/User")
                .then()
                .extract().response();
        statusCode = response.statusCode();
        assertEquals(201,statusCode,"Status Code incorrect : " + statusCode);
        return response;
    }
}
