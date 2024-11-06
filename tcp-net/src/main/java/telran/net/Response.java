package telran.net;

import org.json.JSONObject;

import static telran.net.TcpConfigurationProperties.RESPONSE_CODE_FIELD;
import static telran.net.TcpConfigurationProperties.RESPONSE_DATA_FIELD;
public record Response(ResponseCode responseCode, String responseData) {
@Override
    public String toString(){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put(RESPONSE_CODE_FIELD, responseCode);
        jsonObj.put(RESPONSE_DATA_FIELD, responseData);
        return jsonObj.toString();
    }
}