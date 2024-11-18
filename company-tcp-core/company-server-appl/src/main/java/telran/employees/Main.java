package telran.employees;

import java.util.concurrent.TimeUnit;

import telran.io.Persistable;
import telran.net.TcpServer;

public class Main {
    private static final String FILE_NAME = "employees.data";
    private static final int PORT = 4000;
    private static final long SAVE_INTERVAL = 1; // интервал в часах

    public static void main(String[] args) {
        Company company = new CompanyImpl();
        if (company instanceof Persistable persistable) {
            persistable.restoreFromFile(FILE_NAME);
            EmployeesDataSaver dataSaver = new EmployeesDataSaver(persistable, SAVE_INTERVAL, TimeUnit.HOURS);
            dataSaver.start();

            Runtime.getRuntime().addShutdownHook(new Thread(dataSaver::stop));
        }
        TcpServer tcpServer = new TcpServer(new CompanyProtocol(company), PORT);
        tcpServer.run();
    }
}
