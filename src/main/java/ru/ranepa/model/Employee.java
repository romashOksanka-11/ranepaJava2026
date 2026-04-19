package ru.ranepa.model;

import java.math.BigDecimal;
import java.time.LocalDate;

// Класс "Сотрудник"
public class Employee {
    // Поля класса
    private Long id;
    private String name;
    private String position;
    private BigDecimal salary;
    private LocalDate hireDate;

    // Конструктор
    public Employee(Long id, String name, String position, BigDecimal salary, LocalDate hireDate) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.hireDate = hireDate;
    }

    // Геттеры и сеттеры
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public Long getId() {
        return id;
    }

    @Override // Аннотация
    public String toString() {
        return "Сотрудник {" +
                "id = " + id +
                ", имя = \"" + name + "\"" +
                ", должность = \"" + position + "\"" +
                ", зарплата = " + salary +
                ", дата найма = " + hireDate +
                "}";
    }
}
