package telran.queries;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitInfo;

import org.hibernate.jpa.HibernatePersistenceProvider;

import telran.queries.config.BullsCowsPersistenceUnitInfo;
import telran.queries.repositories.BullsCowsRepositoryImpl;
import telran.queries.services.BullsCowsServiceImpl;

public class GameServer {
    private final BullsCowsServiceImpl service;
    private final ServerSocket serverSocket;

    public GameServer(BullsCowsServiceImpl service, int port) throws IOException {
        this.service = service;
        this.serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(new ClientHandler(socket, service)).start();
        }
    }

    public static void main(String[] args) throws IOException {
        EntityManagerFactory emf = createEntityManagerFactory();
        BullsCowsRepositoryImpl repository = new BullsCowsRepositoryImpl(emf);
        BullsCowsServiceImpl service = new BullsCowsServiceImpl(emf, repository);
        GameServer server = new GameServer(service, 8080);
        server.start();
    }

    private static EntityManagerFactory createEntityManagerFactory() {
        HashMap<String, Object> hibernateProperties = new HashMap<>();
        hibernateProperties.put("hibernate.hbm2ddl.auto", "update");
        PersistenceUnitInfo persistenceUnit = new BullsCowsPersistenceUnitInfo();
        HibernatePersistenceProvider hibernatePersistenceProvider = new HibernatePersistenceProvider();
        return hibernatePersistenceProvider.createContainerEntityManagerFactory(persistenceUnit, hibernateProperties);
    }
}
