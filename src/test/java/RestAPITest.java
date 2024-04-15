
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestAPITest {
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    public Response getRequest() {
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/posts")
                .then()
                .extract().response();

        assertEquals(200, response.statusCode());
//        assertEquals("qui est esse", response.jsonPath().getString("title[1]"));
        return response;
    }

    public Response postRequest() {
        File newUser = new File("src/test/resources/newUser.json");
        Response response = given().contentType(ContentType.JSON)
                .body(newUser)
                .when()
                .post("/posts")
                .then()
                .extract()
                .response();
        return response;
    }

    @Test
    public void main() {
        System.out.println(postRequest().statusCode());
        System.out.println(postRequest().prettyPrint());
        System.out.println(getRequest().statusCode());
        System.out.println(getRequest().prettyPrint());
    }

}
