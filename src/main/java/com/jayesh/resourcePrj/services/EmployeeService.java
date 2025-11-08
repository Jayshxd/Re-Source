package com.jayesh.resourcePrj.services;


import com.jayesh.resourcePrj.dto.request.EmployeeRequestDto;
import com.jayesh.resourcePrj.dto.response.EmployeeResponseDto;
import com.jayesh.resourcePrj.entities.Employee;
import com.jayesh.resourcePrj.entities.Role;
import com.jayesh.resourcePrj.repo.EmployeeRepo;
import com.jayesh.resourcePrj.repo.RoleRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepo employeeRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    public EmployeeResponseDto registerEmployee(EmployeeRequestDto request){
        log.info("Received request to register employee");
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
        Employee saved = employeeRepo.save(employee);
        log.info("Employee registered successfully");
        return new EmployeeResponseDto(saved);
    }
}
