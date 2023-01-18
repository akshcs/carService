package com.assignment.converzai.carService.rest.user;

import com.assignment.converzai.carService.dto.user.CreateUserDTO;
import com.assignment.converzai.carService.dto.user.UpdateUserDTO;
import com.assignment.converzai.carService.entity.user.Operator;
import com.assignment.converzai.carService.service.user.intf.OperatorService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/operator")
public class OperatorResource {
    @Autowired
    @Qualifier("operator")
    private OperatorService operatorService;

    @GetMapping("/{operatorId}/get")
    @ApiOperation(value = "Finds Operator by operatorId",
            notes = "Provide an operator id to look specific operator from Operator Table", response = Operator.class)
    public Operator getOperator(@PathVariable("operatorId") long operatorId) throws UsernameNotFoundException {
        return operatorService.getOperatorById(operatorId);
    }

    @GetMapping("/getAll")
    @ApiOperation(value = "Finds All the Operators",
            notes = "Looks up all the operators from Operator Table")
    public List<Operator> getAllOperator(){
        return operatorService.getAllOperators();
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add a new Operator",
            notes = "Provide operator details to add a new operator into Operator Table " +
                    "(Payload should be of type CreateUserDTO)", response = Operator.class)
    public Operator addOperator(@Valid @RequestBody CreateUserDTO createUserDTO) throws UsernameNotFoundException {
        return operatorService.saveNewOperator(createUserDTO);
    }

    @PutMapping("/{operatorId}/update")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update Operator by operatorId",
            notes = "Update a operator's details, Use operatorId to look specific operator from Operator Table and update it's values " +
                    "(Payload should be of type UpdateUserDTO)", response = Operator.class)
    public Operator updateOperator(@PathVariable("operatorId") long operatorId, @Valid @RequestBody UpdateUserDTO updateUserDTO) throws UsernameNotFoundException{
        return operatorService.updateOperatorProfile(operatorId, updateUserDTO);
    }

    @DeleteMapping("/{operatorId}/remove")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete Operator by operatorId",
            notes = "Use operatorId to look specific operator from Operator Table and delete it")
    public void deleteOperator(@PathVariable("operatorId") long operatorId) throws UsernameNotFoundException{
        operatorService.deleteUserById(operatorId);
    }
}
