package telran.employees;

import telran.net.TcpServer;

public class Main {
    public static void main(String[] args) {
        Company company = new CompanyImpl(); // Реальная реализация интерфейса Company
        CompanyProtocol protocol = new CompanyProtocol(company);
        TcpServer server = new TcpServer(protocol, 4000);
        new Thread(server).start();
        System.out.println("Company TCP Server is running on port 4000");
    }
}
