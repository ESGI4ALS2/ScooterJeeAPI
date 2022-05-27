package com.scooterjee.app.expostion.category.dto;

import javax.validation.constraints.NotBlank;

public class CategoryDTO {

    @NotBlank
    public Long id;

    @NotBlank
    public String name;

    public CategoryDTO() {

    }

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
