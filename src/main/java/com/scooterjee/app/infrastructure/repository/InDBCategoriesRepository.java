package com.scooterjee.app.infrastructure.repository;

import com.scooterjee.app.domain.categories.Categories;
import com.scooterjee.app.domain.categories.CategoriesRepository;
import com.scooterjee.app.domain.user.User;
import com.scooterjee.app.infrastructure.database.categories.CategoriesDB;
import com.scooterjee.app.infrastructure.database.categories.CategoriesDBRepository;
import com.scooterjee.app.infrastructure.database.user.UserDB;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InDBCategoriesRepository implements CategoriesRepository {

    private final CategoriesDBRepository categoriesDBRepository;

    public InDBCategoriesRepository(CategoriesDBRepository categoriesDBRepository) {
        this.categoriesDBRepository = categoriesDBRepository;
    }

    @Override
    public Optional<Categories> get(Long key) {
        return categoriesDBRepository.findById(key).map(CategoriesDB::toCategories);
    }

    @Override
    public Long add(Categories value) {
        CategoriesDB categoriesDB = categoriesDBRepository.save(CategoriesDB.of(value));
        value.setId(categoriesDB.getCategoriesID());
        return categoriesDB.getCategoriesID();
    }

    @Override
    public boolean update(Categories value) {
        if (!categoriesDBRepository.existsById(value.getID())) {
            return false;
        }
        categoriesDBRepository.save(CategoriesDB.of(value));
        return true;
    }

    @Override
    public boolean remove(Long value) {
        categoriesDBRepository.deleteById(value);
        return !categoriesDBRepository.existsById(value);
    }

    @Override
    public List<Categories> getAll() {
        List<Categories> result = new ArrayList<>();
        categoriesDBRepository.findAll().forEach(categoriesDB -> result.add(categoriesDB.toCategories()));
        return result;
    }

    @Override
    public List<User> getAllCategoriesUsers(Long id) {
        Optional<CategoriesDB> categoriesDB = categoriesDBRepository.findById(id);

        if(categoriesDB.isEmpty()){
            return List.of();
        }

        return categoriesDB.get().getAssignedUser().stream().map(UserDB::toUser).collect(Collectors.toList());
    }
}
