package ru.ranepa.service;

import org.springframework.stereotype.Service;
import ru.ranepa.exception.ResourceNotFoundException;
import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;
import ru.ranepa.model.dto.EmployeeRequestDto;
import ru.ranepa.model.dto.EmployeeResponseDto;
import ru.ranepa.model.dto.EmployeeStatsDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HRMService {
    private final EmployeeRepository employeeRepository;

    public HRMService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Преобразует DTO (то, что пришло от клиента) в Entity (то, что сохранится в БД)
    private Employee toEntity(EmployeeRequestDto dto) {
        Employee entity = new Employee();
        entity.setName(dto.getName());
        entity.setPosition(dto.getPosition());
        entity.setSalary(dto.getSalary());
        return entity;
    }

    // Преобразует Entity (то, что достали из БД) в DTO (то, что отправим клиенту)
    private EmployeeResponseDto toDto(Employee entity) {
        EmployeeResponseDto dto = new EmployeeResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPosition(entity.getPosition());
        dto.setSalary(entity.getSalary());
        dto.setHireDate(entity.getHireDate());
        return dto;
    }

    // Создать нового сотрудника
    public EmployeeResponseDto createEmployee(EmployeeRequestDto requestDto) {
        // Преобразуем DTO в Entity
        Employee newEmployee = toEntity(requestDto);
        // Сохраняем в базу данных. Метод save() вернет сущность с заполненным ID.
        Employee savedEntity = employeeRepository.save(newEmployee);
        // Преобразуем сохраненную сущность обратно в DTO для ответа
        return toDto(savedEntity);
    }

    // Получить список всех сотрудников
    public List<EmployeeResponseDto> findAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Найти сотрудника по ID
    public EmployeeResponseDto findEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                // Если сотрудник не найден:
                .orElseThrow(() -> new ResourceNotFoundException("Сотрудник с ID " + id + " не найден"));
        // Преобразуем сущность в DTO для отправки клиенту
        return toDto(employee);
    }

    // Удалить сотрудника по ID
    public boolean deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Найти сотрудников по должности
    public List<EmployeeResponseDto> findByPosition(String position) {
        return employeeRepository.findByPosition(position).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Получить статистику
    public EmployeeStatsDto getStatistics() {
        List<Employee> allEmployees = employeeRepository.findAll();

        if (allEmployees.isEmpty()) {
            // Если база пуста, возвращаем нули, чтобы избежать ошибок деления на ноль
            return new EmployeeStatsDto(BigDecimal.ZERO, BigDecimal.ZERO, null);
        }

        // Максимальная зарплата
        BigDecimal topSalary = allEmployees.stream()
                .map(Employee::getSalary)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        // Средняя зарплата
        BigDecimal sum = allEmployees.stream()
                .map(Employee::getSalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal averageSalary = sum.divide(BigDecimal.valueOf(allEmployees.size()), 2, RoundingMode.HALF_UP);

        EmployeeResponseDto employeeResponseDto = allEmployees.stream()
                .max(Comparator.comparing(Employee::getSalary))
                .map(this::toDto)
                .orElse(null);

        return new EmployeeStatsDto(averageSalary, topSalary, employeeResponseDto);
    }
}
