package com.scooterjee.app.expostion.role.controller;


import com.scooterjee.app.domain.role.Role;
import com.scooterjee.app.expostion.error.ErrorHandler;
import com.scooterjee.app.expostion.role.dto.CreateRoleDTO;
import com.scooterjee.app.expostion.role.dto.RoleDTO;
import com.scooterjee.app.infrastructure.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RoleController extends ErrorHandler {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping(value = "/roles")
    public List<RoleDTO> getUsers() {
        return roleService
            .getAll()
            .stream()
            .map(role -> new RoleDTO(role.getID(), role.getName()))
            .collect(Collectors.toList()
        );
    }

    @GetMapping(value = "/roles/{id}")
    public RoleDTO getUsers(@PathVariable Long id) {
        Role role = roleService.get(id);
        return new RoleDTO(role.getID(),role.getName());
    }

    @PostMapping(value = "/roles")
    public void postCategory(@RequestBody @Valid CreateRoleDTO createRoleDTO) {
        roleService.add(new Role(null,createRoleDTO.name));
    }

    @DeleteMapping(value = "/roles/{id}")
    public void deleteCategory(@PathVariable Long id) {
        roleService.remove(id);
    }
}
