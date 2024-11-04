package telran.net;

public class Main {
    static ReverseLengthClient reverseLengthClient;

    public static void main(String[] args) {
      //client application
        Item[] items = {
            Item.of("Start session", Main::startSession),
            Item.of("Exit", Main::exit, true)
        };
        Menu menu = new Menu("Reverse-Length Application", items);
        menu.perform(new StandardInputOutput());
    }

    static void startSession(InputOutput io) {
        String host = io.readString("Enter hostname");
        int port = io.readNumberRange("Enter port", "Wrong port", 3000, 50000).intValue();
        if (reverseLengthClient != null) {
            reverseLengthClient.close();
        }
        reverseLengthClient = new ReverseLengthClient(host, port);
        Menu menu = new Menu("Run Session",
                Item.of("Send reverse request", Main::reverseRequest),
                Item.of("Send length request", Main::lengthRequest),
                Item.ofExit());
        menu.perform(io);
    }

    static void reverseRequest(InputOutput io) {
        String string = io.readString("Enter any string");
        String response = reverseLengthClient.sendAndReceive("reverse", string);
        io.writeLine(response);
    }

    static void lengthRequest(InputOutput io) {
        String string = io.readString("Enter any string");
        String response = reverseLengthClient.sendAndReceive("length", string);
        io.writeLine(response);
    }

    static void exit(InputOutput io) {
        if (reverseLengthClient != null) {
            reverseLengthClient.close();
        }
    }
}
