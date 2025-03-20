package telran.monitoring;

import java.util.List;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import telran.monitoring.api.SensorData;
import telran.monitoring.logging.Logger;

public class LatestDataSaverS3 extends AbstractDataSaverLogger {
    private final S3Client s3Client;
    private final String bucketName;

    public LatestDataSaverS3(Logger logger) {
        super(logger);
        s3Client = S3Client.builder().build();
        bucketName = System.getenv("S3_BUCKET_NAME");
        if (bucketName == null || bucketName.isEmpty()) {
            throw new RuntimeException("Environment variable S3_BUCKET_NAME is not set.");
        }
        logger.log("config", "Using S3 bucket: " + bucketName);
    }

    @Override
    public void addValue(SensorData data) {
        try {
            String key = "patient-" + data.patientId() + "-" + data.timestamp() + ".json";
            String jsonData = data.toString();

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromString(jsonData));
            logger.log("info", "Saved data to S3 bucket: " + bucketName + " with key: " + key);
        } catch (Exception e) {
            logger.log("severe", "Failed to save data to S3: " + e.getMessage());
        }
    }

    @Override
    public List<SensorData> getAllValues(long patientId) {
        logger.log("info", "Fetching all values for patient: " + patientId);
        return List.of(); // Заглушка: Логика извлечения данных должна быть реализована здесь
    }

    @Override
    public SensorData getLastValue(long patientId) {
        logger.log("info", "Fetching last value for patient: " + patientId);
        return null; // Заглушка: Логика извлечения последнего объекта должна быть реализована здесь
    }

    @Override
    public void clearValues(long patientId) {
        logger.log("info", "Clearing all values for patient: " + patientId);
        // Заглушка: Логика удаления объектов из S3 должна быть реализована здесь
    }

    @Override
    public void clearAndAddValue(long patientId, SensorData sensorData) {
        clearValues(patientId);
        addValue(sensorData);
        logger.log("info", "Cleared previous data and added new value for patient: " + patientId);
    }
}
