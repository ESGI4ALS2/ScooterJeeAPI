package com.scooterjee.app.domain.categories;

import com.scooterjee.app.domain.user.User;
import com.scooterjee.kernel.Repository;

import java.util.List;

public interface CategoriesRepository extends Repository<Categories, Long> {

    List<User> getAllCategoriesUsers(Long id);
}
