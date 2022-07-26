package com.scooterjee.app.expostion.user.dto;

import com.scooterjee.app.domain.user.User;
import com.scooterjee.app.expostion.role.dto.RoleDTO;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {

    @NotBlank
    public String email;
    @NotBlank
    public String lastname;
    @NotBlank
    public String firstname;
    @NotBlank
    public String address;
    @NotBlank
    public String phoneNumber;

    public List<RoleDTO> roles;


    public UserDTO(
        String email,
        String lastname,
        String firstname,
        String address,
        String phoneNumber,
        List<RoleDTO> roles
    ) {
        this.email = email;
        this.lastname = lastname;
        this.firstname = firstname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
    }

    public static UserDTO of(User user) {
        return new UserDTO(
            user.getEmailAddress().toString(),
            user.getLastName(),
            user.getFirstName(),
            user.getAddress().toString(),
            user.getPhoneNumber(),
            user.getAssignedRoles()
                .stream()
                .map(role -> new RoleDTO(role.getID(), role.getName()))
                .collect(Collectors.toList())
        );
    }
}
