import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class RestAPITestPost {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://timesofindia.indiatimes.com/ads.txt";
    }

    @Test
    public void postRequest() {

        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .and()
                .body("")
                .get()
                .then()
                .extract().response();

        System.out.println(response.statusCode());
        System.out.println(response.body().prettyPrint());
        String responseBody = response.asString();
        String splitChar[] = responseBody.split(" ");
        int temp = 0;
        for(int i=0; i < splitChar.length; i++){
            if (splitChar[i].contains("pubmatic.com")){
                temp++;
            }
        }
        System.out.println("count of pubmatic.com : " + temp);
    }
}