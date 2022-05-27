package com.scooterjee.app.infrastructure.service;

import com.scooterjee.app.domain.role.Role;
import com.scooterjee.app.domain.role.RoleRepository;
import com.scooterjee.kernel.SimpleService;
import com.scooterjee.kernel.Validator;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends SimpleService<RoleRepository, Role, Long> {
    public RoleService(RoleRepository repository, Validator<Role> validator) {
        super(repository, validator, "role");
    }
}
