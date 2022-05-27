package com.scooterjee.app.expostion.role.dto;

import javax.validation.constraints.NotBlank;

public class RoleDTO {

    public long id;
    @NotBlank
    public String name;

    public RoleDTO(long id,String name) {
        this.id = id;
        this.name = name;
    }
}
