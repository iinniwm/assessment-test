package com.test.restful.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.restful.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    public void getAllUsers_ShouldReturnInitialUsers() throws Exception {
        mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", hasSize(3)));
    }

    @Test
    @WithMockUser
    public void getUserById_WithValidId_ShouldReturnUser() throws Exception {
        mockMvc.perform(get("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.content.username", is("Bret")));
    }

    @Test
    @WithMockUser
    public void getUserById_WithInvalidId_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/users/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void createUser_WithValidData_ShouldCreateAndReturnUser() throws Exception {
        User newUser = new User();
        newUser.setName("John Doe");
        newUser.setUsername("johndoe");
        newUser.setEmail("john@example.com");

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.content.username", is("johndoe")));
    }

    @Test
    @WithMockUser
    public void createUser_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        User invalidUser = new User();
        // Missing required fields

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void updateUser_WithValidData_ShouldUpdateAndReturnUser() throws Exception {
        // First, get the user
        MvcResult result = mockMvc.perform(get("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        User user = objectMapper.readValue(content, User.class);
        user.setName("Updated Name");

        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.content.name", is("Updated Name")));
    }

    @Test
    @WithMockUser
    public void deleteUser_WithValidId_ShouldDeleteAndReturnSuccess() throws Exception {
        // First, create a user to delete
        User newUser = new User();
        newUser.setName("Delete Me");
        newUser.setUsername("deleteme");
        newUser.setEmail("delete@example.com");

        MvcResult result = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        // Extract the ID from the response
        int startIndex = content.indexOf("\"id\":");
        int endIndex = content.indexOf(",", startIndex);
        String idStr = content.substring(startIndex + 5, endIndex).trim();
        Long id = Long.parseLong(idStr);

        // Now delete the user
        mockMvc.perform(delete("/api/users/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)));

        // Verify the user is deleted
        mockMvc.perform(get("/api/users/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}