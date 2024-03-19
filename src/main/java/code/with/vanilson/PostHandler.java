package code.with.vanilson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;

/**
 * Handler for requests to Lambda function.
 */
public class PostHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    public static final String USER_ID = "userId";

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        String requestBody = input.getBody();
        Gson gson = new Gson();

        Map<String, String> userDetails = gson.fromJson(requestBody, Map.class);
        userDetails.put(USER_ID, UUID.randomUUID().toString());
        //todo: Process user details

        //noinspection MismatchedQueryAndUpdateOfCollection
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("firstName", userDetails.get("firstName"));
        stringMap.put("lastName", userDetails.get("lastName"));
        stringMap.put(USER_ID, userDetails.get(USER_ID));

        var response = new APIGatewayProxyResponseEvent();
        response.withStatusCode(200);
        response.withBody(gson.toJson(stringMap, Map.class));

        Map<String, String> responseHeaders = new HashMap<>();
        responseHeaders.put("Content-Type", "application/json");
        response.withHeaders(responseHeaders);

        return response;
    }

}
