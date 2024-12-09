package telran.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.time.Instant;

public class TcpClientServerSession implements Runnable {
    private static final int MAX_ERRORS_IN_ROW = 5;
    private static final int MAX_REQUESTS_PER_SECOND = 10;
    private Protocol protocol;
    private Socket socket;
    private int errorCount = 0;
    private long startTime;
    private int requestCount = 0;

    public TcpClientServerSession(Protocol protocol, Socket socket) {
        this.protocol = protocol;
        this.socket = socket;
        this.startTime = Instant.now().getEpochSecond();
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintStream writer = new PrintStream(socket.getOutputStream())) {
             
            socket.setSoTimeout(500); // set timeout for the socket operations
            String request;
            while ((request = reader.readLine()) != null) {
                String response = protocol.getResponseWithJSON(request);
                writer.println(response);
                updateRequestCount();

                if (shouldTerminateSession()) {
                    break;
                }
            }
        } catch (SocketTimeoutException e) {
            // timeout reached, check if shutdown requested or handle DoS
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    private void updateRequestCount() {
        long currentTime = Instant.now().getEpochSecond();
        if (currentTime != startTime) {
            startTime = currentTime;
            requestCount = 0;
        }
        requestCount++;
    }

    private boolean shouldTerminateSession() {
        return requestCount > MAX_REQUESTS_PER_SECOND || errorCount >= MAX_ERRORS_IN_ROW;
    }
}
