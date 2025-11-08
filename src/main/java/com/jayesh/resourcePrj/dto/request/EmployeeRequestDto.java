package com.jayesh.resourcePrj.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeRequestDto {
    private String name;
    private String email;
    private String username;
    private String password;
}
