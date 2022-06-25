package com.scooterjee.app.infrastructure.database.categories;

import com.scooterjee.app.domain.categories.Categories;
import com.scooterjee.app.infrastructure.database.user.UserDB;

import javax.persistence.*;
import java.util.List;

@Table(name = "categories")
@Entity
public class CategoriesDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categories_id")
    private Long categoriesID;

    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_categories",
        joinColumns = { @JoinColumn(name = "user_id") },
        inverseJoinColumns = { @JoinColumn(name = "categories_id") }
    )
    private List<UserDB> assignedUser;

    public CategoriesDB() {
    }

    public CategoriesDB(Long categoriesID, String name) {
        this.categoriesID = categoriesID;
        this.name = name;
    }

    public Long getCategoriesID() {
        return categoriesID;
    }

    public String getName() {
        return name;
    }

    public static CategoriesDB of(Categories categories){
        return new CategoriesDB(categories.getID(), categories.getName());
    }

    public Categories toCategories(){
        return new Categories(categoriesID, name);
    }

    public List<UserDB> getAssignedUser() {
        return assignedUser;
    }
}
