package com.test.restful.controller;

import com.test.restful.exception.ResourceNotFoundException;
import com.test.restful.model.ApiResponse;
import com.test.restful.model.User;
import com.test.restful.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing users
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get all users with pagination
     * 
     * @param pageable Pagination information
     * @return Paginated list of users
     */
    @GetMapping("/paginated")
    @Operation(summary = "Get all users with pagination", description = "Returns a paginated list of users")
    public ResponseEntity<com.test.restful.model.ApiResponse<Page<User>>> getAllUsersPaginated(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        
        logger.info("Fetching paginated users with page: {}, size: {}", 
                pageable.getPageNumber(), pageable.getPageSize());
        
        Page<User> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(com.test.restful.model.ApiResponse.success(users, "Users retrieved successfully"));
    }

    /**
     * Get all users
     * 
     * @return List of all users
     */
    @GetMapping
    @Operation(summary = "Get all users", description = "Returns a list of all users")
    public ResponseEntity<com.test.restful.model.ApiResponse<List<EntityModel<User>>>> getAllUsers() {
        logger.info("Fetching all users");
        
        List<User> users = userService.getAllUsers();
        
        // Add HATEOAS links to each user
        List<EntityModel<User>> userModels = users.stream()
                .map(user -> {
                    EntityModel<User> userModel = EntityModel.of(user);
                    Link selfLink = WebMvcLinkBuilder.linkTo(
                            WebMvcLinkBuilder.methodOn(UserController.class).getUserById(user.getId()))
                            .withSelfRel();
                    userModel.add(selfLink);
                    return userModel;
                })
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(com.test.restful.model.ApiResponse.success(userModels, "Users retrieved successfully"));
    }

    /**
     * Get user by ID
     * 
     * @param userId User ID
     * @return User details
     */
    @GetMapping("/{userId}")
    @Operation(summary = "Get user by ID", description = "Returns a user by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found",
                content = @Content(schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<com.test.restful.model.ApiResponse<EntityModel<User>>> getUserById(
            @Parameter(description = "ID of the user to retrieve") @PathVariable Long userId) {
        
        logger.info("Fetching user with ID: {}", userId);
        
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        
        // Add HATEOAS links
        EntityModel<User> userModel = EntityModel.of(user);
        
        // Self link
        Link selfLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UserController.class).getUserById(userId))
                .withSelfRel();
        
        // All users link
        Link allUsersLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UserController.class).getAllUsers())
                .withRel("all-users");
        
        userModel.add(selfLink, allUsersLink);
        
        return ResponseEntity.ok(com.test.restful.model.ApiResponse.success(userModel, "User retrieved successfully"));
    }

    /**
     * Create a new user
     * 
     * @param user User details
     * @return Created user
     */
    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a new user and returns the created user")
    public ResponseEntity<com.test.restful.model.ApiResponse<EntityModel<User>>> createUser(
            @Valid @RequestBody User user) {
        
        logger.info("Creating new user with username: {}", user.getUsername());
        
        User createdUser = userService.createUser(user);
        
        // Add HATEOAS links
        EntityModel<User> userModel = EntityModel.of(createdUser);
        
        Link selfLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UserController.class).getUserById(createdUser.getId()))
                .withSelfRel();
        
        userModel.add(selfLink);
        
        return new ResponseEntity<>(
                com.test.restful.model.ApiResponse.success(userModel, "User created successfully"),
                HttpStatus.CREATED);
    }

    /**
     * Update an existing user
     * 
     * @param userId User ID
     * @param userDetails Updated user details
     * @return Updated user
     */
    @PutMapping("/{userId}")
    @Operation(summary = "Update a user", description = "Updates an existing user and returns the updated user")
    public ResponseEntity<com.test.restful.model.ApiResponse<EntityModel<User>>> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody User userDetails) {
        
        logger.info("Updating user with ID: {}", userId);
        
        User updatedUser = userService.updateUser(userId, userDetails);
        
        // Add HATEOAS links
        EntityModel<User> userModel = EntityModel.of(updatedUser);
        
        Link selfLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UserController.class).getUserById(userId))
                .withSelfRel();
        
        userModel.add(selfLink);
        
        return ResponseEntity.ok(com.test.restful.model.ApiResponse.success(userModel, "User updated successfully"));
    }

    /**
     * Delete a user
     * 
     * @param userId User ID
     * @return Success message
     */
    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete a user", description = "Deletes a user by ID")
    public ResponseEntity<com.test.restful.model.ApiResponse<Void>> deleteUser(@PathVariable Long userId) {
        logger.info("Deleting user with ID: {}", userId);
        
        boolean deleted = userService.deleteUser(userId);

        if (!deleted) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        return ResponseEntity.ok(com.test.restful.model.ApiResponse.success(null, "User deleted successfully"));
    }
}