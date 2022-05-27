package com.scooterjee.app.domain.session;

import com.scooterjee.kernel.Repository;

import java.util.Optional;
import java.util.UUID;

public interface SessionRepository extends Repository<Session, String> {
    Optional<Session> get(UUID id);

    void removeAllForUserID(Long userID);
}
