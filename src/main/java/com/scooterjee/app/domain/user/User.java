package com.scooterjee.app.domain.user;

import com.scooterjee.app.domain.address.Address;
import com.scooterjee.app.domain.categories.Categories;
import com.scooterjee.app.domain.problem.Problem;
import com.scooterjee.app.domain.role.Role;
import com.scooterjee.kernel.Entity;
import com.scooterjee.kernel.email.EmailAddress;

import java.util.ArrayList;
import java.util.List;

public class User extends Entity<Long> {

    private final String firstName;
    private final String lastName;
    private final String password;
    private final String phoneNumber;
    private final EmailAddress emailAddress;
    private final Address address;

    private final List<Categories> assignedCategories;

    private final List<Role> assignedRoles;

    public User(Long id,
                String firstName, String lastName,
                String password,
                String phoneNumber,
                EmailAddress emailAddress,
                Address address,
                List<Categories> assignedCategories,
                List<Role> assignedRoles) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.address = address;
        this.assignedCategories = assignedCategories;
        this.assignedRoles = assignedRoles;
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
            new ArrayList<>()
        );
    }

    //TODO déplacer dans ProblemService
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

        //earth radius in meters = 6366000
        //TODO constante
        return 6366000 * tt;
    }

    //TODO déplacer dans ProblemService
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

        //10000 = 10km j'imagine
        //TODO constante
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
}
