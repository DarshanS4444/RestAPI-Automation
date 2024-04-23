package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;

public class APIRequestSpecification {

    public static RequestSpecification setRequestSpecification(HashMap<String, String> headers,
                                                               HashMap<String, Object> pathParams,
                                                               HashMap<String, Object> queryParams) {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();

        if (headers != null) {
            requestSpecBuilder.addHeaders(headers);
        }
        if (pathParams != null) {
            requestSpecBuilder.addPathParams(pathParams);
        }
        if (queryParams != null) {
            requestSpecBuilder.addQueryParams(queryParams);
        }
        return requestSpecBuilder.build();
    }
}
