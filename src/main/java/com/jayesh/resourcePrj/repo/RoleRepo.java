package com.jayesh.resourcePrj.repo;

import com.jayesh.resourcePrj.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {

    Optional<Role> findRoleByName(String name);
}
