package com.assignment.converzai.carService.service.role.intf;

import com.assignment.converzai.carService.entity.role.Role;

import java.util.List;

public interface RoleService {
    Role getRoleById(long roleId);

    List<Role> getAllRoles();

    Role saveNewRole(String roleName);
}
