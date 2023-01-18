package com.assignment.converzai.carService.rest.user;

import com.assignment.converzai.carService.dto.user.CreateUserDTO;
import com.assignment.converzai.carService.dto.user.UpdateUserDTO;
import com.assignment.converzai.carService.entity.user.Customer;
import com.assignment.converzai.carService.exception.user.DuplicateUserException;
import com.assignment.converzai.carService.service.user.intf.CustomerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerResource {

    @Autowired
    @Qualifier("customer")
    private CustomerService customerService;

    @GetMapping("/{customerId}/get")
    @ApiOperation(value = "Finds Customer by customerId",
            notes = "Provide a customer id to look specific customer from Customer Table", response = Customer.class)
    public Customer getCustomer(@PathVariable("customerId") long customerId) throws UsernameNotFoundException {
        return customerService.getCustomerById(customerId);
    }

    @GetMapping("/getAll")
    @ApiOperation(value = "Finds All the Customers",
            notes = "Looks up all the customers from Customer Table")
    public List<Customer> getAllCustomer(){
        return customerService.getAllCustomers();
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add a new Customer",
            notes = "Provide customer details to add a new customer into Customer Table " +
                    "(Payload should be of type CreateUserDTO)", response = Customer.class)
    public Customer addCustomer(@Valid @RequestBody CreateUserDTO createUserDTO) throws DuplicateUserException {
        return customerService.saveNewCustomer(createUserDTO);
    }

    @PutMapping("/{customerId}/update")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update Customer by customerId",
            notes = "Update a customer's details, Use customerId to look specific customer from Customer Table and update it's values " +
                    "(Payload should be of type UpdateUserDTO)", response = Customer.class)
    public Customer updateCustomer(@PathVariable("customerId") long customerId, @Valid @RequestBody UpdateUserDTO updateUserDTO) throws DuplicateUserException {
        return customerService.updateCustomerProfile(customerId, updateUserDTO);
    }

    @DeleteMapping("/{customerId}/remove")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete Customer by customerId",
            notes = "Use customerId to look specific customer from Customer Table and delete it")
    public void deleteCustomer(@PathVariable("customerId") long customerId) throws DuplicateUserException {
        customerService.deleteUserById(customerId);
    }
}
