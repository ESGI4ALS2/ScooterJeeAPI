package com.scooterjee.app.infrastructure.repository;

import com.scooterjee.app.domain.vote.Vote;
import com.scooterjee.app.domain.vote.VoteRepository;
import com.scooterjee.app.infrastructure.database.vote.VoteDB;
import com.scooterjee.app.infrastructure.database.vote.VoteDBRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InDBVoteRepository implements VoteRepository {

    private final VoteDBRepository dbRepository;

    public InDBVoteRepository(VoteDBRepository dbRepository) {
        this.dbRepository = dbRepository;
    }

    @Override
    public Optional<Vote> get(Long key) {
        return dbRepository.findById(key).map(VoteDB::toVote);
    }

    @Override
    public Long add(Vote value) {
        return dbRepository.save(VoteDB.of(value)).getId();
    }

    @Override
    public boolean update(Vote value) {
        return dbRepository.save(VoteDB.of(value)) != null;
    }

    @Override
    public boolean remove(Long value) {
        dbRepository.deleteById(value);
        return !dbRepository.existsById(value);
    }

    @Override
    public List<Vote> getAll() {
        return null;
    }
}
