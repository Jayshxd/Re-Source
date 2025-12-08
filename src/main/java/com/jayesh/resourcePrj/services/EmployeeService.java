package com.jayesh.resourcePrj.services;


import com.jayesh.resourcePrj.dto.request.EmployeeRequestDto;
import com.jayesh.resourcePrj.dto.request.LoginRequestDto;
import com.jayesh.resourcePrj.dto.response.EmployeeResponseDto;
import com.jayesh.resourcePrj.dto.response.LoginResponseDto;
import com.jayesh.resourcePrj.entities.Employee;
import com.jayesh.resourcePrj.entities.Role;
import com.jayesh.resourcePrj.repo.EmployeeRepo;
import com.jayesh.resourcePrj.repo.RoleRepo;
import com.jayesh.resourcePrj.security.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

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
        return Employee.builder()
                .username(request.getUsername())
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(role))
                .build();
    }

    public List<EmployeeResponseDto> findAllEmployees(String name, String email, String username) {
        log.info("Received request to find all employees");
        List<Employee> employees = employeeRepo.findByCriteria(name, email, username);
        log.info("Employees found successfully");
        return employees.stream().map(EmployeeResponseDto::new).toList();
    }


    public LoginResponseDto login(LoginRequestDto request) {
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtUtil.generateJwtToken(userDetails);
        return new LoginResponseDto(jwtToken, userDetails.getUsername(), "Login Successful");
    }
}
