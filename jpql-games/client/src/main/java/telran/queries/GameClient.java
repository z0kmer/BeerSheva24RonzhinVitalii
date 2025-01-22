package telran.queries;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import telran.queries.services.BullsCowsService;
import telran.queries.services.BullsCowsServiceClientProxy;
import telran.queries.view.MainMenu;

public class GameClient {
    public void start(String host, int port) {
        try (Socket socket = new Socket(host, port);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            BullsCowsService service = new BullsCowsServiceClientProxy(oos, ois);
            new MainMenu(service, new Scanner(System.in)).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
