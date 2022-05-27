package com.scooterjee.app.infrastructure.database.problemstatus;

import com.scooterjee.app.domain.problemestatus.ProblemStatus;

import javax.persistence.*;

@Table(name = "problem_status")
@Entity
public class ProblemStatusDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_status_id")
    private Long problemStatusID;

    private String name;

    public ProblemStatusDB() {
    }

    public ProblemStatusDB(Long problemStatusID, String name) {
        this.problemStatusID = problemStatusID;
        this.name = name;
    }

    public Long getProblemStatusID() {
        return problemStatusID;
    }

    public String getName() {
        return name;
    }

    public static ProblemStatusDB of(ProblemStatus problemStatus){
        return new ProblemStatusDB(problemStatus.getID(), problemStatus.getName());
    }

    public ProblemStatus toProblemStatus(){
        return new ProblemStatus(problemStatusID, name);
    }
}
