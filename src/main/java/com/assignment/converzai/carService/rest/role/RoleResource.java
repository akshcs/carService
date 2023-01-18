package com.assignment.converzai.carService.rest.role;

import com.assignment.converzai.carService.entity.role.Role;
import com.assignment.converzai.carService.entity.user.Customer;
import com.assignment.converzai.carService.exception.user.DuplicateUserException;
import com.assignment.converzai.carService.service.role.intf.RoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/role")
public class RoleResource {
    @Autowired
    private RoleService roleService;

    @GetMapping("/{roleId}/get")
    @ApiOperation(value = "Finds Roles by roleId",
            notes = "Provide a role id to look specific Role from Role Table", response = Customer.class)
    public Role getRole(@PathVariable("roleId") long roleId) throws UsernameNotFoundException {
        return roleService.getRoleById(roleId);
    }

    @GetMapping("/getAll")
    @ApiOperation(value = "Finds All the roles",
            notes = "Looks up all the roles from Role Table")
    public List<Role> getAllRoles(){
        return roleService.getAllRoles();
    }

    @PostMapping("/new/{roleName}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add a new Role",
            notes = "Provide Role name to add a new role into Role Table ", response = Role.class)
    public Role addRole(@PathVariable("roleName") String roleName) throws DuplicateUserException {
        return roleService.saveNewRole(roleName);
    }
}
