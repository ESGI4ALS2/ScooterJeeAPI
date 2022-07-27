package com.scooterjee.app.infrastructure.repository;

import com.scooterjee.app.domain.vote_type.VoteType;
import com.scooterjee.app.domain.vote_type.VoteTypeRepository;
import com.scooterjee.app.infrastructure.database.vote_type.VoteTypeDB;
import com.scooterjee.app.infrastructure.database.vote_type.VoteTypeDBRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InDBVoteTypeRepository implements VoteTypeRepository {

    private final VoteTypeDBRepository dbRepository;

    public InDBVoteTypeRepository(VoteTypeDBRepository dbRepository) {
        this.dbRepository = dbRepository;
    }

    @Override
    public Optional<VoteType> get(Long key) {
        return dbRepository.findById(key).map(VoteTypeDB::toVoteType);
    }

    @Override
    public Long add(VoteType value) {
        VoteTypeDB voteTypeDB = dbRepository.save(VoteTypeDB.of(value));
        value.setId(voteTypeDB.getId());

        return voteTypeDB.getId();
    }

    @Override
    public boolean update(VoteType value) {
        if(!dbRepository.existsById(value.getID())) {
            return false;
        }
        dbRepository.save(VoteTypeDB.of(value));

        return true;
    }

    @Override
    public boolean remove(Long value) {
        dbRepository.deleteById(value);
        return !dbRepository.existsById(value);
    }

    @Override
    public List<VoteType> getAll() {
        List<VoteType> list = new ArrayList<>();
        dbRepository.findAll().forEach(voteTypeDB -> list.add(voteTypeDB.toVoteType()));

        return list;
    }
}
