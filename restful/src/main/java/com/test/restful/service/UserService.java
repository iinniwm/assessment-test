package com.test.restful.service;

import com.test.restful.entity.UserEntity;
import com.test.restful.exception.ResourceNotFoundException;
import com.test.restful.exception.UserAlreadyExistsException;
import com.test.restful.mapper.UserMapper;
import com.test.restful.model.User;
import com.test.restful.repository.UserJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing user operations
 */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    
    private final UserJpaRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserJpaRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * Retrieves all users with pagination support
     * 
     * @param pageable Pagination information
     * @return Page of users
     */
    @Transactional(readOnly = true)
    public Page<User> getAllUsers(Pageable pageable) {
        logger.info("Retrieving all users with pagination: {}", pageable);
        return userRepository.findAll(pageable)
                .map(userMapper::toDto);
    }

    /**
     * Retrieves all users
     * 
     * @return List of all users
     */
    @Transactional(readOnly = true)
    @Cacheable(value = "users")
    public List<User> getAllUsers() {
        logger.info("Retrieving all users");
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a user by ID
     * 
     * @param id User ID
     * @return Optional containing the user if found
     */
    @Transactional(readOnly = true)
    @Cacheable(value = "users", key = "#id")
    public Optional<User> getUserById(Long id) {
        logger.info("Retrieving user with ID: {}", id);
        return userRepository.findById(id)
                .map(userMapper::toDto);
    }

    /**
     * Creates a new user
     * 
     * @param user User to create
     * @return Created user
     * @throws UserAlreadyExistsException if username or email already exists
     */
    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public User createUser(User user) {
        logger.info("Creating new user with username: {}", user.getUsername());
        
        // Validate username uniqueness
        if (userRepository.existsByUsername(user.getUsername())) {
            logger.error("Username already exists: {}", user.getUsername());
            throw new UserAlreadyExistsException("Username already exists: " + user.getUsername());
        }
        
        // Validate email uniqueness
        if (userRepository.existsByEmail(user.getEmail())) {
            logger.error("Email already exists: {}", user.getEmail());
            throw new UserAlreadyExistsException("Email already exists: " + user.getEmail());
        }
        
        UserEntity userEntity = userMapper.toEntity(user);
        UserEntity savedEntity = userRepository.save(userEntity);
        return userMapper.toDto(savedEntity);
    }

    /**
     * Updates an existing user
     * 
     * @param id User ID
     * @param userDetails Updated user details
     * @return Updated user
     * @throws ResourceNotFoundException if user not found
     * @throws UserAlreadyExistsException if username or email already exists
     */
    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public User updateUser(Long id, User userDetails) {
        logger.info("Updating user with ID: {}", id);
        
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        // Check if username is being changed and if it's already taken
        if (!userEntity.getUsername().equals(userDetails.getUsername()) && 
                userRepository.existsByUsername(userDetails.getUsername())) {
            logger.error("Username already exists: {}", userDetails.getUsername());
            throw new UserAlreadyExistsException("Username already exists: " + userDetails.getUsername());
        }
        
        // Check if email is being changed and if it's already taken
        if (!userEntity.getEmail().equals(userDetails.getEmail()) && 
                userRepository.existsByEmail(userDetails.getEmail())) {
            logger.error("Email already exists: {}", userDetails.getEmail());
            throw new UserAlreadyExistsException("Email already exists: " + userDetails.getEmail());
        }
        
        userMapper.updateEntityFromDto(userDetails, userEntity);
        UserEntity updatedEntity = userRepository.save(userEntity);
        return userMapper.toDto(updatedEntity);
    }

    /**
     * Deletes a user
     * 
     * @param id User ID
     * @return true if user was deleted, false otherwise
     */
    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public boolean deleteUser(Long id) {
        logger.info("Deleting user with ID: {}", id);
        
        if (!userRepository.existsById(id)) {
            return false;
        }
        
        userRepository.deleteById(id);
        return true;
    }
}