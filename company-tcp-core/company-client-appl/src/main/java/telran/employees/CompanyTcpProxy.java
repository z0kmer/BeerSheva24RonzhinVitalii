package telran.employees;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import telran.net.TcpClient;

public class CompanyTcpProxy implements Company{
    TcpClient tcpClient;
    public CompanyTcpProxy (TcpClient tcpClient) {
        this.tcpClient = tcpClient;
    }
    @Override
    public Iterator<Employee> iterator() {
        String jsonStr = tcpClient.sendAndReceive("iterator", "");
        JSONArray jsonArray = new JSONArray(jsonStr);
        return jsonArray.toList().stream()
        .map(obj -> new Employee(new JSONObject(obj.toString())))
        .iterator();
    }

    @Override
    public void addEmployee(Employee empl) {
        tcpClient.sendAndReceive("addEmployee", empl.toString());
    }

    @Override
    public int getDepartmentBudget(String arg0) {
        String response = tcpClient.sendAndReceive("getDepartmentBudget", department);
        return Integer.parseInt(response);
    }

    @Override
    public String[] getDepartments() {
        String jsonStr = tcpClient.sendAndReceive("getDepartments", "");
        JSONArray jsonArray = new JSONArray(jsonStr) ;
        String[]res = jsonArray.toList().toArray(String[]::new);
        return res;
    }

    @Override
    public Employee getEmployee(long arg0) {
        String response = tcpClient.sendAndReceive("getEmployee", String.valueOf(id));
        return new Employee(new JSONObject(response));
    }

    @Override
    public Manager[] getManagersWithMostFactor() {
        String jsonStr = tcpClient.sendAndReceive("getManagersWithMostFactor", "");
        JSONArray jsonArray = new JSONArray(jsonStr); Manager[] managers = new Manager[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            managers[i] = new Manager(jsonArray.getJSONObject(i));
        }
        return managers;
    }

    @Override
    public Employee removeEmployee(long arg0) {
        String response = tcpClient.sendAndReceive("removeEmployee", String.valueOf(id));
        return new Employee(new JSONObject(response));
    }

}