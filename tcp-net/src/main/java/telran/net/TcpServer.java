package telran.net;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpServer implements Runnable {
    Protocol protocol;
    int port;
    private ExecutorService executorService;
    private static volatile boolean shutdownRequested = false;

    public TcpServer(Protocol protocol, int port) {
        this.protocol = protocol;
        this.port = port;
        this.executorService = Executors.newFixedThreadPool(10); // Ограниченный пул потоков
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on the port " + port);
            while (!shutdownRequested) {
                try {
                    Socket socket = serverSocket.accept();
                    var session = new TcpClientServerSession(protocol, socket);
                    executorService.submit(session);
                } catch (Exception e) {
                    if (shutdownRequested) break;
                }
            }
            executorService.shutdownNow(); // Остановка всех потоков выключая
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void shutdown() {
        //In the ExecutorService framework to provide shutdownNow (to ignore all not processing client sessions)
        shutdownRequested = true;
        TcpClientServerSession.requestShutdown();
    }
}