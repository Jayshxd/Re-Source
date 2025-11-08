package com.jayesh.resourcePrj.services;


import com.jayesh.resourcePrj.dto.request.EmployeeRequestDto;
import com.jayesh.resourcePrj.dto.response.EmployeeResponseDto;
import com.jayesh.resourcePrj.entities.Employee;
import com.jayesh.resourcePrj.entities.Role;
import com.jayesh.resourcePrj.repo.EmployeeRepo;
import com.jayesh.resourcePrj.repo.RoleRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepo employeeRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    public EmployeeResponseDto registerEmployee(EmployeeRequestDto request){
        log.info("Received request to register employee");
        Employee employee = mapDtoToEmployee(request);
        Employee saved = employeeRepo.save(employee);
        log.info("Employee registered successfully");
        return new EmployeeResponseDto(saved);
    }
    @Transactional
    public List<EmployeeResponseDto> registerEmployeesInBulk(List<EmployeeRequestDto> request){
        log.info("Received request to register employees in bulk");
        List<Employee> employees = request.stream().map(this::mapDtoToEmployee).toList();
        List<Employee> saved = employeeRepo.saveAll(employees);
        log.info("All employees registered successfully");
        return saved.stream().map(EmployeeResponseDto::new).toList();
    }
    public Employee mapDtoToEmployee(EmployeeRequestDto request){
        log.info("Received request to map employee");
        if(employeeRepo.findEmployeeByUsername(request.getUsername()).isPresent()){
            throw new IllegalArgumentException("Employee already exists");
        }
        Role role = roleRepo.findRoleByName("user").orElseGet(
                ()->roleRepo.save(new Role("user"))
        );
        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setPassword(passwordEncoder.encode(request.getPassword()));
        employee.setUsername(request.getUsername());
        employee.setRoles(Set.of(role));
        return employee;
    }

    public List<EmployeeResponseDto> findAllEmployees(String name, String email, String username) {
        log.info("Received request to find all employees");
        List<Employee> employees = employeeRepo.findByCriteria(name, email, username);
        log.info("Employees found successfully");
        return employees.stream().map(EmployeeResponseDto::new).toList();
    }


}
