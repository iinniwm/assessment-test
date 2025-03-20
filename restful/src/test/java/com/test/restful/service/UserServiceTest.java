package com.test.restful.service;

import com.test.restful.entity.UserEntity;
import com.test.restful.exception.ResourceNotFoundException;
import com.test.restful.exception.UserAlreadyExistsException;
import com.test.restful.mapper.UserMapper;
import com.test.restful.model.User;
import com.test.restful.repository.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserJpaRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private UserEntity userEntity;
    private User user;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("Test User");
        userEntity.setUsername("testuser");
        userEntity.setEmail("test@example.com");

        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setUsername("testuser");
        user.setEmail("test@example.com");
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers() {
        // Arrange
        List<UserEntity> userEntities = Arrays.asList(userEntity);
        when(userRepository.findAll()).thenReturn(userEntities);
        when(userMapper.toDto(any(UserEntity.class))).thenReturn(user);

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("testuser", result.get(0).getUsername());
        verify(userRepository).findAll();
    }

    @Test
    void getUserById_WhenUserExists_ShouldReturnUser() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(userMapper.toDto(userEntity)).thenReturn(user);

        // Act
        Optional<User> result = userService.getUserById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
        verify(userRepository).findById(1L);
    }

    @Test
    void getUserById_WhenUserDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userService.getUserById(1L);

        // Assert
        assertFalse(result.isPresent());
        verify(userRepository).findById(1L);
    }

    @Test
    void createUser_WhenUsernameAndEmailAreUnique_ShouldCreateUser() {
        // Arrange
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userMapper.toEntity(any(User.class))).thenReturn(userEntity);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(userMapper.toDto(any(UserEntity.class))).thenReturn(user);

        // Act
        User result = userService.createUser(user);

        // Assert
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userRepository).save(userEntity);
    }

    @Test
    void createUser_WhenUsernameExists_ShouldThrowException() {
        // Arrange
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        // Act & Assert
        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(user));
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void createUser_WhenEmailExists_ShouldThrowException() {
        // Arrange
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        // Act & Assert
        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(user));
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void updateUser_WhenUserExists_ShouldUpdateUser() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(userMapper.toDto(any(UserEntity.class))).thenReturn(user);

        // Act
        User result = userService.updateUser(1L, user);

        // Assert
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userRepository).save(userEntity);
    }

    @Test
    void updateUser_WhenUserDoesNotExist_ShouldThrowException() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(1L, user));
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void deleteUser_WhenUserExists_ShouldReturnTrue() {
        // Arrange
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        // Act
        boolean result = userService.deleteUser(1L);

        // Assert
        assertTrue(result);
        verify(userRepository).deleteById(1L);
    }

    @Test
    void deleteUser_WhenUserDoesNotExist_ShouldReturnFalse() {
        // Arrange
        when(userRepository.existsById(anyLong())).thenReturn(false);

        // Act
        boolean result = userService.deleteUser(1L);

        // Assert
        assertFalse(result);
        verify(userRepository, never()).deleteById(anyLong());
    }
}