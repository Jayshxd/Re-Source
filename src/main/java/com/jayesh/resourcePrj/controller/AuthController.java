package com.jayesh.resourcePrj.controller;


import com.jayesh.resourcePrj.dto.request.EmployeeRequestDto;
import com.jayesh.resourcePrj.dto.response.EmployeeResponseDto;
import com.jayesh.resourcePrj.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")

public class AuthController {

    private final EmployeeService employeeService;
    @PostMapping("/register")
    public ResponseEntity<?> post(@RequestBody EmployeeRequestDto request){
        EmployeeResponseDto response = employeeService.registerEmployee(request);
        return ResponseEntity.ok(response);
    }

}
