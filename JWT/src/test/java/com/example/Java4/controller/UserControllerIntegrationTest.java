package com.example.Java4.controller;

import com.example.Java4.entity.LoginResponseDTO;
import com.example.Java4.entity.User;
import com.example.Java4.repository.ListItemRepository;
import com.example.Java4.repository.ListRepository;
import com.example.Java4.repository.UserRepository;
import com.example.Java4.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.config.name=application-tests")
@AutoConfigureMockMvc
@Transactional
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private AuthenticationService authenticationService;

    private String token;
    private User testUser;


    @BeforeEach
    void setup() {
        register();
        login();
    }

    private void register() {
        authenticationService.registerUser("test@test.com", "userTest", "password");

    }

    private void login() {
        LoginResponseDTO login = authenticationService.loginUser("userTest", "password");
        token = login.getJwt();
        testUser = login.getUser();

    }

    @Test
    void findAllUsersAsAdmin() throws Exception {
        LoginResponseDTO login = authenticationService.loginUser("admin", "password");

        mvc.perform(get("/user/s")
                        .header("Authorization", "Bearer " + login.getJwt())
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username").value("admin"))
                .andExpect(jsonPath("$[1].username").value("userTest"));
    }

    @Test
    void findAllUsersAsUser() throws Exception {

        mvc.perform(get("/user/s")
                        .header("Authorization", "Bearer " + token)
                ).andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void findUserById() throws Exception {

        mvc.perform(get("/user/" + testUser.getId())
                        .header("Authorization", "Bearer " + token)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("userTest"))
                .andExpect(jsonPath("$.email").value("test@test.com"));
    }
}