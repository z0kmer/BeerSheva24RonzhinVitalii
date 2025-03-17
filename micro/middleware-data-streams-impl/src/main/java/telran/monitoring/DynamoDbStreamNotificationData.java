package telran.monitoring;

import java.util.HashMap;
import java.util.Map;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class DynamoDbStreamNotificationData extends DynamoDbStream<NotificationData>{

    public DynamoDbStreamNotificationData(String table) {
            super(table);
            
        }
    
        @Override
    Map<String, AttributeValue> getMap(NotificationData notificationData) {
        HashMap<String, AttributeValue> map = new HashMap<>(){{
            put("patientId",AttributeValue.builder().n(notificationData.patientId() + "").build());
            put("email",AttributeValue.builder().s(notificationData.email() + "").build());
            put("timestamp",AttributeValue.builder().n(notificationData.timestamp() + "").build());
            put("notificationText",AttributeValue.builder().s(notificationData.notificationText() + "").build());
            
        }};
        return map;
    }

}