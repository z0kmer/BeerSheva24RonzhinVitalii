package telran.monitoring;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;
import java.util.stream.IntStream;

import telran.monitoring.api.SensorData;
import telran.monitoring.logging.Logger;
import telran.monitoring.logging.LoggerStandard;

public class Main {
    static Logger logger = new LoggerStandard("imitator");
    static final int MIN_PULSE_VALUE = 40;
    static final int MAX_PULSE_VALUE = 240;
    static final long TIMEOUT_SEND = 500;
    static final int TIMEOUT_RESPONSE = 1000;
    static final String DEFAULT_HOST = "localhost";
    static final int DEFAULT_PORT = 5000;
    static final int DEFAULT_N_PATIENTS = 10;
    static final int DEFAULT_N_PACKETS = 1000;
    static final double JUMP_PROB = 0.1; // вероятность прыжка
    static final int MIN_JUMP_PERCENT = 10; // минимальный процент прыжка
    static final int MAX_JUMP_PERCENT = 100; // максимальный процент прыжка
    static final double JUMP_POSITIVE_PROB = 0.7; // вероятность положительного прыжка
    static final int PATIENT_ID_FOR_INFO_LOGGING = 3; // пациент для логгирования
    static DatagramSocket socket = null;

    public static void main(String[] args) throws Exception {
        socket = new DatagramSocket();
        socket.setSoTimeout(TIMEOUT_RESPONSE);
        IntStream.rangeClosed(1, DEFAULT_N_PACKETS).forEach(Main::send);
    }

    static void send(int i) {
        SensorData sensor = getRandomSensorData(i);
        String jsonStr = sensor.toString();
        try {
            udpSend(jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void udpSend(String jsonStr) throws Exception {
        logger.log("finest", String.format("data to be sent is %s", jsonStr));
        byte[] bufferSend = jsonStr.getBytes();
        DatagramPacket packet = new DatagramPacket(bufferSend, bufferSend.length,
                InetAddress.getByName(DEFAULT_HOST), DEFAULT_PORT);
        socket.send(packet);
        socket.receive(packet);
        if (!jsonStr.equals(new String(packet.getData()))) {
            throw new Exception("received packet doesn't equal the sent one");
        }
    }

    private static SensorData getRandomSensorData(int i) {
        long patientId = getRandomNumber(1, DEFAULT_N_PATIENTS);
        int value = getRandomNumber(MIN_PULSE_VALUE, MAX_PULSE_VALUE);
    
        if (Math.random() < JUMP_PROB) {
            int jumpPercent = getRandomNumber(MIN_JUMP_PERCENT, MAX_JUMP_PERCENT);
            if (Math.random() < JUMP_POSITIVE_PROB) {
                value += value * jumpPercent / 100;
            } else {
                value -= value * jumpPercent / 100;
            }
            logger.log("debug", String.format("Jumped value for patientId %d: %d", patientId, value));
        }
    
        if (value < MIN_PULSE_VALUE) {
            value = MIN_PULSE_VALUE;
        }
        if (value > MAX_PULSE_VALUE) {
            value = MAX_PULSE_VALUE;
        }
    
        long timestamp = System.currentTimeMillis();
        SensorData res = new SensorData(patientId, value, timestamp);
        if (patientId == PATIENT_ID_FOR_INFO_LOGGING) {
            logger.log("info", res.toString());
        }
        return res;
    }
    
    private static int getRandomNumber(int minValue, int maxValue) {
        return new Random().nextInt((maxValue - minValue) + 1) + minValue;
    }
    
}
