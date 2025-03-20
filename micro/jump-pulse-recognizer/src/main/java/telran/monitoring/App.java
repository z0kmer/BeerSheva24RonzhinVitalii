package telran.monitoring;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent.DynamodbStreamRecord;
import com.amazonaws.services.lambda.runtime.events.models.dynamodb.AttributeValue;

import telran.monitoring.api.JumpPulseData;
import telran.monitoring.api.LatestValuesSaver;
import telran.monitoring.api.SensorData;
import telran.monitoring.logging.Logger;
import telran.monitoring.logging.LoggerStandard;

public class App {
    private static final String DEFAULT_STREAM_CLASS_NAME = "telran.monitoring.DynamoDbStreamJumpPulseData";
    private static final String DEFAULT_STREAM_NAME = "jump_pulse_values";
    private static final float DEFAULT_JUMP_FACTOR = 0.5f;
    private static final String DEFAULT_LATEST_VALUES_SAVER_CLASS = "telran.monitoring.LatestValuesSaverMap";

    private Map<String, String> env = System.getenv();
    private String streamName = getStreamName();
    private Logger logger = new LoggerStandard(streamName);
    private MiddlewareDataStream<JumpPulseData> dataStream;

    private LatestValuesSaver latestValuesSaver = getLatestValuesSaver();

    @SuppressWarnings("unchecked")
    public App() {
        try {
            dataStream = (MiddlewareDataStream<JumpPulseData>) MiddlewareDataStreamFactory.getStream(getStreamClassName(), getStreamName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void handleRequest(final DynamodbEvent event, final Context context) {
        event.getRecords().forEach(this::sensorDataProcessing);
    }

    private String getStreamName() {
        return env.getOrDefault("STREAM_NAME", DEFAULT_STREAM_NAME);
    }

    private String getStreamClassName() {
        return env.getOrDefault("STREAM_CLASS_NAME", DEFAULT_STREAM_CLASS_NAME);
    }

    private LatestValuesSaver getLatestValuesSaver() {
        String latestValuesSaverClassName = env.getOrDefault("LATEST_VALUES_SAVER_CLASS", DEFAULT_LATEST_VALUES_SAVER_CLASS);
        logger.log("config", "LatestValuesSaver class is " + latestValuesSaverClassName);
        return LatestValuesSaver.getLatestValuesSaver(latestValuesSaverClassName);
    }

    private void sensorDataProcessing(DynamodbStreamRecord record) {
        String eventName = record.getEventName();
        if ("INSERT".equalsIgnoreCase(eventName)) {
            Map<String, AttributeValue> map = record.getDynamodb().getNewImage();
            if (map != null) {
                SensorData sensorData = getSensorData(map);
                logger.log("finest", sensorData.toString());
                JumpPulseData jumpData = jumpRecognition(sensorData);
                if (jumpData != null) {
                    dataStream.publish(jumpData);
                    logger.log("debug", "Published Jump with data: " + jumpData);
                }
            } else {
                logger.log("severe", "No new image found in event");
            }
        } else {
            logger.log("severe", eventName + " not supposed for processing");
        }
    }

    private JumpPulseData jumpRecognition(SensorData sensorData) {
        long patientId = sensorData.patientId();
        SensorData latestSensorData = latestValuesSaver.getLastValue(patientId);
        JumpPulseData jumpDataResult = null;

        int currentValue = sensorData.value();
        int oldValue = latestSensorData != null ? latestSensorData.value() : 0;

        if (oldValue != 0 && isJump(oldValue, currentValue)) {
            jumpDataResult = new JumpPulseData(patientId, oldValue, currentValue, System.currentTimeMillis());
        }

        if (oldValue != currentValue) {
            latestValuesSaver.clearAndAddValue(patientId, sensorData);
        }
        return jumpDataResult;
    }

    private boolean isJump(int oldValue, int currentValue) {
        float factor = getFactor();
        return Math.abs(currentValue - oldValue) / (float) oldValue >= factor;
    }

    private float getFactor() {
        float res = DEFAULT_JUMP_FACTOR;
        String factorStr = env.getOrDefault("JUMP_FACTOR", String.valueOf(DEFAULT_JUMP_FACTOR));
        try {
            res = Float.parseFloat(factorStr);
        } catch (NumberFormatException e) {
            logger.log("severe", "Wrong jump factor env value, default value is taken " + DEFAULT_JUMP_FACTOR);
        }
        return res;
    }

    private SensorData getSensorData(Map<String, AttributeValue> map) {
        long patientId = Long.parseLong(map.get("patientId").getN());
        int value = Integer.parseInt(map.get("value").getN());
        long timestamp = Long.parseLong(map.get("timestamp").getN());
        return new SensorData(patientId, value, timestamp);
    }
}
