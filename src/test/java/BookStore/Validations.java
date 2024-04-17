package BookStore;

import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Validations {
    public void verifyNewUserCreated(Response response) {
        System.out.println("Validating user created using User ID");
        String userID = response.jsonPath().getString("userID");
        System.out.println("User ID created" + userID);
//        assertNotNull(userID, "UserID is null : " + userID);
    }
}
