package com.assignment.converzai.carService.service.user.impl;

import com.assignment.converzai.carService.dao.role.RoleRepository;
import com.assignment.converzai.carService.dao.user.CustomerRepository;
import com.assignment.converzai.carService.dao.user.OperatorRepository;
import com.assignment.converzai.carService.dao.user.UserRepository;
import com.assignment.converzai.carService.dto.user.CreateUserDTO;
import com.assignment.converzai.carService.dto.user.UpdateUserDTO;
import com.assignment.converzai.carService.entity.user.Customer;
import com.assignment.converzai.carService.entity.user.Operator;
import com.assignment.converzai.carService.entity.role.Role;
import com.assignment.converzai.carService.entity.user.User;
import com.assignment.converzai.carService.exception.user.DuplicateUserException;
import com.assignment.converzai.carService.service.user.intf.UserService;
import com.assignment.converzai.carService.service.validation.intf.UserValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
@Qualifier("user")
public class UserServiceImpl implements UserService {

    @Autowired
    private OperatorRepository operatorRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserValidationService userValidationService;

    public UserServiceImpl() {
    }

    @Override
    public boolean userExists(String userName) {
        return userRepository.findByUserName(userName).isPresent();
    }

    @Override
    public User getUserById(long userId) throws UsernameNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User with id : " + userId +" not found!"));
    }

    @Override
    public Operator getOperatorById(long operatorId) throws UsernameNotFoundException {
        return operatorRepository.findById(operatorId)
                .orElseThrow(() -> new UsernameNotFoundException("Operator with id : " + operatorId +" not found!"));
    }

    @Override
    public List<Operator> getAllOperators() {
        return operatorRepository.findAll();
    }


    @Override
    public Operator saveNewOperator(CreateUserDTO createUserDTO) {
        userValidationService.checkDuplicates(createUserDTO.getUserName(), createUserDTO.getEmail(), createUserDTO.getMobile());
        Operator operator = new Operator(createUserDTO, getRolesForOperator());
        return operatorRepository.save(operator);
    }

    @Override
    public Operator updateOperatorProfile(long operatorId, UpdateUserDTO updateUserDTO) {
        Operator operator = getOperatorById(operatorId);
        validateDuplicatesOnUpdate(updateUserDTO, operator);
        operator.update(updateUserDTO);
        return operatorRepository.save(operator);
    }

    @Override
    public Collection<Role> getRolesForOperator() {
        HashSet<Role> roles = new HashSet();
        roles.add(roleRepository.findByName("ROLE_OPERATOR"));
        return roles;
    }

    @Override
    public Customer getCustomerById(long customerId) throws UsernameNotFoundException {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new UsernameNotFoundException("Customer with id : " + customerId +" not found!"));
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer saveNewCustomer(CreateUserDTO createUserDTO) throws DuplicateUserException {
        userValidationService.checkDuplicates(createUserDTO.getUserName(), createUserDTO.getEmail(), createUserDTO.getMobile());
        Customer customer = new Customer(createUserDTO, getRolesForCustomer());
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomerProfile(long userId, UpdateUserDTO updateUserDTO) throws DuplicateUserException {
        Customer customer;
        try {
            customer = getCustomerById(userId);
        } catch (UsernameNotFoundException exception){
            throw exception;
        }
        validateDuplicatesOnUpdate(updateUserDTO, customer);
        customer.update(updateUserDTO);
        return customerRepository.save(customer);
    }

    @Override
    public Collection<Role> getRolesForCustomer() {
        HashSet<Role> roles = new HashSet();
        roles.add(roleRepository.findByName("ROLE_CUSTOMER"));
        return roles;
    }

    @Override
    public User getUserByUsername(String userName) throws UsernameNotFoundException {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Customer with user name : " + userName +" not found!"));
    }

    @Override
    public User getUserByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Customer with email : " + email +" not found!"));
    }

    @Override
    public User getUserByMobile(String mobile) throws UsernameNotFoundException {
        return userRepository.findByMobile(mobile)
                .orElseThrow(() -> new UsernameNotFoundException("Customer with mobile : " + mobile +" not found!"));
    }

    @Override
    public List<User> getUsersByRoleName(String roleName) {
        return userRepository.findByRoleName(roleName);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(long userId) throws UsernameNotFoundException{
        try {
            userRepository.deleteById(userId);
        } catch (Exception ex){
            throw new UsernameNotFoundException("Can not delete User, Unable to find");
        }
    }

    private void validateDuplicatesOnUpdate(UpdateUserDTO updateUserDTO, User user) throws DuplicateUserException {
        String userName = null;
        String email = null;
        String mobile = null;
        if((Objects.nonNull(updateUserDTO.getUserName())) && (!updateUserDTO.getUserName().equals(user.getUserName()))){
            userName = updateUserDTO.getUserName();
        }
        if((Objects.nonNull(updateUserDTO.getEmail())) && (!updateUserDTO.getEmail().equals(user.getEmail()))){
            email = updateUserDTO.getEmail();
        }
        if((Objects.nonNull(updateUserDTO.getMobile())) && (!updateUserDTO.getMobile().equals(user.getMobile()))){
            mobile = updateUserDTO.getEmail();
        }
        userValidationService.checkDuplicates(userName, email, mobile);
    }
}
