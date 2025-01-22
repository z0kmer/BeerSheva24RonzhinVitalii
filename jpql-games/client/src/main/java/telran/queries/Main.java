package telran.queries;

public class Main {
    public static void main(String[] args) {
        GameClient client = new GameClient();
        client.start("localhost", 8080);  // Убедитесь, что здесь используется правильный порт
    }
}
