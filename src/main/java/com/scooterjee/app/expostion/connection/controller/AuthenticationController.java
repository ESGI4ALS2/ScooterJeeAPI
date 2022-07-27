package com.scooterjee.app.expostion.connection.controller;

import com.scooterjee.app.domain.session.Session;
import com.scooterjee.app.domain.user.User;
import com.scooterjee.app.expostion.connection.dto.SessionDTO;
import com.scooterjee.app.expostion.connection.dto.UserConnectionDTO;
import com.scooterjee.app.expostion.error.ErrorHandler;
import com.scooterjee.app.infrastructure.database.user.UserDB;
import com.scooterjee.app.infrastructure.service.SessionService;
import com.scooterjee.app.infrastructure.service.UserService;
import com.scooterjee.kernel.email.EmailAddress;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Objects;

@RestController
public class AuthenticationController extends ErrorHandler {

    private final SessionService sessionService;
    private final UserService userService;
    private static final int EXPIRATION_OF_TOKEN_IN_HOUR = 4;

    public AuthenticationController(SessionService sessionService, UserService inDBUserRepository) {
        this.sessionService = sessionService;
        this.userService = inDBUserRepository;
    }

    @PostMapping(value = "/login")
    public SessionDTO login(@RequestBody @Valid UserConnectionDTO userConnectionDTO) {
        EmailAddress emailAddress = new EmailAddress(userConnectionDTO.email);
        User user = userService.getByEmail(emailAddress);
        UserDB userDB = UserDB.of(user);

        if(Objects.equals(user.getPassword(), userConnectionDTO.password)) {
            sessionService.removeAllForUserID(userDB.getId());

            Session session = new Session(user, LocalDateTime.now().plusHours(EXPIRATION_OF_TOKEN_IN_HOUR));
            sessionService.add(session);

            return new SessionDTO(session.getTokenID()) ;
        }
        return null;
    }
}
