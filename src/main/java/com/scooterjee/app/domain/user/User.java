package com.scooterjee.app.domain.user;

import com.scooterjee.app.domain.address.Address;
import com.scooterjee.app.domain.categories.Categories;
import com.scooterjee.app.domain.problem.Problem;
import com.scooterjee.app.domain.role.Role;
import com.scooterjee.app.domain.vote.Vote;
import com.scooterjee.app.domain.vote_type.VoteType;
import com.scooterjee.kernel.Entity;
import com.scooterjee.kernel.email.EmailAddress;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User extends Entity<Long> {

    private final static int RADIUS = 6366000;

    private final String firstName;
    private final String lastName;
    private final String password;
    private final String phoneNumber;
    private final EmailAddress emailAddress;
    private final Address address;

    private final List<Categories> assignedCategories;

    private final List<Role> assignedRoles;

    private final List<Vote> voteReceived;

    private final List<Vote> voteGiven;

    public User(
        Long id,
        String firstName,
        String lastName,
        String password,
        String phoneNumber,
        EmailAddress emailAddress,
        Address address,
        List<Categories> assignedCategories,
        List<Role> assignedRoles,
        List<Vote> voteGiven,
        List<Vote> voteReceived
    ) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.address = address;
        this.assignedCategories = assignedCategories;
        this.assignedRoles = assignedRoles;
        this.voteReceived = voteReceived;
        this.voteGiven = voteGiven;
    }

    public User(
        Long id,
        String firstName,
        String lastName,
        String password,
        String phoneNumber,
        EmailAddress emailAddress,
        Address address
    ) {
        this(
            id,
            firstName,
            lastName,
            password,
            phoneNumber,
            emailAddress,
            address,
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>()
        );
    }

    public User(
        String firstName,
        String lastName,
        String password,
        String phoneNumber,
        EmailAddress emailAddress,
        Address address
    ) {
        this(
            null,
            firstName,
            lastName,
            password,
            phoneNumber,
            emailAddress,
            address,
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>()
        );
    }

    private double meterDistanceToProblem(
        double lat_a,
        double lng_a,
        double lat_b,
        double lng_b
    ) {
        float pk = (float) (180.f/Math.PI);

        double a1 = lat_a / pk;
        double a2 = lng_a / pk;
        double b1 = lat_b / pk;
        double b2 = lng_b / pk;

        double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
        double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
        double t3 = Math.sin(a1) * Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);

        return RADIUS * tt;
    }

    public boolean isUserAvailableForProblem(Problem problem){

        if( problem.getReferent() != null){
            return false;
        }

        double distanceToProblem = meterDistanceToProblem(
            problem.getCoordinate().getLatitude(),
            problem.getCoordinate().getLongitude(),
            this.address.getLatitude(),
            this.address.getLongitude()
        );

        return !(distanceToProblem >= 10000);
    }

    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public List<Categories> getAssignedCategories() {
        return assignedCategories;
    }

    public List<Role> getAssignedRoles() {
        return assignedRoles;
    }

    public List<Vote> getVotesReceived() {
        return voteReceived;
    }
    public List<Vote> getVotesGiven() {
        return voteGiven;
    }

    public float getRating() {
        return getVotesReceived().stream()
            .map(vote -> {
                int sign = vote.getType().equals(VoteType.UP_VOTE) ? 1 : -1;
                LocalDate dateOfVote = vote.getVoteDate();
                if(dateOfVote.isAfter(LocalDate.now().minusMonths(1))) {
                    return 1f * sign;
                } else if(dateOfVote.isAfter(LocalDate.now().minusMonths(3))) {
                    return 0.75f * sign;
                } else if(dateOfVote.isAfter(LocalDate.now().minusMonths(6))) {
                    return 0.5f * sign;
                } else {
                    return 0.25f * sign;
                }

            })
            .reduce(Float::sum)
            .orElse(0f);
    }
}
