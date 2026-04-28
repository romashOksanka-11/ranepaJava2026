package ru.ranepa.model.dto;

import java.math.BigDecimal;

public class EmployeeStatsDto {
    private BigDecimal averageSalary;
    private BigDecimal topSalary;
    private EmployeeResponseDto topEmployee;

    public EmployeeStatsDto(BigDecimal averageSalary, BigDecimal topSalary, EmployeeResponseDto topEmployee) {
        this.averageSalary = averageSalary;
        this.topSalary = topSalary;
        this.topEmployee = topEmployee;
    }

    public BigDecimal getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(BigDecimal averageSalary) {
        this.averageSalary = averageSalary;
    }

    public BigDecimal getTopSalary() {
        return topSalary;
    }

    public void setTopSalary(BigDecimal topSalary) {
        this.topSalary = topSalary;
    }

    public EmployeeResponseDto getTopEmployee() {
        return topEmployee;
    }

    public void setTopEmployee(EmployeeResponseDto topEmployee) {
        this.topEmployee = topEmployee;
    }
}
