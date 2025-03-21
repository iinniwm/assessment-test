package com.test.restful.service;

import com.test.restful.model.User;
import com.test.restful.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> updateUser(Long id, User userDetails) {
        Optional<User> userOptional = userRepository.findById(id);
        
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            
            // Update user fields
            existingUser.setName(userDetails.getName());
            existingUser.setUsername(userDetails.getUsername());
            existingUser.setEmail(userDetails.getEmail());
            existingUser.setPhone(userDetails.getPhone());
            existingUser.setWebsite(userDetails.getWebsite());
            
            // Update address if provided
            if (userDetails.getAddress() != null) {
                existingUser.setAddress(userDetails.getAddress());
            }
            
            // Update company if provided
            if (userDetails.getCompany() != null) {
                existingUser.setCompany(userDetails.getCompany());
            }
            
            return Optional.of(userRepository.save(existingUser));
        }
        
        return Optional.empty();
    }

    public boolean deleteUser(Long id) {
        return userRepository.deleteById(id);
    }
}