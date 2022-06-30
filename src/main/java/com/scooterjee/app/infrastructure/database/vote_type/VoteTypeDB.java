package com.scooterjee.app.infrastructure.database.vote_type;

import javax.persistence.*;

@Table(name = "vote_type")
@Entity
public class VoteTypeDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_type_id")
    private Long voteTypeId;

    private String name;
}
