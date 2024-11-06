package telran.appl.net;

import telran.net.Protocol;
import telran.net.Request;
import telran.net.Response;
import telran.net.ResponseCode;

public class ReverseLengthProtocol implements Protocol {
    @Override
    public Response getResponse(Request request) {
        String data = request.requestData();
        String responseData;
        
        switch (request.requestType().toLowerCase()) {
            case "reverse":
                responseData = new StringBuilder(data).reverse().toString();
                break;
            case "length":
                responseData = String.valueOf(data.length());
                break;
            default:
                return new Response(ResponseCode.WRONG_TYPE, "Unsupported request type");
        }
        
        return new Response(ResponseCode.OK, responseData);
    }
}
