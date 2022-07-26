package com.scooterjee.app.expostion.user.dto;

public class ReferentResponse {
    public Long id;
    public String lastName;
    public String firstname;
    public float rating;

    public ReferentResponse(
        Long id,
        String lastName,
        String firstname,
        float rating
    ) {
        this.id = id;
        this.lastName = lastName;
        this.firstname = firstname;
        this.rating = rating;
    }
}
