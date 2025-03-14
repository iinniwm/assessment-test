package com.test.restful.controller;
import com.test.restful.exception.ResourceNotFoundException;
import com.test.restful.model.ApiResponse;
import com.test.restful.model.User;
import com.test.restful.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success(users, "Users retrieved successfully"));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return ResponseEntity.ok(ApiResponse.success(user, "User retrieved successfully"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@Valid @RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(
                ApiResponse.success(createdUser, "User created successfully"),
                HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<User>> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody User userDetails) {

        User updatedUser = userService.updateUser(userId, userDetails)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        return ResponseEntity.ok(ApiResponse.success(updatedUser, "User updated successfully"));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long userId) {
        boolean deleted = userService.deleteUser(userId);

        if (!deleted) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        return ResponseEntity.ok(ApiResponse.success(null, "User deleted successfully"));
    }
}
