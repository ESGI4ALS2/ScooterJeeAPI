package com.scooterjee.app.infrastructure.database.vote;

import com.scooterjee.app.domain.vote.Vote;
import com.scooterjee.app.infrastructure.database.user.UserDB;
import com.scooterjee.app.infrastructure.database.vote_type.VoteTypeDB;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "vote")
@Entity
public class VoteDB {
    @EmbeddedId
    private UserVoteKey voteId;

    private LocalDate dateOfVote;

    @ManyToOne
    @MapsId("voterId")
    @JoinColumn(name = "voter_id")
    private UserDB voter;

    @ManyToOne
    @MapsId("referentId")
    @JoinColumn(name = "referent_id")
    private UserDB referent;

    @OneToOne
    private VoteTypeDB type;

    public VoteDB(
        LocalDate dateOfVote,
        UserDB voter,
        UserDB referent,
        VoteTypeDB type
    ) {
        this.voteId = new UserVoteKey(
            voter.getId(),
            referent.getId()
        );
        this.dateOfVote = dateOfVote;
        this.voter = voter;
        this.referent = referent;
        this.type = type;
    }

    public VoteDB() {

    }

    public Vote toVote() {
        return new Vote(
            0L,
            this.dateOfVote,
            this.voter.toUser(),
            this.referent.toUser(),
            this.type.toVoteType()
        );
    }

    public static VoteDB of(Vote vote) {
        return new VoteDB(
            vote.getVoteDate(),
            UserDB.of(vote.getVoter()),
            UserDB.of(vote.getReferent()),
            VoteTypeDB.of(vote.getType())
        );
    }

    public UserDB getVoter() {
        return this.voter;
    }

    public LocalDate getDateOfVote() {
        return dateOfVote;
    }

    public UserDB getReferent() {
        return referent;
    }

    public VoteTypeDB getType() {
        return type;
    }

    public Long getId() {
        //TODO Gérer composite key
        return 0L;
    }
}
