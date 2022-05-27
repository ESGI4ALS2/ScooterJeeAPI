package com.scooterjee.app.expostion.user.controller;

import com.scooterjee.app.domain.address.Address;
import com.scooterjee.app.domain.user.User;
import com.scooterjee.app.expostion.category.dto.CategoryDTO;
import com.scooterjee.app.expostion.error.ErrorHandler;
import com.scooterjee.app.expostion.problem.dto.ProblemDTO;
import com.scooterjee.app.expostion.role.dto.RoleDTO;
import com.scooterjee.app.expostion.user.dto.CreateUserDTO;
import com.scooterjee.app.expostion.user.dto.UserCategoriesDTO;
import com.scooterjee.app.expostion.user.dto.UserDTO;
import com.scooterjee.app.infrastructure.service.AddressService;
import com.scooterjee.app.infrastructure.service.ProblemService;
import com.scooterjee.app.infrastructure.service.UserService;
import com.scooterjee.kernel.email.EmailAddress;
import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageLatLng;
import com.byteowls.jopencage.model.JOpenCageResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController extends ErrorHandler {

    private final UserService userService;

    private final AddressService addressService;

    private final ProblemService problemService;

    @Value("${open_cages.token}")
    private String token;


    public UserController(UserService userService, AddressService addressService, ProblemService problemService) {
        this.userService = userService;
        this.addressService = addressService;
        this.problemService = problemService;
    }

    @GetMapping(value = "/users")
    public List<UserDTO> getUsers() {
        return userService.getAll().stream()
                .map(user -> new UserDTO(
                        user.getEmailAddress().toString(),
                        user.getLastName(),
                        user.getFirstName(),
                        user.getAddress().toString(),
                        user.getPhoneNumber(),
                        user.getAssignedRoles().stream().map(role -> new RoleDTO(role.getID(), role.getName())).collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/users/{email}")
    public UserDTO getUserByEmail(@PathVariable @Valid String email) {
        User user = userService.getByEmail(new EmailAddress(email));
        return UserDTO.of(user);
    }

    @GetMapping(value = "/users/{email}/categories")
    public List<CategoryDTO> getUserCategories(@PathVariable @Valid String email) {
        User user = userService.getByEmail(new EmailAddress(email));
        return user.getAssignedCategories().stream().map(categories -> new CategoryDTO(categories.getID(), categories.getName())).collect(Collectors.toList());
    }

    @GetMapping(value = "/users/{email}/roles")
    public List<RoleDTO> getUserRoles(@PathVariable @Valid String email) {
        User user = userService.getByEmail(new EmailAddress(email));
        return user.getAssignedRoles().stream().map(role -> new RoleDTO(role.getID(), role.getName())).collect(Collectors.toList());
    }

    @GetMapping(value = "/users/{email}/availableproblems")
    public List<ProblemDTO> getUserAvailableProblem(@PathVariable @Valid String email) {
        User user = userService.getByEmail(new EmailAddress(email));
        return problemService.getAllAvailable(user).stream()
                .map(ProblemDTO::of)
                .collect(Collectors.toList());
    }

    @PostMapping(value = "/users/{email}/categories/{id}")
    public void addUserCategories(@PathVariable @Valid String email, @PathVariable @Valid Long id) {
        userService.addCategoryToUser(new EmailAddress(email), id);
    }

    @PostMapping(value = "/users/{email}/categories")
    public void addUserCategories(@PathVariable @Valid String email, @RequestBody @Valid UserCategoriesDTO userCategoriesDTO) {
        for (Long id: userCategoriesDTO.list ) {
            userService.addCategoryToUser(new EmailAddress(email), id);
        }
    }

    @PostMapping(value = "/users/{email}/roles")
    public void addUserRoles(@PathVariable @Valid String email, @RequestBody @Valid UserCategoriesDTO userCategoriesDTO) {
        for (Long id: userCategoriesDTO.list ) {
            userService.addRoleToUser(new EmailAddress(email), id);
        }
    }

    @PostMapping(value = "/users/{email}/roles/{id}")
    public void addUserRoles(@PathVariable @Valid String email, @PathVariable @Valid Long id) {
        userService.addRoleToUser(new EmailAddress(email), id);
    }

    @PostMapping(value = "/users")
    public void addUser(@RequestBody @Valid CreateUserDTO createUserDTO) {
        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder(token);
        JOpenCageForwardRequest request = new JOpenCageForwardRequest(
                createUserDTO.address.number + " " +
                        createUserDTO.address.street + ", " +
                        createUserDTO.address.postalCode);
        request.setRestrictToCountryCode("fr");

        JOpenCageResponse response = jOpenCageGeocoder.forward(request);
        JOpenCageLatLng firstResultLatLng = response.getFirstPosition();

        Address address = new Address(
                null,
                createUserDTO.address.city,
                createUserDTO.address.street,
                createUserDTO.address.number,
                createUserDTO.address.country,
                createUserDTO.address.postalCode,
                firstResultLatLng.getLat(),
                firstResultLatLng.getLng());

        addressService.add(address);

        userService.add(new User(
                null,
                createUserDTO.firstname,
                createUserDTO.lastname,
                createUserDTO.password,
                createUserDTO.phoneNumber,
                new EmailAddress(createUserDTO.email),
                address
        ));
    }

    @DeleteMapping(value = "/users/{email}/roles/{id}")
    public void deleteRoleForUser(@PathVariable @Valid String email, @PathVariable @Valid Long id) {
        userService.removeRoleToUser(new EmailAddress(email), id);
    }
}
