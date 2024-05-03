package BookStore;

import BookStore.POJO.CreateUserRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import utils.ThreadLocalContext;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

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
//        For Generate token post call we don't use POJO we are using File as body
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

    public Response getListOfBooks() {
        System.out.println("Getting list of books in bookstore");
        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .get("BookStore/v1/Book")
                .then()
                .extract().response();
        assertEquals(200, response.statusCode(), "Status code mismatch");
        return response;
    }

    public Response addBookToCart(String userId, String booksPath) throws FileNotFoundException {
        System.out.println("Adding books to user cart from bookstore");
        System.out.println("userID : " + userId);
        ThreadLocalContext.set("userId", userId);
        String booksRequest = "{\n" +
                "  \"userId\": \"%s\",\n" +
                "  \"collectionOfIsbns\": [\n" +
                "    {\n" +
                "      \"isbn\": \"%s\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        JSONObject jsonObject = new JSONObject(Methods.readJsonFile(booksPath));
        JSONArray books = jsonObject.getJSONArray("books");
        String isbn = books.getJSONObject(0).getString("isbn");
        ThreadLocalContext.set("isbn", isbn);
        System.out.println("ISBN : " + isbn);
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + ThreadLocalContext.get("authToken"))
                .body(String.format(booksRequest, userId, isbn))
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .extract().response();
        response.prettyPrint();
        return response;
    }

    public void clearUserCart(Object userId) {
        System.out.println("Clearing User Cart");
        Response response = given()
                .queryParam("UserId", userId.toString())
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + ThreadLocalContext.get("authToken"))
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .extract().response();

        assertEquals(204, response.statusCode(), "Status Code mismatch");
    }

    public Response getBookStoreUserInfo(Object userId) {
        System.out.println("Getting Bookstore User Info ");
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + ThreadLocalContext.get("authToken"))
                .pathParam("UUID", userId)
                .when()
                .get("/Account/v1/User/{UUID}")
                .then()
                .extract().response();
        response.prettyPrint();
        assertEquals(200, response.statusCode(), "Status code mismatch");
        return response;
    }

    public Response updateISBN(String userID, String booksPath) throws FileNotFoundException {
        System.out.println("Updating ISBN value of book");

        JSONObject jsonObject = new JSONObject(Methods.readJsonFile(booksPath));
        JSONArray books = jsonObject.getJSONArray("books");
        String newISBN = books.getJSONObject(6).getString("isbn");
        System.out.println("New ISBN : " + newISBN);
        ThreadLocalContext.set("isbn", newISBN);

        JSONArray booksArray = new JSONArray(getBookStoreUserInfo(userID).jsonPath().getList("books"));
        String oldISBN = booksArray.getJSONObject(0).get("isbn").toString();
        System.out.println("Old ISBN : " + oldISBN);

        String updateISBNRequest = "{\n" +
                "  \"userId\": \"%s\",\n" +
                "  \"isbn\": \"%s\"\n" +
                "}";

        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + ThreadLocalContext.get("authToken"))
                .pathParam("ISBN", oldISBN)
                .body(String.format(updateISBNRequest, userID, newISBN))
                .when()
                .put("/BookStore/v1/Books/{ISBN}")
                .then()
                .extract().response();
        response.prettyPrint();
        assertEquals(200, response.statusCode(), "Status Code Mismatch");
        return response;
    }
}
