package com.scooterjee.app.infrastructure.database.session;

import com.scooterjee.app.domain.session.Session;
import com.scooterjee.app.infrastructure.database.user.UserDB;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "session")
@Entity
public class SessionDB {
    @Id
    private Long sessionId;

    //Ne peut pas être l'identifiant car lance une exception : champ trop long à la création de la base
    private String tokenId;

    @OneToOne(fetch = FetchType.LAZY)
    private UserDB user;

    private LocalDateTime expirationDate;

    protected SessionDB() {
    }

    public SessionDB(String tokenId, UserDB user, LocalDateTime expirationDate) {
        this.tokenId = tokenId;
        this.user = user;
        this.expirationDate = expirationDate;
    }

    protected SessionDB(UserDB user, LocalDateTime expirationDate) {
        this.user = user;
        this.expirationDate = expirationDate;
    }

    public String getTokenId() {
        return tokenId;
    }

    public UserDB getUser() {
        return user;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public static SessionDB of(Session session) {
        SessionDB sessionDB = new SessionDB(session.getTokenID(), UserDB.of(session.getUser()), session.getExpirationDate());
        if (sessionDB.tokenId == null) {
            sessionDB.tokenId = java.util.UUID.randomUUID().toString();
        }
        return sessionDB;
    }

    public Session toSession() {
        return new Session(tokenId, user.toUser(), expirationDate);
    }

}
