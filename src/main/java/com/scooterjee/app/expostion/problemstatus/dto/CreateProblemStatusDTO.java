package com.scooterjee.app.expostion.problemstatus.dto;

import javax.validation.constraints.NotBlank;

public class CreateProblemStatusDTO {
    @NotBlank
    public String name;
}
