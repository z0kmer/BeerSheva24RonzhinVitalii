package telran.employees;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationProperties {
    public static final String MANAGER = "Manager";
    public static final String EMPLOYEE = "Employee";
    public static final String WAGE_EMPLOYEE = "WageEmployee";
    public static final String SALES_PERSON = "SalesPerson";
    public static final String QA = "QA";
    public static final String DEVELOPMENT = "Development";
    public static final String DEV_OPS = "DevOps";
    public static final String SALES = "Sales";
    
    private static Map<String, Map<String, Integer>> mapGen = new HashMap<>();
    static {
        Map<String, Integer> qaConfigMap = new HashMap<>();
        Map<String, Integer> devConfigMap = new HashMap<>();
        Map<String, Integer> devOpsConfigMap = new HashMap<>();
        Map<String, Integer> salesConfigMap = new HashMap<>();
        qaConfigMap.put(MANAGER, 1);
        qaConfigMap.put(EMPLOYEE, 2);
        qaConfigMap.put(WAGE_EMPLOYEE, 10);
        devConfigMap.put(MANAGER, 1);
        devConfigMap.put(WAGE_EMPLOYEE, 30);
        devOpsConfigMap.put(MANAGER, 1);
        devOpsConfigMap.put(EMPLOYEE, 5);
        salesConfigMap.put(MANAGER, 1);
        salesConfigMap.put(SALES_PERSON, 3);
        mapGen.put(QA, qaConfigMap);
        mapGen.put(DEVELOPMENT, devConfigMap);
        mapGen.put(DEV_OPS, devOpsConfigMap);
        mapGen.put(SALES, salesConfigMap);

    }
    public static Map<String, Map<String, Integer>> getGenerationMap() {
        return mapGen;
    }
}