package telran.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

public class TcpClientServerSession implements Runnable {
    Protocol protocol;
    Socket socket;
    private static volatile boolean shutdownRequested = false;
    private static final int MAX_ERRORS = 5;
    private static final int MAX_REQUESTS_PER_SECOND = 10;
    private AtomicInteger errorCount = new AtomicInteger(0);
    private AtomicInteger requestCount = new AtomicInteger(0);
    private Instant startTime = Instant.now();

    public TcpClientServerSession(Protocol protocol, Socket socket) {
        this.protocol = protocol;
        this.socket = socket;
        try {
            socket.setSoTimeout(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        //FIXME add SocketTimeoutException handler for both graceful shutdown and DoS attacks prevention
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintStream writer = new PrintStream(socket.getOutputStream())) {
            String request;
            while (!shutdownRequested && (request = reader.readLine()) != null) {
                if (requestCount.incrementAndGet() > MAX_REQUESTS_PER_SECOND && Instant.now().isBefore(startTime.plusSeconds(1))) {
                    socket.close();
                    break;
                }
                startTime = Instant.now();
                String response = protocol.getResponseWithJSON(request);
                writer.println(response);
                if (!response.contains("Ok")) {
                    if (errorCount.incrementAndGet() >= MAX_ERRORS) {
                        socket.close();
                        break;
                    }
                }
            }
            socket.close();
        } catch (SocketTimeoutException e) {
            if (shutdownRequested) {
                try {
                    socket.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void requestShutdown() {
        shutdownRequested = true;
    }
}