package ru.ranepa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ranepa.model.Employee;

import java.math.BigDecimal;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByPosition(String position);
    List<Employee> findBySalaryGreaterThanEqual(BigDecimal salary);
}