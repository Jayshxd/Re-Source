package com.jayesh.resourcePrj.dto.response;

import com.jayesh.resourcePrj.entities.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeResponseDto {

    private Long id;
    private String name;
    private String username;
    private String email;


    public EmployeeResponseDto(Employee saved) {
        this.id = saved.getId();
        this.name = saved.getName();
        this.username = saved.getUsername();
        this.email = saved.getEmail();
    }
}
