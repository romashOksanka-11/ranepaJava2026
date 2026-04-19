package ru.ranepa.presentation;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;
import ru.ranepa.service.HRMService;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class HRMApplication {
    private final Scanner scanner = new Scanner(System.in);
    private final EmployeeRepository repository = new EmployeeRepository();
    private final HRMService service = new HRMService(repository);
    private long nextId = 1; // Для генерации уникальных ID

    public static void main(String[] args) {
        // Устанавливаем кодировку вывода "UTF-8".
        PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        System.setOut(out);

        HRMApplication app = new HRMApplication();
        app.run();
    }

    private void run() {
        System.out.println("=== Система управления персоналом (HRM) ===");

        while (true) {
            printMenu();
            System.out.print("Выберите пункт меню: ");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    printAllEmployees();
                    break;
                case "2":
                    addNewEmployee();
                    break;
                case "3":
                    deleteEmployeeById();
                    break;
                case "4":
                    findEmployeeById();
                    break;
                case "5":
                    showStatistics();
                    break;
                case "6":
                    System.out.println("Выход из программы. До свидания!");
                    return;
                default:
                    System.out.println("Неверный пункт меню. Пожалуйста, введите число от 1 до 6.");
            }
            System.out.println();
        } 
    }

    private Long getValidIdInput() {
        try {
            System.out.print("Введите ID сотрудника: ");
            return Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат ID. Введите целое число.");
            return null;
        }
    }

    private void showStatistics() {
        try {
            double averageSalary = service.getAverageSalary();
            Employee employeeWithMaxSalary = service.getEmployeeWithMaxSalary();
            System.out.println("--- Статистика по компании ---");
            System.out.printf("Средняя зарплата: %.2f руб.%n", averageSalary);
            System.out.println("Самый высокооплачиваемый сотрудник:");
            System.out.println(employeeWithMaxSalary);
        } catch (NoSuchElementException e) {
            System.out.println("Невозможно вывести статистику");
        }
    }

    private void findEmployeeById() {
        Long id = getValidIdInput();
        if (id == null)
            return;

        try {
            Employee employee = repository.findById(id);
            System.out.println("--- Сотрудник найден ---");
            System.out.println(employee);
        } catch (IllegalArgumentException e) {
            System.out.println("Сотрудник не найден");
        }
    }

    private void deleteEmployeeById() {
        Long id = getValidIdInput();
        if (id == null)
            return;

        try {
            repository.delete(id);
            System.out.println("--- Сотрудник удален ---");
        } catch (IllegalArgumentException e) {
            System.out.println("Сотрудник не найден");
        }
    }

    private void addNewEmployee() {
        System.out.println("--- Добавление нового сотрудника ---");
        System.out.print("Имя: ");
        String name = scanner.nextLine();

        System.out.print("Должность: ");
        String position = scanner.nextLine();

        BigDecimal salary = null;
        while (salary == null) {
            try {
                System.out.print("Зарплата: ");
                salary = new BigDecimal(scanner.nextLine());
                if (salary.compareTo(BigDecimal.ZERO) < 0) {
                    System.out.println("Зарплата не может быть отрицательной.");
                    salary = null;
                }
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат числа. Попробуйте еще раз.");
            }
        }

        LocalDate hireDate = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        while (hireDate == null) {
            try {
                System.out.print("Дата приема на работу (дд.мм.гггг): ");
                hireDate = LocalDate.parse(scanner.nextLine(), formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Неверный формат даты. Используйте дд.мм.гггг (например, 15.08.2025).");
            }
        }

        Employee employee = new Employee(nextId++, name, position, salary, hireDate);
        repository.save(employee);
        System.out.println("Добавлен сотрудник:");
        System.out.println(employee);
    }

    private void printAllEmployees() {
        String info = service.getAllEmployeesInfo();
        if (info == null) {
            System.out.println("Список сотрудников пуст.");
        } else {
            System.out.println("--- Список всех сотрудников ---");
            System.out.println(info);
        }
    }

    private void printMenu() {
        System.out.println("--- Меню ---");
        System.out.println("1. Вывести список всех сотрудников.");
        System.out.println("2. Добавить нового сотрудника.");
        System.out.println("3. Удалить сотрудника по ID.");
        System.out.println("4. Поиск сотрудника по ID.");
        System.out.println("5. Показать статистику (средняя зарплата, топ-менеджер).");
        System.out.println("6. Выход.");
        System.out.println();
    }
}
