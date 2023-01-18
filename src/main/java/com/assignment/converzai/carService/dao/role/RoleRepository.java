package com.assignment.converzai.carService.dao.role;

import com.assignment.converzai.carService.entity.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String roleName);
}