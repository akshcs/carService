package com.assignment.converzai.carService.entity.user;

import com.assignment.converzai.carService.dto.user.CreateUserDTO;
import com.assignment.converzai.carService.dto.user.UpdateUserDTO;
import com.assignment.converzai.carService.entity.common.BaseEntity;
import com.assignment.converzai.carService.entity.role.Role;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@ApiModel(description = "Details about the Users")
public abstract class User extends BaseEntity {
    @Column(name = "username")
    private String userName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "address")
    private String address;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public User() {
    }

    public User(CreateUserDTO createUserDTO, Collection<Role> roles) {
        addUserDetails(createUserDTO.getUserName(), createUserDTO.getFirstName(), createUserDTO.getLastName(),
                createUserDTO.getEmail(), createUserDTO.getMobile(), createUserDTO.getAddress());
        this.roles = roles;
    }

    public void update(UpdateUserDTO updateUserDTO) {
        addUserDetails(updateUserDTO.getUserName(), updateUserDTO.getFirstName(), updateUserDTO.getLastName(),
                updateUserDTO.getEmail(), updateUserDTO.getMobile(), updateUserDTO.getAddress());
    }

    private void addUserDetails(String userName, String firstName, String lastName, String email, String mobile, String address){
        addUserNameDetail(userName, firstName, lastName);
        addUserAddressDetail(email, mobile, address);
    }

    private void addUserNameDetail(String userName, String firstName, String lastName){
        if(Objects.nonNull(userName)){
            this.userName = userName;
        }
        if(Objects.nonNull(firstName)){
            this.firstName = firstName;
        }
        if(Objects.nonNull(lastName)){
            this.lastName = lastName;
        }
    }

    private void addUserAddressDetail(String email, String mobile, String address){
        if(Objects.nonNull(email)){
            this.email = email;
        }
        if(Objects.nonNull(mobile)){
            this.mobile = mobile;
        }
        if(Objects.nonNull(address)){
            this.address = address;
        }
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

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
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

    public boolean hasRole(String roleName) {
        for (Role role : roles) {
            if (role.getName().equals(roleName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return this.getId().equals(user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId());
    }
}
