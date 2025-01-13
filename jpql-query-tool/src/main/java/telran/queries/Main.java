package telran.queries;

import java.util.HashMap;

import org.hibernate.jpa.HibernatePersistenceProvider;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.spi.PersistenceUnitInfo;
import telran.queries.config.BullsCowsPersistenceUnitInfo;
import telran.queries.repositories.BullsCowsRepositoryImpl;
import telran.queries.services.BullsCowsServiceImpl;
import telran.queries.view.MainMenu;
import telran.view.InputOutput;
import telran.view.StandardInputOutput;

public class Main {
    static EntityManager em;
    static InputOutput io = new StandardInputOutput();

    public static void main(String[] args) {
        EntityManagerFactory emf = createEntityManagerFactory();
        em = emf.createEntityManager();
        BullsCowsRepositoryImpl repository = new BullsCowsRepositoryImpl(emf);
        BullsCowsServiceImpl service = new BullsCowsServiceImpl(repository);

        MainMenu menu = new MainMenu(service, io);
        menu.run();
    }

    private static EntityManagerFactory createEntityManagerFactory() {
        HashMap<String, Object> hibernateProperties = new HashMap<>();
        hibernateProperties.put("hibernate.hbm2ddl.auto", "update");
        PersistenceUnitInfo persistenceUnit = new BullsCowsPersistenceUnitInfo();
        HibernatePersistenceProvider hibernatePersistenceProvider = new HibernatePersistenceProvider();
        return hibernatePersistenceProvider.createContainerEntityManagerFactory(persistenceUnit, hibernateProperties);
    }
}
