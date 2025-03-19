package telran.monitoring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;

import telran.monitoring.logging.*;

public class AppEmailAddressProvider implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static final String DB_URL = System.getenv("DB_CONNECTION_STRING");
    private static final String DB_USER = System.getenv("USERNAME");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");
    private static final Logger logger = new LoggerStandard("email-provider");

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        response.setHeaders(headers);

        Map<String, String> queryStringParameters = request.getQueryStringParameters();
        String patientIdStr = queryStringParameters != null ? queryStringParameters.get("patientId") : null;

        if (patientIdStr == null) {
            response.setStatusCode(400);
            response.setBody("{\"error\":\"patientId is required\"}");
            return response;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT email FROM patients WHERE patient_id = ?"
            );
            statement.setLong(1, Long.parseLong(patientIdStr));
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String email = resultSet.getString("email");
                response.setStatusCode(200);
                response.setBody("{\"email\":\"" + email + "\"}");
            } else {
                response.setStatusCode(404);
                response.setBody("{\"error\":\"No email found for patientId " + patientIdStr + "\"}");
            }
        } catch (Exception e) {
            logger.log("severe", e.toString());
            response.setStatusCode(500);
            response.setBody("{\"error\":\"Internal server error\"}");
        }

        return response;
    }
}
