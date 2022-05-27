package com.scooterjee.app.expostion.problem.dto;

import com.scooterjee.app.domain.problem.Problem;
import com.scooterjee.app.expostion.category.dto.CategoryDTO;
import com.scooterjee.app.expostion.problemstatus.dto.ProblemStatusDTO;
import com.scooterjee.app.expostion.scooter.dto.ScooterDTO;
import com.scooterjee.app.expostion.user.dto.UserDTO;

import java.time.LocalDate;

public class ProblemDTO {

    public Long id;
    public String name;
    public String description;
    public ScooterDTO scooter;
    public Double latitude;
    public Double longitude;
    public LocalDate date;
    public UserDTO owner;
    public UserDTO referent;
    public CategoryDTO category;
    public ProblemStatusDTO status;

    public ProblemDTO() {
    }

    public ProblemDTO(Long id, String name, String description, ScooterDTO scooterDTO, Double latitude, Double longitude, LocalDate date, UserDTO owner, UserDTO referent, CategoryDTO categoryDTO, ProblemStatusDTO status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.scooter = scooterDTO;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.owner = owner;
        this.referent = referent;
        this.category = categoryDTO;
        this.status = status;
    }

    public static ProblemDTO of(Problem problem) {
        UserDTO referent = null;
        if (problem.getReferent() != null) {
            referent = UserDTO.of(problem.getReferent());
        }
        return new ProblemDTO(problem.getID(),
                problem.getName(),
                problem.getDescription(),
                new ScooterDTO(problem.getScooter().getID(), problem.getScooter().getModel().getID(), problem.getScooter().getSerialNumber()),
                problem.getCoordinate().getLatitude(),
                problem.getCoordinate().getLongitude(),
                problem.getDate(),
                UserDTO.of(problem.getScooter().getOwner()),
                referent,
                new CategoryDTO(problem.getCategories().getID(), problem.getCategories().getName()),
                new ProblemStatusDTO(problem.getStatus().getID(), problem.getStatus().getName()));
    }
}
