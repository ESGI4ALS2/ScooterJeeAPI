package com.scooterjee.app.infrastructure.database.user;

import com.scooterjee.app.domain.categories.Categories;
import com.scooterjee.app.domain.role.Role;
import com.scooterjee.app.domain.user.User;
import com.scooterjee.app.domain.vote.Vote;
import com.scooterjee.app.infrastructure.database.address.AddressDB;
import com.scooterjee.app.infrastructure.database.categories.CategoriesDB;
import com.scooterjee.app.infrastructure.database.role.RoleDB;
import com.scooterjee.app.infrastructure.database.vote.VoteDB;
import com.scooterjee.kernel.email.EmailAddress;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Table(name = "user")
@Entity
public class UserDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long user_id;

    @Column(unique = true, columnDefinition = "VARCHAR(150)")
    private String mail;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate registerDate;

    @OneToOne
    private AddressDB address;

    @OneToMany(mappedBy = "voter")
    private List<VoteDB> votesReceived;

    @OneToMany(mappedBy = "referent")
    private List<VoteDB> votesGiven;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "user_role",
        joinColumns = {@JoinColumn(name = "user_id")},
        inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private List<RoleDB> roles;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_categories",
        joinColumns = {@JoinColumn(name = "user_id")},
        inverseJoinColumns = {@JoinColumn(name = "categories_id")}
    )
    private List<CategoriesDB> categories;

    protected UserDB() {
    }

    public UserDB(
        Long user_id,
        String mail, String password,
        String firstName, String lastName,
        String phoneNumber,
        LocalDate registerDate,
        AddressDB address,
        List<RoleDB> roles,
        List<CategoriesDB> categories,
        List<VoteDB> votesGiven,
        List<VoteDB> votesReceived
    ) {
        this.user_id = user_id;
        this.mail = mail;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.registerDate = registerDate;
        this.address = address;
        this.roles = roles;
        this.categories = categories;
        this.votesGiven = votesGiven;
        this.votesReceived = votesReceived;
    }

    public static UserDB of(User user) {
        return new UserDB(
            user.getID(),
            user.getEmailAddress().toString(),
            user.getPassword(),
            user.getFirstName(),
            user.getLastName(),
            user.getPhoneNumber(),
            LocalDate.now(),
            AddressDB.of(user.getAddress()),
            user.getAssignedRoles()
                .stream()
                .map(role -> new RoleDB(
                    role.getID(),
                    role.getName()
                ))
                .collect(Collectors.toList()),
            user.getAssignedCategories()
                .stream()
                .map(categories1 -> new CategoriesDB(
                    categories1.getID(),
                    categories1.getName()
                ))
                .collect(Collectors.toList()),
            user.getVotesReceived()
                .stream()
                .map(VoteDB::of)
                .collect(Collectors.toList()),
            user.getVotesGiven()
                .stream()
                .map(VoteDB::of)
                .collect(Collectors.toList())
        );
    }

    public User toUser() {
        return new User(
            this.getId(),
            this.getFirstName(),
            this.getLastName(),
            this.getPassword(),
            this.getPhoneNumber(),
            new EmailAddress(this.getMail()),
            this.getAddress().toAddress(),
            categories
                .stream()
                .map(categoriesDB -> new Categories(categoriesDB.getCategoriesID(), categoriesDB.getName()))
                .collect(Collectors.toList()),
            roles
                .stream()
                .map(roleDB -> new Role(roleDB.getRoleID(), roleDB.getName()))
                .collect(Collectors.toList()),
            votesReceived
                .stream()
                .map(voteDB -> new Vote(
                    voteDB.getId(),
                    voteDB.getDateOfVote(),
                    voteDB.getVoter().toVoter(),
                    voteDB.getReferent().toVoter(),
                    voteDB.getType().toVoteType()
                ))
                .collect(Collectors.toList()),
            votesGiven
                .stream()
                .map(voteDB -> new Vote(
                    voteDB.getId(),
                    voteDB.getDateOfVote(),
                    voteDB.getVoter().toVoter(),
                    voteDB.getReferent().toVoter(),
                    voteDB.getType().toVoteType()
                ))
                .collect(Collectors.toList())
        );
    }

    public User toVoter() {
        return new User(
            this.getId(),
            this.getFirstName(),
            this.getLastName(),
            this.getPassword(),
            this.getPhoneNumber(),
            new EmailAddress(this.getMail()),
            this.getAddress().toAddress(),
            categories
                .stream()
                .map(categoriesDB -> new Categories(categoriesDB.getCategoriesID(), categoriesDB.getName()))
                .collect(Collectors.toList()),
            roles
                .stream()
                .map(roleDB -> new Role(roleDB.getRoleID(), roleDB.getName()))
                .collect(Collectors.toList()),
            List.of(),
            List.of()
        );
    }

    public Long getId() {
        return user_id;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public AddressDB getAddress() {
        return address;
    }

    public List<RoleDB> getRoles() {
        return roles;
    }

    public void setAddress(AddressDB addressDB) {
        this.address = addressDB;
    }

    public List<CategoriesDB> getCategories() {
        return categories;
    }

    public List<VoteDB> getVotesReceived() {
        return votesReceived;
    }
    public List<VoteDB> getVotesGiven() {
        return votesGiven;
    }
}
