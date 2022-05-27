package com.scooterjee.app.expostion.category.dto;

import javax.validation.constraints.NotBlank;

public class CreateCategoryDTO {
    @NotBlank
    public String name;

    public CreateCategoryDTO(){

    }

    public CreateCategoryDTO(String name) {
        this.name = name;
    }
}
