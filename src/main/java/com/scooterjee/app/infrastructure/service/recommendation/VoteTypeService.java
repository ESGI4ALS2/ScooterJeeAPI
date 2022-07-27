package com.scooterjee.app.infrastructure.service.recommendation;

import com.scooterjee.app.domain.vote_type.VoteType;
import com.scooterjee.app.domain.vote_type.VoteTypeRepository;
import com.scooterjee.kernel.SimpleService;
import com.scooterjee.kernel.Validator;
import org.springframework.stereotype.Service;

@Service
public class VoteTypeService extends SimpleService<VoteTypeRepository, VoteType, Long> {

    public VoteTypeService(
        VoteTypeRepository repository,
        Validator<VoteType> validator
    ) {
        super(repository, validator, "vote_type");
    }

    @Override
    public Long add(VoteType value) {
        return super.add(value);
    }
}
