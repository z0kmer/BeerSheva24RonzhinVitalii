package telran.employees.db.jpa;

import org.json.JSONObject;

import telran.employees.Employee;
import telran.employees.Manager;

public class EmployeesMapper {
    private static final String PACKAGE = "telran.employees.";
    private static final String CLASS_NAME = "className";

    public static Employee toEmployeeDtoFromEntity(EmployeeEntity entity) {
        String entityClassName = entity.getClass().getSimpleName();
        String dtoClassName = PACKAGE + entityClassName.replaceAll("Entity", "");
        JSONObject jsonObj = new JSONObject();
        jsonObj.put(CLASS_NAME, dtoClassName);
        entity.toJsonObject(jsonObj);
        return Employee.getEmployeeFromJSON(jsonObj.toString());
    }

    public static EmployeeEntity toEmployeeEntityFromDto(Employee empl) {
        if (empl instanceof Manager) {
            ManagerEntity managerEntity = new ManagerEntity();
            managerEntity.fromEmployeeDto(empl);
            return managerEntity;
        }
        // Добавляем другие типы сотрудников по аналогии
        EmployeeEntity entity = new EmployeeEntity();
        entity.fromEmployeeDto(empl);
        return entity;
    }
}
