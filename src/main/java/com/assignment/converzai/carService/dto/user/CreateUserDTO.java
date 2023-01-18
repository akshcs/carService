package com.assignment.converzai.carService.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Component
@ApiModel(description = "DTO for Adding Customer/Operator")
public class CreateUserDTO {
    @Size(min = 5, max = 15, message = "Username should have 5-15 letters")
    @NotBlank(message = "User Name is Mandatory")
    @ApiModelProperty(notes = "Username should have 5-15 letters", required = true)
    private String userName;

    @NotBlank(message = "First Name is Mandatory")
    @ApiModelProperty(required = true)
    private String firstName;

    @NotBlank(message = "Last Name is Mandatory")
    @ApiModelProperty(required = true)
    private String lastName;

    @Email(message = "Email not valid!")
    @NotBlank(message = "Email is Mandatory")
    @ApiModelProperty(notes = "Email should be a valid email pattern", required = true)
    private String email;

    @Pattern(regexp = "[0-9]{10}", message = "Please enter valid mobile phone")
    @NotBlank(message = "Mobile is Mandatory")
    @ApiModelProperty(notes = "Should be a 10 letter Number", required = true)
    private String mobile;

    @Size(min = 5, max = 100, message = "Wrong Address (Size should be between 5 to 100 Characters)!")
    @NotBlank(message = "Address is Mandatory")
    @ApiModelProperty(notes = "Size should be between 5 to 100 Characters", required = true)
    private String address;

    public CreateUserDTO() {
    }

    public CreateUserDTO(String userName, String firstName, String lastName, String email, String mobile, String address) {
        this.setUserName(userName);
        this.setEmail(email);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setMobile(mobile);
        this.setAddress(address);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
