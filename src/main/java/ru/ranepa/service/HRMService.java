package ru.ranepa.service;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HRMService {
    private final EmployeeRepository repository;

    public HRMService(EmployeeRepository repository) {
        this.repository = repository;
    }

    // Расчет среднего значения зарплаты по всей компании
    public double getAverageSalary() {
        return repository.findAll()
                .stream()
                .mapToDouble(e -> e.getSalary().doubleValue())
                .average()
                .orElseThrow();
    }

    // Поиск самого высокооплачиваемого сотрудника
    public Employee getEmployeeWithMaxSalary() {
        return repository.findAll()
                .stream()
                .max(Comparator.comparing(Employee::getSalary))
                .orElseThrow();
    }

    // Фильтрация сотрудников по должности
    public List<Employee> getEmployeesByPosition(String position) {
        if (repository.isEmpty())
            return null;
        return repository.findAll()
                .stream()
                .filter(employee -> position.equals(employee.getPosition()))
                .toList();
    }

    // Получение списка всех сотрудников
    public String getAllEmployeesInfo() {
        if (repository.isEmpty())
            return null;
        return repository.findAll()
                .stream()
                .map(Employee::toString)
                .collect(Collectors.joining("\n"));
    }
}
