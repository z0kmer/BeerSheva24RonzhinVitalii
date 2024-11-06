package telran.appl.net;

import telran.net.TcpServer;

public class Main {
    public static void main(String[] args) {
        ReverseLengthProtocol protocol = new ReverseLengthProtocol();
        TcpServer server = new TcpServer(protocol, 4000);
        new Thread(server).start();
        System.out.println("Reverse-Length TCP Server is running on port 4000");
    }
}
