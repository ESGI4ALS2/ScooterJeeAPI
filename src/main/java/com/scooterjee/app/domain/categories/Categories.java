package com.scooterjee.app.domain.categories;

import com.scooterjee.app.domain.user.User;
import com.scooterjee.kernel.Entity;

import java.util.ArrayList;
import java.util.List;

public class Categories extends Entity<Long> {

    private final String name;

    private final List<User> users;

    public Categories(Long id, String name, List<User> users) {
        super(id);
        this.name = name;
        this.users = users;
    }

    public Categories(Long id, String name) {
        this(id, name, new ArrayList<>());
    }

    public String getName(){
        return name;
    }

    public List<User> getUsers() {
        return users;
    }
}
