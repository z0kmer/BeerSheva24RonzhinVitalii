package telran.queries;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import telran.queries.services.BullsCowsServiceClientProxy;
import telran.queries.view.MainMenu;
import telran.view.InputOutput;
import telran.view.StandardInputOutput;

public class GameClient {
    private final String host;
    private final int port;

    public GameClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        try (Socket socket = new Socket(ClientConfig.SERVER_URL.split("//")[1].split(":")[0],
                Integer.parseInt(ClientConfig.SERVER_URL.split(":")[2]));
                OutputStream output = socket.getOutputStream();
                InputStream input = socket.getInputStream();
                ObjectOutputStream oos = new ObjectOutputStream(output);
                ObjectInputStream ois = new ObjectInputStream(input)) {
            InputOutput io = new StandardInputOutput();
            BullsCowsServiceClientProxy serviceProxy = new BullsCowsServiceClientProxy(oos, ois);
            MainMenu mainMenu = new MainMenu(serviceProxy, io);
            mainMenu.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GameClient client = new GameClient(ClientConfig.SERVER_URL.split("//")[1].split(":")[0],
                Integer.parseInt(ClientConfig.SERVER_URL.split(":")[2]));
        client.start();
    }
}
