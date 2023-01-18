package com.assignment.converzai.carService.service.user.intf;

import com.assignment.converzai.carService.dto.user.CreateUserDTO;
import com.assignment.converzai.carService.dto.user.UpdateUserDTO;
import com.assignment.converzai.carService.entity.user.Customer;
import com.assignment.converzai.carService.entity.user.Operator;
import com.assignment.converzai.carService.entity.role.Role;
import com.assignment.converzai.carService.entity.user.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.List;

public interface UserService {
    /*
     * User
     * */
    boolean userExists(String userName);

    User getUserById(long userId);

    User getUserByUsername(String userName);

    User getUserByEmail(String userName) throws UsernameNotFoundException;

    User getUserByMobile(String userName) throws UsernameNotFoundException;

    List<User> getUsersByRoleName(String roleName);

    List<User> getAllUsers();

    void deleteUserById(long userId);

    /*
     * Operator
     * */
    Operator getOperatorById(long operatorId);
    List<Operator> getAllOperators();

    Operator saveNewOperator(CreateUserDTO createUserDTO);

    Operator updateOperatorProfile(long operatorId, UpdateUserDTO updateUserDTO);

    Collection<Role> getRolesForOperator();

    /*
     * Customer
     * */
    Customer getCustomerById(long customerId);

    List<Customer> getAllCustomers();

    Customer saveNewCustomer(CreateUserDTO createUserDTO);

    Customer updateCustomerProfile(long userId, UpdateUserDTO updateUserDTO);

    Collection<Role> getRolesForCustomer();
}

