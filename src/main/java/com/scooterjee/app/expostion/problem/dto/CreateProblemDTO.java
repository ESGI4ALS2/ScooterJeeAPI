package com.scooterjee.app.expostion.problem.dto;

import javax.validation.constraints.NotBlank;

public class CreateProblemDTO {
    @NotBlank
    public String name;
    @NotBlank
    public String description;
    public Long scooterId;
    public Double latitude;
    public Double longitude;
    public Long categoryId;
    public Long problemStatusId;
}
