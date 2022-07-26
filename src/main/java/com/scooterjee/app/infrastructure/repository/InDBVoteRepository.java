package com.scooterjee.app.infrastructure.repository;

import com.scooterjee.app.domain.vote.Vote;
import com.scooterjee.app.domain.vote.VoteRepository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public class InDBVoteRepository implements VoteRepository {
    @Override
    public Optional<Vote> get(Long key) {
        return Optional.empty();
    }

    @Override
    public Long add(Vote value) {
        return null;
    }

    @Override
    public boolean update(Vote value) {
        return false;
    }

    @Override
    public boolean remove(Long value) {
        return false;
    }

    @Override
    public List<Vote> getAll() {
        return null;
    }
}
