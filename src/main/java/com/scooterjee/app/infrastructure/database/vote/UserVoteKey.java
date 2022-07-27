package com.scooterjee.app.infrastructure.database.vote;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserVoteKey implements Serializable {
    @Column(name = "voter_id")
    Long voterId;

    @Column(name = "referent_id")
    Long referentId;

    public UserVoteKey(Long voterId, Long referentId) {
        this.voterId = voterId;
        this.referentId = referentId;
    }

    public UserVoteKey() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserVoteKey that = (UserVoteKey) o;
        return Objects.equals(voterId, that.voterId) && Objects.equals(referentId, that.referentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voterId, referentId);
    }
}
