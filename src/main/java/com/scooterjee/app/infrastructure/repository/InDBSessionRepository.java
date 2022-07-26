package com.scooterjee.app.infrastructure.repository;

import com.scooterjee.app.domain.session.Session;
import com.scooterjee.app.domain.session.SessionRepository;
import com.scooterjee.app.infrastructure.database.session.SessionDB;
import com.scooterjee.app.infrastructure.database.session.SessionDBRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@org.springframework.stereotype.Repository
public class InDBSessionRepository implements SessionRepository {

    private final SessionDBRepository dbRepository;
    private final InDBUserRepository inDBUserRepository;

    public InDBSessionRepository(SessionDBRepository dbRepository, InDBUserRepository inDBUserRepository) {
        this.dbRepository = dbRepository;
        this.inDBUserRepository = inDBUserRepository;
    }

    @Override
    public Optional<Session> get(String key) {
        return dbRepository.findById(key).map(SessionDB::toSession);
    }

    @Override
    public String add(Session value) {
        SessionDB sessionDB = dbRepository.save(SessionDB.of(value));
        value.setId(sessionDB.getTokenId());
        return sessionDB.getTokenId();
    }

    @Override
    public boolean update(Session value) {
        if (!dbRepository.existsById(value.getID())) {
            return false;
        }
        dbRepository.save(SessionDB.of(value));
        return true;
    }

    @Override
    public boolean remove(String value) {
        dbRepository.deleteById(value);
        return !dbRepository.existsById(value);
    }

    @Override
    public List<Session> getAll() {
        List<Session> list = new ArrayList<>();

        dbRepository.findAll().forEach(sessionDB -> list.add( sessionDB.toSession()));

        return list;
    }

    @Override
    public void removeAllForUserID(Long userID) {
        dbRepository.deleteAllByUserID(userID);
    }

    @Override
    public Optional<Session> get(UUID id) {
        return dbRepository.findById(id.toString()).map(SessionDB::toSession);
    }
}
