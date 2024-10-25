package telran.employees;
import telran.io.Persistable;
public class Main {
    public static void main(String[] args) {
       Company company = new CompanyImpl();
       if (company instanceof Persistable persistable) {
        persistable.restoreFromFile("employees.data");
       }
       Item[] items = CompanyItems.getItems(company);
       Menu menu = new Menu("Company Application", items);
       menu.perform(new StandardInputOutput());
    }
}