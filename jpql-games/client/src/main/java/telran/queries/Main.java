package telran.queries;

import telran.view.InputOutput;
import telran.view.StandardInputOutput;

public class Main {
    static InputOutput io = new StandardInputOutput();

    public static void main(String[] args) {
        GameClient client = new GameClient(ClientConfig.SERVER_URL.split("//")[1].split(":")[0],
                Integer.parseInt(ClientConfig.SERVER_URL.split(":")[2]));
        client.start();
    }
}