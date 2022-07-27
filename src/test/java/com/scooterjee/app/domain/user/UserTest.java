package com.scooterjee.app.domain.user;

import com.scooterjee.app.domain.address.Address;
import com.scooterjee.app.domain.categories.Categories;
import com.scooterjee.app.domain.problem.Problem;
import com.scooterjee.app.domain.problemestatus.ProblemStatus;
import com.scooterjee.app.domain.scooter.Scooter;
import com.scooterjee.app.domain.scootermodel.ScooterModel;
import com.scooterjee.kernel.coordinate.Coordinate;
import com.scooterjee.kernel.email.EmailAddress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.scooterjee.utils.Utils.defaultValidUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserTest {

    Problem problem;

    @BeforeEach
    void setUp() {
        problem = new Problem(
            1L,
            "name",
            "desc",
            new Scooter(1L, "serial", new ScooterModel(1L, "name"), defaultValidUser, LocalDate.of(2022, 7, 27)),
            new Coordinate(48.8566, 2.3522),
            LocalDate.of(2022, 7,26),
            new Categories(1L,"cate"),
            new ProblemStatus(1L, "statusName"),
            defaultValidUser
        );
    }

    @Test
    void should_be_available_for_problem_if_user_is_under_radius() {
        User user = new User(2L, "bob", "bob", "p",
                             "0601020304",
                             new EmailAddress("test@test.com"),
                             new Address(1L, "", "", "", "", "", 48.851852, 2.373299));
        assertThat(user.isUserAvailableForProblem(problem)).isEqualTo(true);
    }

    @Test
    void should_not_be_available_for_problem_if_user_is_over_radius() {
        User user = new User(2L, "bob", "bob", "p",
                             "0601020304",
                             new EmailAddress("test@test.com"),
                             new Address(1L, "", "", "", "", "", 51.5072, 0.1276));
        assertThat(user.isUserAvailableForProblem(problem)).isEqualTo(false);
    }
}