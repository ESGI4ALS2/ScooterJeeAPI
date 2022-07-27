package com.scooterjee.app.domain.categories;

import com.scooterjee.app.domain.user.User;
import com.scooterjee.kernel.Repository;

import java.util.List;
import java.util.Optional;

public interface CategoriesRepository extends Repository<Categories, Long> {

    Optional<Categories> getByName(String key);

    List<User> getAllCategoriesUsers(Long id);
}
