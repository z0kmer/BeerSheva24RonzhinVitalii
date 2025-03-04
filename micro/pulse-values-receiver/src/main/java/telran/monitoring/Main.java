package telran.monitoring;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import org.json.JSONObject;

import telran.monitoring.logging.Logger;
import telran.monitoring.logging.LoggerStandard;

public class Main {

    private static final int PORT = 5000;
    private static final int MAX_SIZE = 1500;
    private static final int WARNING_THRESHOLD = 220;
    private static final int SEVERE_THRESHOLD = 230;
    static Logger logger = new LoggerStandard("receiver");

    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(PORT);
        byte[] buffer = new byte[MAX_SIZE];

        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            String data = new String(packet.getData(), 0, packet.getLength());

            logger.log("finest", data);

            JSONObject jsonObj = new JSONObject(data);
            int value = jsonObj.getInt("value");
            
            if (value > WARNING_THRESHOLD) {
                logger.log("warning", String.format("Received high pulse value: %d", value));
            }
            if (value > SEVERE_THRESHOLD) {
                logger.log("severe", String.format("Received critical pulse value: %d", value));
            }

            socket.send(packet);
        }
    }
}
