package telran.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TcpServer implements Runnable {
    Protocol protocol;
    int port;
    private volatile boolean isShutdown = false;
    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    public TcpServer(Protocol protocol, int port) {
        this.protocol = protocol;
        this.port = port;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setSoTimeout(500);
            System.out.println("Server is listening on port " + port);
            while (!executor.isShutdown()) {
                try {
                    Socket socket = serverSocket.accept();
                    var session = new TcpClientServerSession(protocol, socket);
                    executor.execute(session);
                } catch (SocketTimeoutException e) {
                    // timeout reached + check for shutdown signal
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            shutdownAndAwaitTermination();
        }
    }

    public void shutdown() {
        isShutdown = true;
        executor.shutdownNow();
    }

    private void shutdownAndAwaitTermination() {
        executor.shutdown(); // disable new tasks from being submitted
        try {
            // wait some time for existing tasks to terminate
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow(); // Cancel currently executing tasks
                // wait some time for tasks to respond to being cancelled
                if (!executor.awaitTermination(60, TimeUnit.SECONDS))
                    System.err.println("Executor did not terminate");
            }
        } catch (InterruptedException ie) {
            // (re-)cancel if current thread also interrupted
            executor.shutdownNow();
            // preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }
}
