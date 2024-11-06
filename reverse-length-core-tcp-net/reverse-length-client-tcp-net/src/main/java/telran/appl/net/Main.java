package telran.appl.net;

import telran.net.TcpClient;
import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;
import telran.view.StandardInputOutput;

public class Main {
    static TcpClient tcpClient;

    public static void main(String[] args) {
        Item[] items = {
            Item.of("Start session", Main::startSession),
            Item.of("Exit", Main::exit, true)
        };
        Menu menu = new Menu("Reverse-Length TCP Application", items);
        menu.perform(new StandardInputOutput());
    }

    static void startSession(InputOutput io) {
        String host = io.readString("Enter hostname");
        int port = io.readNumberRange("Enter port", "Wrong port", 3000, 50000).intValue();
        tcpClient = new TcpClient(host, port);
        Menu menu = new Menu("Run Session",
                Item.of("Send reverse request", Main::reverseRequest),
                Item.of("Send length request", Main::lengthRequest),
                Item.ofExit());
        menu.perform(io);
    }

    static void reverseRequest(InputOutput io) {
        String string = io.readString("Enter any string");
        String response = tcpClient.sendAndReceive("reverse", string);
        io.writeLine(response);
    }

    static void lengthRequest(InputOutput io) {
        String string = io.readString("Enter any string");
        String response = tcpClient.sendAndReceive("length", string);
        io.writeLine(response);
    }

    static void exit(InputOutput io) {
        if (tcpClient != null) {
            tcpClient.close();
        }
    }
}
