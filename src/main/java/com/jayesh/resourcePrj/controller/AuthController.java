package com.jayesh.resourcePrj.controller;


import com.jayesh.resourcePrj.dto.request.EmployeeRequestDto;
import com.jayesh.resourcePrj.dto.request.LoginRequestDto;
import com.jayesh.resourcePrj.dto.response.EmployeeResponseDto;
import com.jayesh.resourcePrj.dto.response.LoginResponseDto;
import com.jayesh.resourcePrj.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "https://re-source-xi.vercel.app/")
public class AuthController {

    private final EmployeeService employeeService;
    @PostMapping("/register")
    public ResponseEntity<?> post(@RequestBody EmployeeRequestDto request){
        EmployeeResponseDto response = employeeService.registerEmployee(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/bulk/register")
    public ResponseEntity<List<EmployeeResponseDto>> post(@RequestBody List<EmployeeRequestDto> request){
        List<EmployeeResponseDto> response = employeeService.registerEmployeesInBulk(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public LoginResponseDto login(@RequestBody LoginRequestDto request){
        return employeeService.login(request);
    }
}
