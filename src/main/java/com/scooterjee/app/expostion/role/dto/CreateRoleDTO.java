package com.scooterjee.app.expostion.role.dto;

import javax.validation.constraints.NotBlank;

public class CreateRoleDTO {

    @NotBlank
    public String name;

    public CreateRoleDTO() {

    }

    public CreateRoleDTO(String name) {
        this.name = name;
    }
}
