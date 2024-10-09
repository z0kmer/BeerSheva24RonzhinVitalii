package telran.employees;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

import telran.io.Persistable;

public class CompanyImpl implements Company, Persistable{
   private TreeMap<Long, Employee> employees = new TreeMap<>();
   private HashMap<String, List<Employee>> employeesDepartment = new HashMap<>();
   private TreeMap<Float, List<Manager>> managersFactor = new TreeMap<>();
private class CompanyIterator implements Iterator<Employee> {
    Iterator<Employee> iterator = employees.values().iterator();
    Employee lastIterated;
    @Override
    public boolean hasNext() {
       return iterator.hasNext();
    }

    @Override
    public Employee next() {
       lastIterated = iterator.next();
       return lastIterated;
    }
    @Override
    public void remove() {
       iterator.remove();
       removeFromIndexMaps(lastIterated);
    }
}
    @Override
    public Iterator<Employee> iterator() {
       return new CompanyIterator();
    }

    @Override
    public void addEmployee(Employee empl) {
        long id = empl.getId();
        if (employees.putIfAbsent(id, empl) != null) {
            throw new IllegalStateException("Already exists employee " + id);
        }
        addIndexMaps(empl);
    }

    private void addIndexMaps(Employee empl) {
       employeesDepartment.computeIfAbsent(empl.getDepartment(), k -> new ArrayList<>()).add(empl);
       if (empl instanceof Manager manager) {
            managersFactor.computeIfAbsent(manager.getFactor(), k -> new ArrayList<>()).add(manager);
       }
    }

    

    @Override
    public Employee getEmployee(long id) {
        return employees.get(id);
    }

    @Override
    public Employee removeEmployee(long id) {
        Employee empl = employees.remove(id);
        if(empl == null) {
            throw new NoSuchElementException("Not found employee " + id);
        }
        removeFromIndexMaps(empl);
        return empl;
    }


    private void removeFromIndexMaps(Employee empl) {
        removeIndexMap(empl.getDepartment(), employeesDepartment, empl);
        if (empl instanceof Manager manager) {
            removeIndexMap(manager.getFactor(), managersFactor, manager);
        }
    }

    private <K, V extends Employee> void removeIndexMap(K key, Map<K, List<V>> map, V empl) {
        List<V> list = map.get(key);
        list.remove(empl);
        if (list.isEmpty()) {
            map.remove(key);
        }
    }

    @Override
    public int getDepartmentBudget(String department) {
        return employeesDepartment.getOrDefault(department, Collections.emptyList())
        .stream().mapToInt(Employee::computeSalary).sum();
    }

    @Override
    public String[] getDepartments() {
        return employeesDepartment.keySet().stream().sorted().toArray(String[]::new);
    }

    @Override
    public Manager[] getManagersWithMostFactor() {
        Manager [] res = new Manager[0];
        if (!managersFactor.isEmpty()) {
            res = managersFactor.lastEntry().getValue().toArray(res);
        }
        return res;
    }

    @Override
    public void saveToFile(String fileName) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(employees);
            out.writeObject(employeesDepartment);
            out.writeObject(managersFactor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void restoreFromFile(String fileName) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            TreeMap<Long, Employee> tempEmployees = (TreeMap<Long, Employee>) in.readObject();
            HashMap<String, List<Employee>> tempEmployeesDepartment = (HashMap<String, List<Employee>>) in.readObject();
            TreeMap<Float, List<Manager>> tempManagersFactor = (TreeMap<Float, List<Manager>>) in.readObject();
            
            employees.clear();
            employeesDepartment.clear();
            managersFactor.clear();
            
            for (Employee empl : tempEmployees.values()) {
                employees.put(empl.getId(), empl);
                employeesDepartment.computeIfAbsent(empl.getDepartment(), k -> new ArrayList<>()).add(empl);
                if (empl instanceof Manager manager) {
                    managersFactor.computeIfAbsent(manager.getFactor(), k -> new ArrayList<>()).add(manager);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
}