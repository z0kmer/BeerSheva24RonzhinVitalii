package telran.monitoring;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.models.dynamodb.AttributeValue;

import telran.monitoring.api.JumpPulseData;
import telran.monitoring.api.LatestValuesSaver;
import telran.monitoring.api.SensorData;
import telran.monitoring.logging.Logger;
import telran.monitoring.logging.LoggerStandard;

public class App {
    Logger logger = new LoggerStandard("jump-pulse-recognizer");
    LatestValuesSaver saver = new LatestValuesSaverMap();
    MiddlewareDataStream<JumpPulseData> jumpStream;

    float factor = Float.parseFloat(System.getenv().getOrDefault("FACTOR", "0.5"));

    public App() {
        try {
            jumpStream = MiddlewareDataStreamFactory.getStream("telran.monitoring.DynamoDbStreamJumpPulseData", "jump-pulse-values");
        } catch (Exception e) {
            logger.log("severe", "Failed to initialize jumpStream: " + e.toString());
        }
    }

    public void handleRequest(final DynamodbEvent event, final Context context) {
        logger.log("info", "Event received: " + event);
        event.getRecords().forEach(r -> {
            try {
                Map<String, AttributeValue> map = r.getDynamodb().getNewImage();
                logger.log("info", "Record: " + map);

                AttributeValue patientIdAttr = map.get("patientId");
                AttributeValue valueAttr = map.get("value");
                AttributeValue timestampAttr = map.get("timestamp");

                if (patientIdAttr != null && valueAttr != null && timestampAttr != null) {
                    long patientId = Long.parseLong(patientIdAttr.getN());
                    int value = Integer.parseInt(valueAttr.getN());
                    long timestamp = Long.parseLong(timestampAttr.getN());

                    SensorData sensorData = new SensorData(patientId, value, timestamp);
                    logger.log("info", "SensorData: " + sensorData);

                    recognizeAndHandleJump(sensorData);
                } else {
                    logger.log("warning", "One of the attributes is null: patientId=" + patientIdAttr + ", value=" + valueAttr + ", timestamp=" + timestampAttr);
                }
            } catch (Exception e) {
                logger.log("severe", "Error processing record: " + e.toString());
            }
        });
    }

    private void recognizeAndHandleJump(SensorData sensorData) {
        long patientId = sensorData.patientId();
        SensorData lastValue = saver.getLastValue(patientId);
        if (lastValue != null) {
            float diff = Math.abs(lastValue.value() - sensorData.value()) / (float) lastValue.value();
            logger.log("info", "Last value: " + lastValue.value() + " | Current value: " + sensorData.value() + " | Diff: " + diff + " | Factor: " + factor);
            if (diff >= factor) {
                JumpPulseData jumpData = new JumpPulseData(patientId, lastValue.value(), sensorData.value(), sensorData.timestamp());
                logger.log("info", "Jump detected: " + jumpData);
                try {
                    jumpStream.publish(jumpData);
                    logger.log("info", "Jump data published to DynamoDB: " + jumpData);
                } catch (Exception e) {
                    logger.log("severe", "Failed to publish jump data: " + e.toString());
                }
            }
        } else {
            logger.log("info", "No previous value found for patientId: " + patientId);
        }
        saver.clearAndAddValue(patientId, sensorData);
    }
}
