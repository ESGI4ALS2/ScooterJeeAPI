package com.scooterjee.app.infrastructure.database.vote_type;

import com.scooterjee.app.domain.vote_type.VoteType;

import javax.persistence.*;

@Table(name = "vote_type")
@Entity
public class VoteTypeDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_type_id")
    private Long voteTypeId;

    private String name;

    public VoteTypeDB(Long voteTypeId, String name) {
        this.voteTypeId = voteTypeId;
        this.name = name;
    }

    public VoteTypeDB() {

    }

    public VoteType toVoteType() {
        return new VoteType(
          this.voteTypeId,
          this.name
        );
    }

    public static VoteTypeDB of(VoteType type) {
        return new VoteTypeDB(
            type.getID(),
            type.getName()
        );
    }

    public Long getId() {
        return this.voteTypeId;
    }

    public String getName() {
        return name;
    }
}
