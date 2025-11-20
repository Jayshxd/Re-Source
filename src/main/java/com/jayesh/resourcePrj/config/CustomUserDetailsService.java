package com.jayesh.resourcePrj.config;

import com.jayesh.resourcePrj.entities.Employee;
import com.jayesh.resourcePrj.repo.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeRepo employeeRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepo.findEmployeeByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Employee not found"));
        return new User(
                employee.getUsername(),
                employee.getPassword(),
                employee.getRoles().stream().map(role->new SimpleGrantedAuthority(role.getName().toUpperCase())).toList()
        );
    }
}
