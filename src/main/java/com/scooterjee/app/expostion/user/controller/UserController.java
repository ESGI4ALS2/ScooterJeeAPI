package com.scooterjee.app.expostion.user.controller;

import com.scooterjee.app.domain.address.Address;
import com.scooterjee.app.domain.user.User;
import com.scooterjee.app.expostion.category.dto.CategoryDTO;
import com.scooterjee.app.expostion.error.ErrorHandler;
import com.scooterjee.app.expostion.problem.dto.ProblemDTO;
import com.scooterjee.app.expostion.role.dto.RoleDTO;
import com.scooterjee.app.expostion.user.dto.*;
import com.scooterjee.app.infrastructure.service.AddressService;
import com.scooterjee.app.infrastructure.service.ProblemService;
import com.scooterjee.app.infrastructure.service.UserService;
import com.scooterjee.kernel.email.EmailAddress;
import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageLatLng;
import com.byteowls.jopencage.model.JOpenCageResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController extends ErrorHandler {

    private final UserService userService;

    private final AddressService addressService;

    private final ProblemService problemService;

    @Value("${open_cages.token}")
    private String token;


    public UserController(
        UserService userService,
        AddressService addressService,
        ProblemService problemService
    ) {
        this.userService = userService;
        this.addressService = addressService;
        this.problemService = problemService;
    }

    @GetMapping(value = "/users")
    public List<UserDTO> getUsers() {

        return userService
            .getAll()
            .stream()
            .map(user -> new UserDTO(
                user.getEmailAddress().toString(),
                user.getLastName(),
                user.getFirstName(),
                user.getAddress().toString(),
                user.getPhoneNumber(),
                user.getAssignedRoles()
                    .stream()
                    .map(role -> new RoleDTO(role.getID(), role.getName()))
                    .collect(Collectors.toList())
            ))
            .collect(Collectors.toList()
        );
    }

    @GetMapping(value = "/users/{email}")
    public UserDTO getUserByEmail(@PathVariable String email) {
        User user = userService.getByEmail(new EmailAddress(email));
        return UserDTO.of(user);
    }

    @GetMapping(value = "/users/{email}/categories")
    public List<CategoryDTO> getUserCategories(@PathVariable String email) {
        User user = userService.getByEmail(new EmailAddress(email));
        return user
            .getAssignedCategories()
            .stream()
            .map(categories -> new CategoryDTO(categories.getID(), categories.getName()))
            .collect(Collectors.toList());
    }

    @GetMapping(value = "/users/{email}/roles")
    public List<RoleDTO> getUserRoles(@PathVariable String email) {
        User user = userService.getByEmail(new EmailAddress(email));
        return user
            .getAssignedRoles()
            .stream()
            .map(role -> new RoleDTO(role.getID(), role.getName()))
            .collect(Collectors.toList()
        );
    }

    @GetMapping(value = "/users/{email}/availableproblems")
    public List<ProblemDTO> getUserAvailableProblem(@PathVariable String email) {
        User user = userService.getByEmail(new EmailAddress(email));
        return problemService.getAllAvailable(user).stream()
                .map(ProblemDTO::of)
                .collect(Collectors.toList());
    }

    @PostMapping(value = "/users/{email}/categories/{id}")
    public void addUserCategories(@PathVariable String email, @PathVariable Long id) {
        userService.addCategoryToUser(new EmailAddress(email), id);
    }

    @PostMapping(value = "/users/{email}/categories")
    public void addUserCategories(
        @PathVariable String email,
        @RequestBody @Valid UserCategoriesDTO userCategoriesDTO
    ) {
        for (Long id: userCategoriesDTO.list ) {
            userService.addCategoryToUser(new EmailAddress(email), id);
        }
    }

    @PostMapping(value = "/users/{email}/roles")
    public void addUserRoles(
        @PathVariable String email,
        @RequestBody @Valid UserCategoriesDTO userCategoriesDTO
    ) {
        for (Long id: userCategoriesDTO.list ) {
            userService.addRoleToUser(new EmailAddress(email), id);
        }
    }

    @PostMapping(value = "/users/{email}/roles/{id}")
    public void addUserRoles(
        @PathVariable String email,
        @PathVariable Long id
    ) {
        userService.addRoleToUser(new EmailAddress(email), id);
    }

    @PostMapping(value = "/users")
    public ResponseEntity<CreateUserResponse> addUser(@RequestBody @Valid CreateUserDTO createUserDTO) {
        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder(token);

        String addressAsString = String.format(
            "%s %s, %s",
            createUserDTO.address.number,
            createUserDTO.address.street,
            createUserDTO.address.postalCode
        );

        JOpenCageForwardRequest request = new JOpenCageForwardRequest(addressAsString);

        request.setRestrictToCountryCode("fr");

        JOpenCageResponse response = jOpenCageGeocoder.forward(request);
        JOpenCageLatLng firstResultLatLng = response.getFirstPosition();

        if(null == firstResultLatLng){
            System.out.println("Not working. Setting default values");
            firstResultLatLng = new JOpenCageLatLng();
            firstResultLatLng.setLat(0.0);
            firstResultLatLng.setLng(0.1);
        }

        Address address = new Address(
            null,
            createUserDTO.address.city,
            createUserDTO.address.street,
            createUserDTO.address.number,
            createUserDTO.address.country,
            createUserDTO.address.postalCode,
            firstResultLatLng.getLat(),
            firstResultLatLng.getLng()
        );

        addressService.add(address);

        Long userId = userService.add(
            new User(
                null,
                createUserDTO.firstname,
                createUserDTO.lastname,
                createUserDTO.password,
                createUserDTO.phoneNumber,
                new EmailAddress(createUserDTO.email),
                address
            )
        );

        return new ResponseEntity<>(new CreateUserResponse(userId), HttpStatus.CREATED);

    }

    @DeleteMapping(value = "/users/{email}/roles/{id}")
    public void deleteRoleForUser(
        @PathVariable String email,
        @PathVariable Long id
    ) {
        userService.removeRoleToUser(new EmailAddress(email), id);
    }

    @GetMapping(value = "/referents")
    public ResponseEntity<List<ReferentResponse>> getReferents() {
        return new ResponseEntity<>(userService.getReferents(),HttpStatus.OK);
    }
}
