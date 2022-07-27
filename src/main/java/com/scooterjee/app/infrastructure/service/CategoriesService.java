package com.scooterjee.app.infrastructure.service;

import com.scooterjee.app.domain.categories.Categories;
import com.scooterjee.app.domain.categories.CategoriesRepository;
import com.scooterjee.app.domain.user.User;
import com.scooterjee.kernel.SimpleService;
import com.scooterjee.kernel.Validator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesService extends SimpleService<CategoriesRepository, Categories, Long> {
    public CategoriesService(CategoriesRepository repository, Validator<Categories> validator) {
        super(repository, validator, "categories");
    }

    public List<User> getAllCategoriesUsers(Long categoryID) {
        return repository.getAllCategoriesUsers(categoryID);
    }

    @Override
    public Long add(Categories value) {
        if (repository.getByName(value.getName()).isPresent()) {
            throw getExceptionWhenObjectAlreadyPresent(0L);
        }
        return super.add(value);
    }
}
