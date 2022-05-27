package com.scooterjee.app.infrastructure.database.role;

import com.scooterjee.app.domain.role.Role;

import javax.persistence.*;

@Table(name = "role")
@Entity
public class RoleDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleID;

    private String name;

    public RoleDB() {
    }

    public RoleDB(Long roleID, String name) {
        this.roleID = roleID;
        this.name = name;
    }

    public Long getRoleID() {
        return roleID;
    }

    public String getName() {
        return name;
    }

    public static RoleDB of(Role role){
        return new RoleDB(role.getID(), role.getName());
    }

    public Role toRole(){
        return new Role(roleID, name);
    }
}
