package ru.ranepa.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ranepa.model.dto.EmployeeRequestDto;
import ru.ranepa.model.dto.EmployeeResponseDto;
import ru.ranepa.model.dto.EmployeeStatsDto;
import ru.ranepa.service.HRMService;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final HRMService employeeService;

    public EmployeeController(HRMService employeeService) {
        this.employeeService = employeeService;
    }

    // Получить список всех сотрудников (GET /api/employees)
    @GetMapping
    public List<EmployeeResponseDto> getAllEmployees() {
        return employeeService.findAllEmployees();
    }

    // Получить сотрудника по ID (GET /api/employees/{id})
    @GetMapping("/{id}")
    public EmployeeResponseDto getEmployeeById(@PathVariable Long id) {
        return employeeService.findEmployeeById(id);
    }

    // Создать нового сотрудника (POST /api/employees)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Вернет статус 201 Created
    public EmployeeResponseDto createEmployee(@Valid @RequestBody EmployeeRequestDto request) {
        return employeeService.createEmployee(request);
    }

    // Удалить сотрудника по ID (DELETE /api/employees/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        if (employeeService.deleteEmployee(id)) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    // Найти сотрудников по должности (GET /api/employees/position/{position})
    @GetMapping("/position/{position}")
    public List<EmployeeResponseDto> getEmployeesByPosition(@PathVariable String position) {
        return employeeService.findByPosition(position);
    }

    // Получить статистику (GET /api/employees/stats)
    @GetMapping("/stats")
    public EmployeeStatsDto getStatistics() {
        return employeeService.getStatistics();
    }
}
