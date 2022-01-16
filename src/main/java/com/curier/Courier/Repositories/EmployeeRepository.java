package com.curier.Courier.Repositories;

import com.curier.Courier.Models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByFirstName(String firstName);
}