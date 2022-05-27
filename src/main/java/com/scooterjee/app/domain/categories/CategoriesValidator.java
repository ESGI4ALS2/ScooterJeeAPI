package com.scooterjee.app.domain.categories;

import com.scooterjee.app.domain.categories.exception.InvalidCategoriesException;
import com.scooterjee.kernel.Validator;

public class CategoriesValidator implements Validator<Categories> {

    @Override
    public void validate(Categories categories) {
        if(categories == null) {
            throw new IllegalArgumentException("Categories to validate is null");
        }

        if(categories.getName() == null || categories.getName().isEmpty()){
            throw new InvalidCategoriesException("categories name can not be empty");
        }

    }
}
