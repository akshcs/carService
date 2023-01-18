package com.assignment.converzai.carService.service.role.impl;

import com.assignment.converzai.carService.dao.role.RoleRepository;
import com.assignment.converzai.carService.entity.role.Role;
import com.assignment.converzai.carService.exception.appointment.InvalidAppointmentException;
import com.assignment.converzai.carService.service.role.intf.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role getRoleById(long roleId) throws UsernameNotFoundException {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new InvalidAppointmentException("RoleId with id : " + roleId +" not found!"));
    }

    @Override
    public List<Role> getAllRoles() {
            return roleRepository.findAll();
    }

    @Override
    public Role saveNewRole(String roleName) {
        return roleRepository.save(new Role(roleName));
    }
}
