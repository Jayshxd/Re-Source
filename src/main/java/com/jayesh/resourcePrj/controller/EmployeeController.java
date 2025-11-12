package com.jayesh.resourcePrj.controller;


import com.jayesh.resourcePrj.dto.response.EmployeeResponseDto;
import com.jayesh.resourcePrj.dto.response.TrackResponseDto;
import com.jayesh.resourcePrj.services.EmployeeService;
import com.jayesh.resourcePrj.services.TrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/emps")
public class EmployeeController {
    // Register and Login are in AuthController

    private final EmployeeService employeeService;
    private final TrackService trackService;


    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>> findEmployees(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String username
    ) {
        List<EmployeeResponseDto> employees = employeeService.findAllEmployees(name,email,username);
        return ResponseEntity.ok(employees);
    }


    @GetMapping("/{empId}/tracks")
    @ResponseStatus(HttpStatus.OK)
    public List<TrackResponseDto> findTrackByEmpId(@PathVariable Long empId) {
        return trackService.findTracksByEmpId(empId);
    }


}
