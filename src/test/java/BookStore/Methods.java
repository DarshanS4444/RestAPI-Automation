package BookStore;

import io.restassured.response.Response;
import org.json.JSONArray;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class Methods {

    public void storeBooks(Response response, String filePath) {
        System.out.println("Storing List of books");
        String jsonResponse = response.getBody().asString();
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(jsonResponse);
        } catch (java.io.IOException e) {
            throw new UncheckedIOException("Error writing JSON response to file", e);
        }
    }

    public static String readJsonFile(String filePath) throws FileNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();
        Scanner scanner = new Scanner(new File(filePath));
        while (scanner.hasNextLine()) {
            stringBuilder.append(scanner.nextLine());
        }
        scanner.close();
        return stringBuilder.toString();
    }

    public void verifyResponseMessage(String expectedResponse, String actualResponse) {
        System.out.println("Comparing expected and actual response message");
        assertEquals(expectedResponse, actualResponse, "Status Message mismatch");
    }

    public void verifyBookIsAddedToCart(Response response, String isbn) {
        System.out.println("Verifying Book is added to user's cart");
        boolean bookAddedToCart = false;
        List<Object> booksList = response.jsonPath().getList("books");
        JSONArray jsonArray = new JSONArray(booksList);
        assertFalse(jsonArray.isEmpty());

        for (int i = 0; i < jsonArray.length(); i++) {
            String responseIsbn = jsonArray.getJSONObject(i).get("isbn").toString();
            if (responseIsbn.equals(isbn)) {
                bookAddedToCart = true;
                break;
            }
        }
        assertTrue(bookAddedToCart, String.format("Added book with isbn : %s not present", isbn));
    }
}