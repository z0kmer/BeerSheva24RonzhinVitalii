package telran.net;

import org.json.JSONObject;

import static telran.net.TcpConfigurationProperties.REQUEST_DATA_FIELD;
import static telran.net.TcpConfigurationProperties.REQUEST_TYPE_FIELD;
public record Request(String requestType, String requestData) {
    @Override
    public String toString(){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put(REQUEST_TYPE_FIELD, requestType);
        jsonObj.put(REQUEST_DATA_FIELD, requestData);
        return jsonObj.toString();
    }
}