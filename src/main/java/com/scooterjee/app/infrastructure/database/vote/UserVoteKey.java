package com.scooterjee.app.infrastructure.database.vote;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

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

    //Implémenter hashcode et equals
}
