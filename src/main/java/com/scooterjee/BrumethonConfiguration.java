package com.scooterjee;

import com.scooterjee.app.domain.address.Address;
import com.scooterjee.app.domain.address.AddressValidator;
import com.scooterjee.app.domain.categories.Categories;
import com.scooterjee.app.domain.categories.CategoriesValidator;
import com.scooterjee.app.domain.problem.Problem;
import com.scooterjee.app.domain.problem.ProblemValidator;
import com.scooterjee.app.domain.problemestatus.ProblemStatus;
import com.scooterjee.app.domain.problemestatus.ProblemStatusValidator;
import com.scooterjee.app.domain.role.Role;
import com.scooterjee.app.domain.role.RoleValidator;
import com.scooterjee.app.domain.scooter.Scooter;
import com.scooterjee.app.domain.scooter.ScooterValidator;
import com.scooterjee.app.domain.scootermodel.ScooterModel;
import com.scooterjee.app.domain.scootermodel.ScooterModelValidator;
import com.scooterjee.app.domain.session.Session;
import com.scooterjee.app.domain.session.SessionValidator;
import com.scooterjee.app.domain.user.User;
import com.scooterjee.app.domain.user.UserValidator;
import com.scooterjee.app.domain.vote.Vote;
import com.scooterjee.app.domain.vote.VoteValidator;
import com.scooterjee.app.domain.vote_type.VoteType;
import com.scooterjee.app.domain.vote_type.VoteTypeValidator;
import com.scooterjee.kernel.Validator;
import com.scooterjee.kernel.email.EmailAddress;
import com.scooterjee.kernel.email.EmailAddressValidator;
import com.scooterjee.kernel.email.SimpleEmailAddressValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BrumethonConfiguration {

    @Bean
    public Validator<ScooterModel> getScooterModelValidator() {
        return new ScooterModelValidator();
    }

    @Bean
    public Validator<Role> getRoleValidator() {
        return new RoleValidator();
    }

    @Bean
    public Validator<Scooter> getScooterValidator() {
        return new ScooterValidator((ScooterModelValidator) getScooterModelValidator(), (UserValidator) getUserValidator());
    }

    @Bean
    public Validator<User> getUserValidator() {
        return new UserValidator((AddressValidator) getAddressValidator(), (EmailAddressValidator) getEmailValidator());
    }

    @Bean
    public Validator<EmailAddress> getEmailValidator() {
        return new SimpleEmailAddressValidator();
    }

    @Bean
    public Validator<Address> getAddressValidator() {
        return new AddressValidator();
    }

    @Bean
    public Validator<Session> getSessionValidator() {
        return new SessionValidator();
    }

    @Bean
    public Validator<Categories> getCategoriesValidator() {
        return new CategoriesValidator();
    }

    @Bean
    public Validator<ProblemStatus> getProblemStatusValidator() {
        return new ProblemStatusValidator();
    }

    @Bean
    public Validator<Problem> getProblemValidator() {
        return new ProblemValidator();
    }

    @Bean
    public Validator<Vote> getVoteValidator() {
        return new VoteValidator();
    }

    @Bean
    public Validator<VoteType> getVoteTypeValidator() {
        return new VoteTypeValidator();
    }
}
