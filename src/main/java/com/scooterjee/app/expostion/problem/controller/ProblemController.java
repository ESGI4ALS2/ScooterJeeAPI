package com.scooterjee.app.expostion.problem.controller;

import com.scooterjee.app.domain.categories.Categories;
import com.scooterjee.app.domain.problem.Problem;
import com.scooterjee.app.domain.problemestatus.ProblemStatus;
import com.scooterjee.app.domain.scooter.Scooter;
import com.scooterjee.app.domain.session.Session;
import com.scooterjee.app.expostion.error.ErrorHandler;
import com.scooterjee.app.expostion.problem.dto.CreateProblemDTO;
import com.scooterjee.app.expostion.problem.dto.ProblemDTO;
import com.scooterjee.app.infrastructure.service.*;
import com.scooterjee.kernel.coordinate.Coordinate;
import com.scooterjee.kernel.exception.SimpleServiceException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class ProblemController extends ErrorHandler {

    private final ProblemService problemService;

    private final ProblemStatusService problemStatusService;

    private final CategoriesService categoriesService;

    private final ScooterService scooterService;

    private final SessionService sessionService;

    public ProblemController(ProblemService problemService,
                             ProblemStatusService problemStatusService,
                             CategoriesService categoriesService,
                             ScooterService scooterService,
                             SessionService sessionService) {
        this.problemService = problemService;
        this.problemStatusService = problemStatusService;
        this.categoriesService = categoriesService;
        this.scooterService = scooterService;
        this.sessionService = sessionService;
    }

    @GetMapping(value = "/problems")
    public List<ProblemDTO> getAllProblems() {
        return problemService.getAll().stream()
                .map(ProblemDTO::of)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/problems/{id}")
    public ProblemDTO getProblem(@PathVariable @Valid Long id) {
        Problem problem = problemService.get(id);
        return ProblemDTO.of(problem);
    }

    @PostMapping(value = "/problems")
    public void postProblem(@RequestBody @Valid CreateProblemDTO createProblemDTO) {
        Scooter scooter = scooterService.get(createProblemDTO.scooterId);
        Categories categories = categoriesService.get(createProblemDTO.categoryId);
        ProblemStatus problemStatus = problemStatusService.get(createProblemDTO.problemStatusId);

        this.problemService.add(new Problem(
                null,
                createProblemDTO.name,
                createProblemDTO.description,
                scooter,
                new Coordinate(createProblemDTO.latitude, createProblemDTO.longitude),
                LocalDate.now(),
                categories,
                problemStatus
        ));
    }

    @PutMapping(value = "/problems/{id}")
    public void putReferentOnProblem(@RequestHeader("uuid") UUID uuid, @PathVariable @Valid Long id) {
        Session userSession = sessionService.get(uuid.toString());
        problemService.putReferentOnProblem(userSession.getUser(),id);
    }

    @PutMapping(value = "/problems/{id}/status/{statusId}")
    public void putStatusOnProblem(@RequestHeader("uuid") UUID uuid, @PathVariable @Valid Long id, @PathVariable @Valid Long statusId) {
        Session userSession = sessionService.get(uuid.toString());
        Problem problem = problemService.get(id);
        if (problem.getReferent() == null && !Objects.equals(problem.getReferent().getID(), userSession.getUser().getID())) {
            throw new SimpleServiceException("Only the referent on the problem can close it");
        }
        ProblemStatus problemStatus = problemStatusService.get(statusId);
        if (problemStatus == null) {
            throw new SimpleServiceException("Problem status does not exist");
        }
        problemService.putProblemStatusOnProblem(problemStatus, id);
    }
}
