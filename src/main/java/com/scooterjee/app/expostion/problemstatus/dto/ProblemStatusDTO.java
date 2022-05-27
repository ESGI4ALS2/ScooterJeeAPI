package com.scooterjee.app.expostion.problemstatus.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProblemStatusDTO {

    @NotNull
    public Long id;

    @NotBlank
    public String name;

    public ProblemStatusDTO() {

    }

    public ProblemStatusDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
