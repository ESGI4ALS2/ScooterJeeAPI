package com.scooterjee.app.domain.problem;

import com.scooterjee.app.domain.categories.Categories;
import com.scooterjee.app.domain.problemestatus.ProblemStatus;
import com.scooterjee.app.domain.scooter.Scooter;
import com.scooterjee.app.domain.user.User;
import com.scooterjee.kernel.Entity;
import com.scooterjee.kernel.coordinate.Coordinate;

import java.time.LocalDate;

public class Problem extends Entity<Long> {

    private final String name;
    private final String description;
    private final Scooter scooter;
    private final Coordinate coordinate;
    private final LocalDate date;
    private final Categories categories;
    private User referent;
    private ProblemStatus status;

    public Problem(
        Long id,
        String name,
        String description,
        Scooter scooter,
        Coordinate coordinate,
        LocalDate date,
        Categories categories,
        ProblemStatus status
    ) {
        super(id);
        this.name = name;
        this.description = description;
        this.scooter = scooter;
        this.coordinate = coordinate;
        this.date = date;
        this.categories = categories;
        this.status = status;
    }

    public Problem(
        User referent,
        Long id,
        String name,
        String description,
        Scooter scooter,
        Coordinate coordinate,
        LocalDate date,
        Categories categories,
        ProblemStatus status
    ) {
        super(id);
        this.name = name;
        this.description = description;
        this.scooter = scooter;
        this.coordinate = coordinate;
        this.date = date;
        this.categories = categories;
        this.status = status;
        this.referent = referent;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public LocalDate getDate() {
        return date;
    }

    public Categories getCategories() {
        return categories;
    }

    public ProblemStatus getStatus() {
        return status;
    }

    public Scooter getScooter() {
        return scooter;
    }

    public User getReferent() {
        return referent;
    }

    public void setStatus(ProblemStatus status) {
        this.status = status;
    }

    public void setReferent(User referent) {
        this.referent = referent;
    }
}
