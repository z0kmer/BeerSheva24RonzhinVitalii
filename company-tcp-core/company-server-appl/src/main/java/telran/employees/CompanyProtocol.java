package telran.employees;

import org.json.JSONArray;
import org.json.JSONObject;

import telran.net.Protocol;
import telran.net.Request;
import telran.net.Response;
import telran.net.ResponseCode;

public class CompanyProtocol implements Protocol {
    Company company;

    public CompanyProtocol(Company company) {
        this.company = company;
    }

    @Override
    public Response getResponse(Request request) {
        switch (request.requestType()) {
            case "addEmployee":
                company.addEmployee(new Employee(new JSONObject(request.requestData())));
                return new Response(ResponseCode.OK, "Employee added successfully");

            case "getDepartmentBudget":
                int budget = company.getDepartmentBudget(request.requestData());
                return new Response(ResponseCode.OK, String.valueOf(budget));

            case "getDepartments":
                String[] departments = company.getDepartments();
                return new Response(ResponseCode.OK, new JSONArray(departments).toString());

            case "getEmployee":
                Employee employee = company.getEmployee(Long.parseLong(request.requestData()));
                return new Response(ResponseCode.OK, employee.toString());

            case "getManagersWithMostFactor":
                Manager[] managers = company.getManagersWithMostFactor();
                return new Response(ResponseCode.OK, new JSONArray(managers).toString());

            case "removeEmployee":
                Employee removedEmployee = company.removeEmployee(Long.parseLong(request.requestData()));
                return new Response(ResponseCode.OK, removedEmployee.toString());

            default:
                return new Response(ResponseCode.WRONG_TYPE, "Invalid request type");
        }
    }
}
