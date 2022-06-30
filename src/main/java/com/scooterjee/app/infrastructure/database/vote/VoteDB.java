package com.scooterjee.app.infrastructure.database.vote;

import com.scooterjee.app.infrastructure.database.user.UserDB;
import com.scooterjee.app.infrastructure.database.vote_type.VoteTypeDB;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "vote")
@Entity
public class VoteDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_id")
    private Long voteId;

    private LocalDate dateOfVote;

    //OneToMany ??
    @OneToOne
    private UserDB voter;

    @OneToOne
    private UserDB referent;

    @OneToOne
    private VoteTypeDB type;
}
