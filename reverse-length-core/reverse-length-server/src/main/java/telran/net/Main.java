package telran.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private static final int PORT = 4000;

    public static void main(String[] args) throws Exception {
        //Server application
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
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2);
                String type = parts[0];
                String content = parts[1];
                String response = "";
                if ("reverse".equalsIgnoreCase(type)) {
                    response = new StringBuilder(content).reverse().toString();
                } else if ("length".equalsIgnoreCase(type)) {
                    response = String.valueOf(content.length());
                }
                writer.println(response);
            }
        } catch (Exception e) {
            System.out.println("Client closed connection abnormally");
        }
    }
}
