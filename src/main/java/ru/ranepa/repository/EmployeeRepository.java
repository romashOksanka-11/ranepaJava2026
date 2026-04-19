package ru.ranepa.repository;

import ru.ranepa.model.Employee;
import java.util.HashMap;
import java.util.List;

public class EmployeeRepository {
    private final HashMap<Long, Employee> employees = new HashMap<>();

    public void save(Employee employee) {
        long id = employee.getId();
        employees.put(id, employee);
    }

    public List<Employee> findAll() {
        return employees.values()
                .stream()
                .toList();
    }

    public Employee findById(long id) {
        if (!employees.containsKey(id)){
            throw new IllegalArgumentException("Такого сотрудника нет");
        }
        return employees.get(id);
    }

    public void delete(Long id) {
        if (!employees.containsKey(id)) {
            throw new IllegalArgumentException("Такого сотрудника нет");
        }
        employees.remove(id);
    }

    public boolean isEmpty() {
        return employees.isEmpty();
    }
}
