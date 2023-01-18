package com.assignment.converzai.carService.entity.role;

import com.assignment.converzai.carService.entity.common.BaseEntity;
import io.swagger.annotations.ApiModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "roles")
@ApiModel(description = "Every user has a role either Customer or Operator")
public class Role extends BaseEntity {
    @Column(name = "name")
    @NotBlank(message = "name is Mandatory")
    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
