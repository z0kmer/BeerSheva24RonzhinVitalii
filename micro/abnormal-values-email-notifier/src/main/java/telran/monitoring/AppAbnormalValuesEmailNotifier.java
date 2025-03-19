package telran.monitoring;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;

import org.json.JSONObject;

import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent.DynamodbStreamRecord;
import com.amazonaws.services.lambda.runtime.events.models.dynamodb.AttributeValue;
import com.sun.jdi.connect.Transport;

import telran.monitoring.logging.Logger;
import telran.monitoring.logging.LoggerStandard;

public class AppAbnormalValuesEmailNotifier implements RequestHandler<DynamodbEvent, String> {
    private static final String EMAIL_PROVIDER_URL = System.getenv("EMAIL_PROVIDER_URL");
    private static final Logger logger = new LoggerStandard("email-notifier");

    @Override
    public String handleRequest(DynamodbEvent event, Context context) {
        for (DynamodbStreamRecord record : event.getRecords()) {
            if ("INSERT".equals(record.getEventName())) {
                Map<String, AttributeValue> newImage = record.getDynamodb().getNewImage();
                if (newImage != null) {
                    processRecord(newImage);
                }
            }
        }
        return "Processed successfully";
    }

    private void processRecord(Map<String, AttributeValue> newImage) {
        try {
            long patientId = Long.parseLong(newImage.get("patientId").getN());
            int value = Integer.parseInt(newImage.get("value").getN());
            String email = getEmailForPatient(patientId);

            if (email != null) {
                sendEmailNotification(email, patientId, value);
            } else {
                logger.log("severe", "Email not found for patientId: " + patientId);
            }
        } catch (Exception e) {
            logger.log("severe", "Failed to process record: " + e.toString());
        }
    }

    private String getEmailForPatient(long patientId) throws Exception {
        URL url = new URL(EMAIL_PROVIDER_URL + "?patientId=" + patientId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                // Извлечение email из JSON-ответа
                JSONObject jsonResponse = new JSONObject(response.toString());
                return jsonResponse.getString("email");
            }
        } else {
            logger.log("warning", "Failed to fetch email for patientId " + patientId +
                    ". Response code: " + responseCode);
        }
        return null;
    }

    private void sendEmailNotification(String email, long patientId, int value) throws Exception {
        logger.log("info", "Sending email to " + email + " about patientId " + patientId + " with abnormal value: " + value);
        // Настройка SMTP-сервера
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.example.com");
        properties.put("mail.smtp.port", "25");
        Session session = Session.getDefaultInstance(properties);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("noreply@example.com")); 
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email)); 
            message.setText("Dear user,\n\nPatient ID: " + patientId +
                    " has recorded an abnormal pulse value of: " + value + ".\n\nBest regards,\nMonitoring Team");

            Transport.send(message); 
            logger.log("info", "Email sent successfully to " + email);
        } catch (MessagingException e) {
            logger.log("severe", "Failed to send email: " + e.getMessage());
            throw new RuntimeException("Error while sending email", e);
        }
    }
}
