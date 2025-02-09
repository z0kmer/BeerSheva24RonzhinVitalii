package telran.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
record Point(String id, int x) {
    @Override
    public String toString(){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("id", id);
        jsonObj.put("x", x);
        return jsonObj.toString();
    }
}
record Response(int code, String text) {

}
public class Main {
    static HttpClient client = HttpClient.newHttpClient();

    public static void main(String[] args) throws IOException, InterruptedException {
    //    sendAddPointRequest(new Point("2", 10));
       sendGetPointRequest("10");

    }

    private static void sendAddPointRequest(Point point) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
           .uri(URI.create("http://localhost:3500/addPoint"))
           .POST(BodyPublishers.ofString(point.toString())).build();
               HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
              System.out.println(new Response(response.statusCode(), response.body()));
    }
    private static void sendGetPointRequest(String pointId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
           .uri(URI.create("http://localhost:3500/getPoint"))
           .POST(BodyPublishers.ofString(pointId)).build();
               HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
              System.out.println(new Response(response.statusCode(), response.body()));
    }
}