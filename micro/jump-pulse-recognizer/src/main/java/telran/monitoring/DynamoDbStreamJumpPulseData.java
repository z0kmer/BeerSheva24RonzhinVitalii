package telran.monitoring;

import java.util.HashMap;
import java.util.Map;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import telran.monitoring.api.JumpPulseData;

public class DynamoDbStreamJumpPulseData extends DynamoDbStream<JumpPulseData> {

    public DynamoDbStreamJumpPulseData(String table) {
        super(table);
    }

    @Override
    Map<String, AttributeValue> getMap(JumpPulseData jumpPulseData) {
        HashMap<String, AttributeValue> map = new HashMap<>() {{
            put("patientId", AttributeValue.builder().n(jumpPulseData.patientId() + "").build());
            put("oldValue", AttributeValue.builder().n(jumpPulseData.oldValue() + "").build());
            put("newValue", AttributeValue.builder().n(jumpPulseData.newValue() + "").build());
            put("timestamp", AttributeValue.builder().n(jumpPulseData.timestamp() + "").build());
        }};
        return map;
    }
}
