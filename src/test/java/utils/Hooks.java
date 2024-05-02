package utils;

import BookStore.APICalls;
import io.cucumber.java.Before;
public class Hooks {
//    This before to generate token before we run few tests - we can either give tags or feature file path
    @Before("@AddBooksToCart")
    public void generateAuthTokenForUser(){
        new APICalls().generateAuthToken();
    }
}
