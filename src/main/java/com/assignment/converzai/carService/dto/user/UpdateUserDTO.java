package com.assignment.converzai.carService.dto.user;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Component
public class UpdateUserDTO {
    @Size(min = 5, max = 15, message = "Username should have 5-15 letters")
    @ApiModelProperty(notes = "Username should have 5-15 letters")
    private String userName;
    private String firstName;
    private String lastName;
    @Email(message = "Email not valid!")
    @ApiModelProperty(notes = "Email should be a valid email pattern")
    private String email;
    @Pattern(regexp = "[0-9]{10}", message = "Please enter valid mobile phone")
    @ApiModelProperty(notes = "Should be a 10 letter Number")
    private String mobile;
    @Size(min = 5, max = 100, message = "Wrong Address (Size should be between 5 to 100 Characters)!")
    @ApiModelProperty(notes = "Size should be between 5 to 100 Characters")
    private String address;

    public UpdateUserDTO() {
    }

    public UpdateUserDTO(String userName, String firstName, String lastName, String email, String mobile, String address) {
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
