package telran.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private static final int PORT = 4000;

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(PORT);
        while (true) {
            Socket socket = serverSocket.accept();
            runSession(socket);
        }
    }

    private static void runSession(Socket socket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream writer = new PrintStream(socket.getOutputStream())) {
            String line = "";
            while((line = reader.readLine()) != null){
                writer.printf("Echo server on %s , port: %d sends back %s\n", socket.getLocalAddress().getHostAddress(),
                socket.getLocalPort(), line);
            }
        } catch (Exception e) {
           System.out.println("client closed connection abnormally");
        }
    }
}