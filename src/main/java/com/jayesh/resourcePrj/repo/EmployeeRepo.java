package com.jayesh.resourcePrj.repo;


import com.jayesh.resourcePrj.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Long> {

    Optional<Employee> findEmployeeByUsername(String username);

}
