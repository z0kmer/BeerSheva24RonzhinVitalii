package telran.employees.db;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import telran.employees.Company;
import telran.employees.Employee;
import telran.employees.Manager;
import telran.employees.db.jpa.EmployeeEntity;
import telran.employees.db.jpa.EmployeesMapper;
import telran.employees.db.jpa.ManagerEntity;

public class CompanyDbImpl implements Company {
    private CompanyRepository repository;
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock readLock = lock.readLock();
    private Lock writeLock = lock.writeLock();

    public CompanyDbImpl(CompanyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Iterator<Employee> iterator() {
        try {
            readLock.lock();
            return repository.getEmployees().iterator();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void addEmployee(Employee empl) {
        try {
            writeLock.lock();
            EmployeeEntity entity = EmployeesMapper.toEmployeeEntityFromDto(empl);
            repository.save(entity); // Сохраняю в базу данных
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public Employee getEmployee(long id) {
        try {
            readLock.lock();
            EmployeeEntity entity = repository.findById(id).orElse(null);
            return entity == null ? null : EmployeesMapper.toEmployeeDtoFromEntity(entity);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Employee removeEmployee(long id) {
        try {
            writeLock.lock();
            EmployeeEntity entity = repository.findById(id).orElse(null);
            if (entity != null) {
                repository.delete(entity);
                return EmployeesMapper.toEmployeeDtoFromEntity(entity);
            }
            return null;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public int getDepartmentBudget(String department) {
        try {
            readLock.lock();
            List<EmployeeEntity> employees = repository.findByDepartment(department);
            return employees.stream().mapToInt(EmployeeEntity::computeSalary).sum();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public String[] getDepartments() {
        try {
            readLock.lock();
            return repository.findAllDepartments();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Manager[] getManagersWithMostFactor() {
        try {
            readLock.lock();
            List<ManagerEntity> managers = repository.findManagersWithMostFactor();
            return managers.toArray(new Manager[0]);
        } finally {
            readLock.unlock();
        }
    }
}
